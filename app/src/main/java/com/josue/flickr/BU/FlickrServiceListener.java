package com.josue.flickr.BU;

import com.josue.flickr.DB.FlickrObjet;

import java.util.List;

public interface FlickrServiceListener {
    public void onResponseListener (List<FlickrObjet> flickrObjetList);

}
