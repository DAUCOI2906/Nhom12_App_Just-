package com.example.app_coffee_order;

import java.util.HashMap;
import java.util.Map;

public class CartItem {
    private String productId;
    private String name;
    private int price;
    private String image;
    private int quantity;
    private String size;
    private boolean checkbox;
    private String masp;

    public CartItem() {};

    public CartItem(String productId, String name, int price, String image, int quantity, String size, boolean checkbox, String masp) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.size = size;
        this.checkbox = checkbox;
        this.masp = masp;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", productId);
        result.put("name", name);
        result.put("price", price);
        result.put("image", image);
        result.put("size", size);
        result.put("quantity", quantity);
        result.put("checkbox", checkbox);
        result.put("masp", masp);
        // Thêm các trường khác nếu cần thiết

        return result;
    }
}
