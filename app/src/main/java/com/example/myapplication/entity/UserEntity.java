package com.example.myapplication.entity;

import com.example.myapplication.common.util.Utils;

public class UserEntity {

    Long id;
    String name;

    String imageUrl;

    String note;



    public UserEntity(){
        this.name = Utils.INSTANCE.randomName();
        this.note = "那天，她说，她不会忘记我，可是我想太多";
    };

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
