package com.calvin.bookstoreproduction.Mapper;

import com.calvin.bookstorebasis.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductionMapper {

    @Select("SELECT * FROM PRODUCTION")
    List<Product> getAllProduction();

    @Select("SELECT * From PRODUCTION WHERE NAME = #{bookName}")
    Product getProductByName(@Param("bookName") String bookName);

    @Select("SELECT * From PRODUCTION WHERE PRODUCTIONId = #{productionId}")
    Product getProductByProductionId(@Param("productionId") int productionId);

    @Insert("INSERT INTO PRODUCTION(PRODUCTIONId, NAME, PRICE, DESCRIPTION, QUANTITY) VALUES(#{production" +
            ".productionId}, #{production.name}, #{production.price}, #{production.description}, #{production" +
            ".quantity})")
    void addProduction(@Param("production") Product bookProduction);

    @Insert("INSERT INTO PRODUCTION_AUTHOR(AUTHOR,PRODUCTIONId) VALUES(#{author}, #{productionId})")
    boolean addAuthor(@Param("author") String author, @Param("productionId") int productionId);

    @Insert("INSERT INTO PRODUCTION_CATEGORIES(CATEGORY,PRODUCTIONId) VALUES(#{category}, #{productionId})")
    boolean addCategory(@Param("category") String category, @Param("productionId") int productionId);

    @Select("SELECT PRODUCTIONId From PRODUCTION WHERE NAME = #{bookName}")
    int getProductionId(@Param("bookName") String bookName);

    @Select("SELECT NAME From PRODUCTION WHERE PRODUCTIONId = #{productionId}")
    String getProductName(@Param("productionId") int productionId);

    @Delete("DELETE FROM PRODUCTION WHERE NAME = #{bookName}")
    void deleteProduction(@Param("bookName") String bookName);

    @Select("SELECT AUTHOR FROM PRODUCTION_AUTHOR WHERE PRODUCTIONId = #{productionId}")
    List<String> getAuthor(@Param("productionId") int productionId);

    @Select("SELECT CATEGORY FROM PRODUCTION_CATEGORIES WHERE PRODUCTIONId = #{productionId}")
    List<String> getCategories(@Param("productionId") int productionId);

    @Update("UPDATE PRODUCTION SET QUANTITY = #{quantity} WHERE PRODUCTIONId = #{productionId} ")
    void updateQuantity(@Param("productionId") int productionId, @Param("quantity") int quantity);

    @Update("UPDATE PRODUCTION SET PRICE = #{price} WHERE PRODUCTIONId = #{productionId} ")
    void updatePrice(@Param("productionId") int productionId, @Param("price") float price);

    @Update("UPDATE PRODUCTION SET DESCRIPTION = #{description} WHERE PRODUCTIONId = #{productionId} ")
    void updateDescription(@Param("productionId") int productionId, @Param("description") String description);

    @Select("SELECT PRODUCTIONId FROM PRODUCTION_CATEGORIES WHERE CATEGORY = #{category}")
    List<Integer> getProductionIdByCategory(@Param("category") String category);

}
