package com.josue.flickr.BU;

import com.josue.flickr.MO.FlickrObjet;

import java.util.List;

public interface FlickrServiceListener {
    public void onResponseListener (List<FlickrObjet> flickrObjetList);

}
