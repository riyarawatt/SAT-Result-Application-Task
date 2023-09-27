package com.task.ecommerce.dto;

import java.util.List;

public class ProductDTO {

    private Integer productId;
    private String productName;
    private String description;
    private Float salePrice;
    private Integer categoryId;
    private Boolean digital;
    private Float shippingCost;
    private Integer customerReviewCount;
    private String images;

    public ProductDTO() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Float salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getDigital() {
        return digital;
    }

    public void setDigital(Boolean digital) {
        this.digital = digital;
    }

    public Float getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Float shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Integer getCustomerReviewCount() {
        return customerReviewCount;
    }

    public void setCustomerReviewCount(Integer customerReviewCount) {
        this.customerReviewCount = customerReviewCount;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
