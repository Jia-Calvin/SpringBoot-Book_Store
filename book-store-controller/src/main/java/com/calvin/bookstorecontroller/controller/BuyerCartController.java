package com.calvin.bookstorecontroller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.calvin.bookstorebasis.entity.BuyerCart;
import com.calvin.bookstorebasis.entity.BuyerItem;
import com.calvin.bookstorebasis.entity.Product;
import com.calvin.bookstorecontroller.redis.BuyerCartRedisService;
import com.calvin.bookstoreproduction.Service.ProductionService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;


@Controller
public class BuyerCartController {

    public static final String BUYER_CART = "buyerCart";

    public static final int COOKIE_KEEP_TIME = 30 * 60;

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:${dubbo.protocol.port.production}")
    private ProductionService productService;

    @Autowired
    private BuyerCartRedisService buyerCartRedisService;

    @RequestMapping(value = "/shopping/buyerCart", method = RequestMethod.POST)
    public void buyerCart(@RequestParam String productName, @RequestParam String
            buyQuantity, HttpServletResponse response, HttpServletRequest request) throws IOException {
        // 首先拿到产品的唯一对应 Id
        int productId = productService.getProductionId(productName);
        ObjectMapper mapper = new ObjectMapper();
        // Include.NON_NULL 属性为 NULL 不序列化
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        BuyerCart buyerCart = null;

        // 1. 将cookie购物车取出，需要做没有cookies的判断，找对应于自己设置的名字为BUYER_CAR的cookie
        buyerCart = getBuyerCartFromCookies(request, mapper);

        // 2. 如果购物车为空，则新建一个购物车
        if (buyerCart == null) {
            buyerCart = new BuyerCart();
        }

        // 3. 将前端传过来的商品 productId 以及购买数量 buyQuantity 加入购物车，重复的商品已由 addItem 处理
        if (buyQuantity != null) {
            Product product = new Product(productId);
            BuyerItem item = new BuyerItem(product, Integer.valueOf(buyQuantity));
            buyerCart.addItem(item);
        }

        // 4. 判断是否登录，对不同的方式进行不同的处理
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username != null) {
            // 登录状态：将购物车写入redis持久化，并立即清空cookie，重复的商品已在redis插入中处理
            buyerCartRedisService.insertBuyerCartToRedis(buyerCart, username);
            destroyCookie(response);
        } else {
            // 非登录状态： 将购物车写入cookie中， 因为cookie前面已经取出，所以不会有重复，重复的情况已在buyerCart addItem 处理
            saveBuyerCartInCookies(buyerCart, response, mapper);
        }

