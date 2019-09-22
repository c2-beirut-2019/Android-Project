package com.example.projetc2application.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductsBean implements Serializable {

    private String _id = "";
    private String title = "";
    private String description = "";
    private String price = "";
    private int quantityAvailable = 0;
    private String createDate = "";
    private ArrayList<String> colorsAvailable = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public ArrayList<String> getColorsAvailable() {
        return colorsAvailable;
    }

    public void setColorsAvailable(ArrayList<String> colorsAvailable) {
        this.colorsAvailable = colorsAvailable;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
