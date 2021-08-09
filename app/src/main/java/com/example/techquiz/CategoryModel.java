package com.example.techquiz;

public class CategoryModel {
 public   String text,image,categoryid;

    public CategoryModel(String image, String text, String categoryid) {
        this.image = image;
        this.text = text;
        this.categoryid = categoryid;
    }
    public CategoryModel(){

    }

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
}
