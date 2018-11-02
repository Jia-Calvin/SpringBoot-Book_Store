package com.calvin.bookstorecontroller;

import com.calvin.bookstorebasis.entity.BuyerCart;
import com.calvin.bookstorebasis.entity.BuyerItem;
import com.calvin.bookstorebasis.entity.Product;
import com.calvin.bookstorecontroller.controller.ProductController;
import com.calvin.bookstorecontroller.redis.BuyerCartRedisService;
import com.calvin.bookstoreproduction.Service.ProductionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BookStoreControllerApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BookStoreControllerApplication.class, args);

        BuyerCartRedisService redisService = context.getBean(BuyerCartRedisService.class);
//        ProductController productController = context.getBean(ProductController.class);
//        ProductionService productService = productController.getProductService();
//
//        Product product1 = productService.getProductByName("Java核心技术卷 I");
//        Product product2 = productService.getProductByName("Java核心技术卷 II");
//
//        BuyerItem item = new BuyerItem(product1, 10);
//        BuyerCart buyerCart = new BuyerCart();
//        buyerCart.addItem(item);
//        item = new BuyerItem(product2, 10);
//        buyerCart.addItem(item);
//
//        System.out.println(redisService.insertBuyerCartToRedis(buyerCart, "calvin"));
//
//        final String key = "buyerCart" + "calvin";
//        redisService.remove(key);
//        System.out.println(redisService.exists(key));
//
//        System.out.println(redisService.getBuyerCartFromRedis("calvin"));
    }
}
