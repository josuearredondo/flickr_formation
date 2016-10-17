package com.josue.flickr;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class FlickrService extends Service {
    private IBinder binder = new ServiceBinder();
    private String str = new String("Hola");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
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
}
