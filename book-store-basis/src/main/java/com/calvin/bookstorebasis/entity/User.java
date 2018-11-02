package com.calvin.bookstorebasis.entity;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    public static final boolean MAN = false;
    public static final boolean WOMAN = true;

    private int userId;

    private String username;

    private String password;

    private boolean gender;

    private String email;

    private List<String> address;

    private String telephone;

    public User(String username, String password, boolean gender, String email, List<String> address,
                String telephone) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
    }


    public User(int userId, String username, String password, boolean gender, String email,
                String telephone) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + toValue(gender) + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    public String toValue(boolean gender) {
        return gender ? "Woman" : "Man";
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
