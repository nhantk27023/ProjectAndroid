package com.example.novelpassion_project.book;

public class Book {
    private int resourceId;
    private String title;
    private String desc;

    public Book(int resourceId, String title, String desc) {
        this.resourceId = resourceId;
        this.title = title;
        this.desc = desc;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
