package com.calvin.bookstoreproduction.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.calvin.bookstorebasis.entity.Product;
import com.calvin.bookstoreproduction.Mapper.ProductionMapper;
import com.calvin.bookstoreproduction.Service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class ProductionServiceImpl implements ProductionService {

    @Autowired
    ProductionMapper productionMapper;

    @Override
    public int getProductionId(String bookName) {
        return productionMapper.getProductionId(bookName);
    }

    @Override
    public void minusBookQuantity(String bookName, int gap) {
        Product product = getProductByName(bookName);
        setBookQuantity(product.getName(), product.getQuantity() - gap);
    }

    @Override
    public List<Product> getAllBook() {
        List<Product> products = productionMapper.getAllProduction();
        for (Product product : products) {
            product.setCategories(getCategories(product.getName()));
            product.setAuthors(getAuthor(product.getName()));
        }
        return products;
    }

    @Override
    public List<String> getCategories(String bookName) {
        return productionMapper.getCategories(getProductionId(bookName));
    }

    @Override
    public List<String> getAuthor(String bookName) {
        return productionMapper.getAuthor(getProductionId(bookName));
    }

    @Override
    public void setBookPrice(String bookName, float price) {
        productionMapper.updatePrice(getProductionId(bookName), price);
    }

    @Override
    public void setBookQuantity(String bookName, int quantity) {
        productionMapper.updateQuantity(getProductionId(bookName), quantity);
    }

    @Override
    public void addBookCategory(String bookName, String category) {
        productionMapper.addCategory(category, getProductionId(bookName));
    }

    @Override
    public void addBookAuthor(String bookName, String author) {
        productionMapper.addAuthor(author, getProductionId(bookName));
    }

    @Override
    public void setBookDescription(String bookName, String description) {
        productionMapper.updateDescription(getProductionId(bookName), description);
    }

    @Override
    public boolean addBook(Product product) {
        String bookName = product.getName();
        if (!isExistBook(bookName)) {
            productionMapper.addProduction(product);

            List<String> authors = product.getAuthors();
            List<String> categories = product.getCategories();
            for (String author : authors)
                addBookAuthor(bookName, author);
            for (String category : categories)
                addBookCategory(bookName, category);
            return true;
        }
        return false;
    }

    @Override
    public boolean isExistBook(String bookName) {
        return productionMapper.getProductByName(bookName) != null;
    }

    @Override
    public boolean deleteBook(Product product) {
        productionMapper.deleteProduction(product.getName());
        return true;
    }

    @Override
    public Product getProductByName(String bookName) {
        Product product = productionMapper.getProductByName(bookName);
        product.setAuthors(getAuthor(bookName));
        product.setCategories(getCategories(bookName));
        return product;
    }

    @Override
    public Product getProductById(int productionId) {
        String bookName = productionMapper.getProductName(productionId);
        return getProductByName(bookName);
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        List<Product> products = new ArrayList<>();
        List<Integer> productionIds = productionMapper.getProductionIdByCategory(category);
        for (Integer productionId : productionIds) {
            Product product = productionMapper.getProductByProductionId(productionId);
            product.setAuthors(getAuthor(product.getName()));
            product.setCategories(getCategories(product.getName()));
            products.add(product);
        }
        return products;
    }
}
