package com.calvin.bookstorebasis.entity;

import java.io.Serializable;
import java.util.Objects;

public class BuyerItem implements Serializable {

    private Product product;

    private Integer amount = 1;

    private Boolean isHave = true;

    public BuyerItem(){}

    public BuyerItem(Product product, Integer amount, Boolean isHave) {
        this.product = product;
        this.amount = amount;
        this.isHave = isHave;
    }

    public BuyerItem(Product product, Integer amount) {
        this.product = product;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuyerItem)) return false;
        BuyerItem buyerItem = (BuyerItem) o;
        return Objects.equals(getProduct(), buyerItem.getProduct()) &&
                Objects.equals(getAmount(), buyerItem.getAmount()) &&
                Objects.equals(isHave, buyerItem.isHave);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduct(), getAmount(), isHave);
    }

    @Override
    public String toString() {
        return "BuyerItem{" +
                "product=" + product +
                ", amount=" + amount +
                ", isHave=" + isHave +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getHave() {
        return isHave;
    }

    public void setHave(Boolean have) {
        isHave = have;
    }
}
