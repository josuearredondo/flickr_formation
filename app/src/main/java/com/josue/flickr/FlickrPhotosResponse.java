package com.josue.flickr;

public class FlickrPhotosResponse {
    private Photos photos;
    private String stat;

    public FlickrPhotosResponse() {

    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Photos getPhotos() {
        return photos;
    }

    public String getStat() {
        return stat;
    }
}
