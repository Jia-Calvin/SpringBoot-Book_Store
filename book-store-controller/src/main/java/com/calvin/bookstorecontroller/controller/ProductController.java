package com.calvin.bookstorecontroller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.calvin.bookstorebasis.entity.Product;
import com.calvin.bookstoreproduction.Service.ProductionService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Component
public class ProductController {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:${dubbo.protocol.port.production}")
    private ProductionService productService;

    @RequestMapping(value = "/shopping/getProductByCate", method = RequestMethod.POST)
    @ResponseBody
    public List<Product> getProductByCategory(String category) {
        return productService.getProductByCategory(category);
    }

    @RequestMapping(value = "/shopping/getProductByName", method = RequestMethod.POST)
    @ResponseBody
    public Product getProductByName(String bookName) {
        return productService.getProductByName(bookName);
    }

    @RequestMapping(value = "/shopping/getProductIdByName", method = RequestMethod.POST)
    @ResponseBody
    public int getProductIdByName(String productName) {
        return productService.getProductionId(productName);
    }

    public ProductionService getProductService() {
        return productService;
    }

}
