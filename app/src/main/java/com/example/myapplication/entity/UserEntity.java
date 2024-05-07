package com.example.myapplication.entity;

public class UserEntity {

    Long id;
    String name;

    String imageUrl;

    String note;

    public UserEntity(){};

    public UserEntity(Long id, String name, String imageUrl, String note) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
