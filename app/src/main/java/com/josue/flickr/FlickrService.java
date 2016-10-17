package com.josue.flickr;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

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
        Call<FlickrPhotosResponse> flickrPhotoResponse = service.getPhotos("dogwolf");

        flickrPhotoResponse.enqueue(new Callback<FlickrPhotosResponse>() {
            @Override
            public void onResponse(Call<FlickrPhotosResponse> call, Response<FlickrPhotosResponse> response) {
                String rep = call.request().toString();
                Toast.makeText(context,rep, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<FlickrPhotosResponse> call, Throwable t) {

                Toast.makeText(context,"failure service", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
