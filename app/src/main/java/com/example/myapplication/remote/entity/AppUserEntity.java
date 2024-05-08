package com.example.myapplication.remote.entity;


import com.example.myapplication.entity.UserEntity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NonNull;


public class AppUserEntity extends UserEntity {

    String deviceNumber;

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }
}
