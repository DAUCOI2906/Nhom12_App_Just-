package com.example.app_coffee_order;

public class OrderItem {
    private String deliveryAddress; // Tài nguyên hình ảnh sản phẩm
    private String deliveryPerson; // Tên sản phẩm
    private String deliveryPhoneNumber; // Giá sản phẩm
    private String imageResource; // Số lượng sản phẩm
    private String orderId; // Tổng tiền
    private String orderStatus; // Khuyến mãi
    private String paymentStatus; // Chiết khấu
    private String productName; // Địa chỉ giao hàng
    private int productPrice; // Tên người giao hàng
    private int productQuantity; // Số điện thoại người giao hàng



    public OrderItem() {
    }
    public OrderItem(String deliveryAddress, String deliveryPerson, String deliveryPhoneNumber, String imageResource, String orderId, String orderStatus, String paymentStatus, String productName, int productPrice, int productQuantity) {
        this.deliveryAddress = deliveryAddress;
        this.deliveryPerson = deliveryPerson;
        this.deliveryPhoneNumber = deliveryPhoneNumber;
        this.imageResource = imageResource;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }


    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public  void setDeliveryAddress() {
        this.deliveryAddress = deliveryAddress;
    }

    public  String getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public String getDeliveryPhoneNumber() {
        return deliveryPhoneNumber;
    }

    public void setDeliveryPhoneNumber(String deliveryPhoneNumber) {
        this.deliveryPhoneNumber = deliveryPhoneNumber;
    }

    public  String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public  String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public  int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}

