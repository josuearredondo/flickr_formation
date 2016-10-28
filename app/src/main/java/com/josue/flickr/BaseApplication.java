package com.josue.flickr;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;


public class BaseApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
