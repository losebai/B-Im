package com.example.myapplication.remote.entity;


import com.example.myapplication.entity.UserEntity;


public class AppUserEntity extends UserEntity {

    String deviceNumber;

    public AppUserEntity(){}

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }
}