        response.sendRedirect("/shopping/toCart");
    }

    @RequestMapping(value = "/shopping/toCart", method = {RequestMethod.GET, RequestMethod.POST})
    public void toCart(HttpServletResponse response, HttpServletRequest request) throws
            IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Include.NON_NULL 为 NULL 的属性不进行序列化
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        BuyerCart buyerCart = null;

        // 1. 将cookie购物车取出，此时可能在未登陆前就添加了商品(增强用户体验)，登录后希望登录前的商品还在
        buyerCart = getBuyerCartFromCookies(request, mapper);

        // 2. 判断是否登录
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username != null) {
            if (buyerCart != null) {
                buyerCartRedisService.insertBuyerCartToRedis(buyerCart, username);
                destroyCookie(response);
            }
            // 从redis把购物车(应包含所有的商品)拿出来
            buyerCart = buyerCartRedisService.getBuyerCartFromRedis(username);
        }

        // 3. 判断是否是直接登录，购物车是完全空的（cookie以及redis都没有），则新建一个
        if (buyerCart == null) {
            buyerCart = new BuyerCart();
        }

        // 4. 查询数据库将 buyerCart 中的商品的具体价格以及库存进去
        List<BuyerItem> items = buyerCart.getItems();
        if (items.size() > 0) {
            for (BuyerItem item : items) {
                Product product = productService.getProductById(item.getProduct().getProductionId());
                item.getProduct().setName(product.getName());
                item.getProduct().setAuthors(product.getAuthors());
                item.getProduct().setCategories(product.getCategories());
                item.getProduct().setQuantity(product.getQuantity());
                item.getProduct().setPrice(product.getPrice());
            }
        }

        System.out.println(buyerCart);
        System.out.println();

        // 这里可以换成会话的 flash 属性，放置购物车，flash在下次重定向前都有效
        session.setAttribute(BUYER_CART, buyerCart);


        response.sendRedirect("/cart/buyerCart.html");
    }

    @RequestMapping(value = "/shopping/getBuyerCart", method = RequestMethod.POST)
    @ResponseBody
    public BuyerCart getBuyerCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        BuyerCart buyerCart = (BuyerCart) session.getAttribute(BUYER_CART);
        if (buyerCart != null && buyerCart.getItems().size() > 0)
            return (BuyerCart) session.getAttribute(BUYER_CART);
        else
            return null;
    }

    @RequestMapping(value = "/shopping/deleteProduct", method = RequestMethod.POST)
    public void deleteProduct(@RequestParam String productName, HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        int productId = productService.getProductionId(productName);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        BuyerCart buyerCart = null;

        // 1. 判断登录，决定从cookie或者redis将购物车取出
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username != null) {
            // 从 redis 将购物车取出
            buyerCart = buyerCartRedisService.getBuyerCartFromRedis(username);
        } else {
            // 从 cookie 将购物车取出
            buyerCart = getBuyerCartFromCookies(request, mapper);
        }

        // 2. 将前端传过来的商品 productId (由productName得到)
        Product product = new Product(productId);
        BuyerItem item = new BuyerItem();
        item.setProduct(product);
        buyerCart.removeItem(item);

        // 3. 依旧判断登录情况，对两类方式进行不同的处理
        if (username != null) {
            // 登录状态：将前端传递过来的商品名称从 redis 中去除，然后令cookie失效
            buyerCartRedisService.removeProductFromBuyerCart(product, username);
            destroyCookie(response);
        } else {
            // 非登录状态： 将购物车写入cookie中， 因为cookie前面已经取出，所以不会有重复，重复的情况已在buyerCart处理
            saveBuyerCartInCookies(buyerCart, response, mapper);
        }
        response.sendRedirect("/shopping/toCart");
    }


    @RequestMapping(value = "/shopping/clearBuyerCart", method = RequestMethod.POST)
    public void clearBuyerCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username != null) {
            String key = "buyerCart" + username;
            buyerCartRedisService.remove(key);
        } else {
            destroyCookie(response);
        }
        response.sendRedirect("/shopping/toCart");
    }


    private void saveBuyerCartInCookies(BuyerCart buyerCart, HttpServletResponse response, ObjectMapper mapper) throws
            IOException {
        Writer writer = new StringWriter();
        mapper.writeValue(writer, buyerCart);
        System.out.println(writer.toString());
        // cookie的值要求必须将json转化为utf-8编码方式
        Cookie cookie = new Cookie(BUYER_CART, URLEncoder.encode(writer.toString(), "utf-8"));
        // 可在同一应用服务器上共享方法就是设置: cookie.setPath("/")
        cookie.setPath("/");
        // 设置Cookie过期时间: -1 表示关闭浏览器失效  0: 立即失效  >0: 单位是秒, 多少秒后失效
        cookie.setMaxAge(COOKIE_KEEP_TIME);
        // 增加到浏览器中
        response.addCookie(cookie);
    }

    private BuyerCart getBuyerCartFromCookies(HttpServletRequest request, ObjectMapper mapper) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(BUYER_CART)) {
                    return mapper.readValue(URLDecoder.decode(cookie.getValue(), "utf-8"), BuyerCart.class);
                }
            }
        }
        return null;
    }

    static void destroyCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(BUYER_CART, null);
        // 可在同一应用服务器上共享方法就是设置: cookie.setPath("/")
        cookie.setPath("/");
        // 设置Cookie过期时间: -1 表示关闭浏览器失效  0: 立即失效  >0: 单位是秒, 多少秒后失效
        cookie.setMaxAge(0);
        // 增加到浏览器中，立即失效
        response.addCookie(cookie);
    }
}
