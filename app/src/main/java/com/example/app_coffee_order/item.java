package com.example.app_coffee_order;

import java.util.HashMap;
import java.util.Map;

public class item {
    private Boolean checkbox;
    private String image;
    private String masp;
    private String name;
    private int price;
    private int quantity;
    private String size;

    public item() {
    }

    public item(Boolean checkbox, String image, String masp, String name, int price, int quantity, String size) {
        this.checkbox = checkbox;
        this.image = image;
        this.masp = masp;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
    }

    public Boolean getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(Boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("quantity",quantity);
        result.put("price",price);
        result.put("checkbox",checkbox);

        return result;
    }
    public HashMap<String, Boolean> toMap2() {
        HashMap<String, Boolean> result2 = new HashMap<>();
        result2.put(String.valueOf("checkbox"), checkbox);

        return result2;
    }
}
