package com.josue.flickr.MO;


import java.io.Serializable;

public class FlickrObjet implements Serializable{
    private String title;
    private String url;
    private String id;
    private String type;
    private String lat;
    private String lng;
    private int count;
    private String search;

    enum Type {
        historic, favorite
    }


    public FlickrObjet(String title, String url) {
        this.title = title;
        this.url = url;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
