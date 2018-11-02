package com.calvin.bookstorebasis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuyerCart implements Serializable {

    private List<BuyerItem> items = new ArrayList<>();

    public void addItem(BuyerItem buyerItem) {
        int buyerItemProductId = buyerItem.getProduct().getProductionId();
        for (BuyerItem item : items) {
            if (item.getProduct().getProductionId() == buyerItemProductId) {
                item.setAmount(item.getAmount() + buyerItem.getAmount());
                return;
            }
        }
        items.add(buyerItem);
    }

    public void removeItem(BuyerItem buyerItem) {
        int buyerItemProductId = buyerItem.getProduct().getProductionId();
        for (BuyerItem item : items) {
            if (item.getProduct().getProductionId() == buyerItemProductId) {
                items.remove(item);
                return;
            }
        }
    }

    public List<BuyerItem> getItems() {
        return items;
    }

    public void setItems(List<BuyerItem> items) {
        this.items = items;
    }


    @JsonIgnore
    public Integer getProductAmount() {
        Integer result = 0;
        for (BuyerItem item : items) {
            result += item.getAmount();
        }
        return result;
    }

    @JsonIgnore
    public float getProductPrice() {
        float result = 0F;
        for (BuyerItem item : items) {
            result += item.getAmount() * item.getProduct().getPrice();
        }
        return result;
    }

    @JsonIgnore
    public float getFee() {
        float result = 0F;
        if (getProductPrice() < 79)
            result = 5F;
        return result;
    }

    @JsonIgnore
    public float getTotalPrice0() {
        return getProductPrice() + getFee();
    }

    @Override
    public String toString() {
        return "BuyerCart{" +
                "items=" + items +
                '}';
    }
}
