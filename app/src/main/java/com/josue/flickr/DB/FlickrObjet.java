package com.josue.flickr.DB;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

@Table(database = AppDatabase.class)
public class FlickrObjet extends BaseModel implements Serializable{
    @Column
    @PrimaryKey(autoincrement = true)
    private long id;
    @Column
    private String title;
    @Column
    private String url;
    @Column
    private Type type;
    @Column
    private Double lat;
    @Column
    private Double lng;
    @Column
    private long count;
    @Column
    private String search;
    public enum Type {
        history, favorite
    }
    public FlickrObjet() {

    }
    public FlickrObjet(String title, String url, String search) {
        this.title = title;
        this.url = url;
        this.search = search;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
