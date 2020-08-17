package com.example.news;

public class News {


    private String url;
    private String image_url;
    private String title;

    private String description;
    private String time;

    public News(String url, String image_url, String title, String description, String time) {
        this.url = url;
        this.image_url = image_url;
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }
}
