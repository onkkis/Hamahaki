package com.example.hamahaki;

public class ParseItem {
    private String imgUrl;
    private String title;
    private String price;
    private String productUrl;

    public ParseItem() {
    }

    public ParseItem(String imgUrl,String title,String price,String productUrl) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.price = price;
        this.productUrl = productUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
