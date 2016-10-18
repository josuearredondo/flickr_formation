package com.josue.flickr;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickrService extends Service {
    private IBinder binder = new ServiceBinder();
    private String str = new String("Hola");
    private Context context;
    FlickrServiceHTTP service;

    public void setFlickrServiceListener(FlickrServiceListener flickrServiceListener) {
        this.flickrServiceListener = flickrServiceListener;
    }

    FlickrServiceListener flickrServiceListener;

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.flickr.com/").addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(FlickrServiceHTTP.class);
        return binder;
    }
    public class ServiceBinder extends Binder {
        FlickrService getService() {
            return FlickrService.this;
        }
    }
    /** method for clients */
    public void getToastText() {
        Toast.makeText(this,str, Toast.LENGTH_SHORT).show();
    }

    public void getRetrofitFlickr() {
        Call<FlickrPhotosResponse> flickrPhotoResponse = service.getPhotos("dogwolf",getString(R.string.api_flickr));

        flickrPhotoResponse.enqueue(new Callback<FlickrPhotosResponse>() {
            @Override
            public void onResponse(Call<FlickrPhotosResponse> call, Response<FlickrPhotosResponse> response) {

                if (call.isExecuted()) {
                    FlickrPhotosResponse flickrPhotosResponse = response.body();
                    flickrServiceListener.onResponseListener(converterPhotoResponse(flickrPhotosResponse));
                    Toast.makeText(context,"onResponse Executed", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<FlickrPhotosResponse> call, Throwable t) {

                Toast.makeText(context,"failure service", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<FlickrObjet> converterPhotoResponse(FlickrPhotosResponse flickrPhotosResponse) {
        List<Photo> photoList = flickrPhotosResponse.getPhotos().getPhoto();
        List<FlickrObjet> flickrObjets = new ArrayList<>();
        for (Photo photo : photoList){
            String url = "https://farm"+photo.getFarm()+".static.flickr.com/"+photo.getServer()+"/"+photo.getId()+"_"+photo.getSecret()+"_s.jpg";
            String title = photo.getTitle();
            FlickrObjet flickrObjet = new FlickrObjet(title, url);
            flickrObjets.add(flickrObjet);
        }
       return flickrObjets;
    }

}
