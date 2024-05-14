package com.example.myapplication.entity;


import java.io.File;

/**
 * 本地图片实体
 */
public class ImageEntity {
    private String name;
    private String location;
    private boolean isDir;
    private File file;

    private int index = 0;

    public ImageEntity(){}

    public ImageEntity(File file) {
        this.file = file;
        this.name = file.getName();
        this.location = file.toURI().toString();
        this.isDir = file.isDirectory();
    }

    public ImageEntity(File file, int index) {
        this.file = file;
        this.name = file.getName();
        this.location = file.toURI().toString();
        this.isDir = file.isDirectory();
        this.index = index;
    }

    public ImageEntity(File file, String name, String location,
                       Boolean isDir) {
        this.file = file;
        this.name = name;
        this.location = location;
        this.isDir = isDir;
    }

    public ImageEntity(File file, String name, String location) {
        this.file = file;
        this.name = name;
        this.location = location;
        if (file != null){
            this.isDir = file.isDirectory();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
