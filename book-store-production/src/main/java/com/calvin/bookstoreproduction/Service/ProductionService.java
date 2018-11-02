package com.calvin.bookstoreproduction.Service;

import com.calvin.bookstorebasis.entity.Product;

import java.util.List;


public interface ProductionService {

    // 查询书本是否存在
    boolean isExistBook(String bookName);

    // 查询获取所有在商城的书本
    List<Product> getAllBook();

    // 查询书本的所有类别
    List<String> getCategories(String bookName);

    // 查询书本的所有作者
    List<String> getAuthor(String bookName);

    // 根据书名获取完整的Product实体
    Product getProductByName(String bookName);

    // 根据Id获取完整的Product实体
    Product getProductById(int productionId);

    // 向数据库添加书本
    boolean addBook(Product product);

    // 删除数据库中的书本
    boolean deleteBook(Product product);

    // 设置书本的价格
    void setBookPrice(String bookName, float price);

    // 设置书本的库存量
    void setBookQuantity(String bookName, int quantity);

    // 设置书本的描述
    void setBookDescription(String bookName, String description);

    // 减少书本的库存量，减少量为gap
    void minusBookQuantity(String bookName, int gap);

    //向书本添加类别
    void addBookCategory(String bookName, String category);

    // 向书本添加作者
    void addBookAuthor(String bookName, String author);

    // 获取书本的唯一id
    int getProductionId(String bookName);

    // 根据类别获取这个类别的所有Product实体
    List<Product> getProductByCategory(String category);
}
