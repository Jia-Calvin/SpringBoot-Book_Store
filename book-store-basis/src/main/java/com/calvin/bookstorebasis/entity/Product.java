package com.calvin.bookstorebasis.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Product implements Serializable {

    private int productionId;

    private String name;

    private List<String> authors;

    private float price;

    private String description;

    private int quantity;

    private List<String> categories;

    @Override
    public String toString() {
        return "Product{" +
                "productionId=" + productionId +
                ", name='" + name + '\'' +
                ", authors=" + authors +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", categories=" + categories +
                '}';
    }


    public Product(int productionId, String name, List<String> authors, float price, String description, int quantity,
                   List<String> categories) {
        this.productionId = productionId;
        this.name = name;
        this.authors = authors;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.categories = categories;
    }

    public Product(String name, List<String> authors, float price, String description, int quantity,
                   List<String> categories) {
        this.name = name;
        this.authors = authors;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.categories = categories;
    }

    public Product(int productionId, String name, float price, String description, int quantity) {
        this.productionId = productionId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public Product(int productionId) {
        this.productionId = productionId;
    }

    public Product() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getProductionId() == product.getProductionId() &&
                Float.compare(product.getPrice(), getPrice()) == 0 &&
                getQuantity() == product.getQuantity() &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getAuthors(), product.getAuthors()) &&
                Objects.equals(getDescription(), product.getDescription()) &&
                Objects.equals(getCategories(), product.getCategories());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getProductionId(), getName(), getAuthors(), getPrice(), getDescription(), getQuantity(),
                getCategories());
    }

    public int getProductionId() {
        return productionId;
    }

    public void setProductionId(int productionId) {
        this.productionId = productionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
