package com.example.myapplication.entity;


import java.io.File;

/**
 * 本地图片实体
 */
public class ImageEntity {
    private String name;
    private String location;
    private boolean isDir;
    private int index = 0;

    private String filePath;

    private String parentPath;

    public ImageEntity(){}

    public ImageEntity(File file) {
        this.name = file.getName();
        this.location = file.toURI().toString();
        this.isDir = file.isDirectory();
        this.filePath = file.getPath();
        this.parentPath = file.getParent();
    }

    public ImageEntity(File file, String name, String location) {
        this.name = name;
        this.location = location;
        if (file != null){
            this.isDir = file.isDirectory();
            this.filePath = file.getPath();
            this.parentPath = file.getParent();
        }
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
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


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
