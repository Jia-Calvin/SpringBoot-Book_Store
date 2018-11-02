package com.calvin.bookstorebasis.entity;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    private int orderId;

    private String status;

    private Date createTime;

    private int cost;

    private String recAddress;

    private String recUsername;

    private String recPhone;

    private String productName;

    public Order(int orderId, String status, Date createTime, int cost, String recAddress, String recUsername, String
            recPhone, String productName) {
        this.orderId = orderId;
        this.status = status;
        this.createTime = createTime;
        this.cost = cost;
        this.recAddress = recAddress;
        this.recUsername = recUsername;
        this.recPhone = recPhone;
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", cost=" + cost +
                ", recAddress='" + recAddress + '\'' +
                ", recUsername='" + recUsername + '\'' +
                ", recPhone='" + recPhone + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getRecAddress() {
        return recAddress;
    }

    public void setRecAddress(String recAddress) {
        this.recAddress = recAddress;
    }

    public String getRecUsername() {
        return recUsername;
    }

    public void setRecUsername(String recUsername) {
        this.recUsername = recUsername;
    }

    public String getRecPhone() {
        return recPhone;
    }

    public void setRecPhone(String recPhone) {
        this.recPhone = recPhone;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
