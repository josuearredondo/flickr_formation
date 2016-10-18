package com.josue.flickr;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FlickrServiceListener {
    private FlickrService boundService;
    boolean bound = false;
    ListView listView;
    Button bntChange;
    Context context;

    AdapterFlickr adapter;
    List<FlickrObjet> listFlickr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        bntChange = (Button) findViewById(R.id.buttonChange);

        adapter = new AdapterFlickr (this);
        listView.setAdapter(adapter);
        addListenerOnButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, FlickrService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        context = this;
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FlickrService.ServiceBinder binder = (FlickrService.ServiceBinder) service;
            boundService = binder.getService();
            boundService.setFlickrServiceListener(MainActivity.this);
            boundService.setContext(context);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };
    public void addListenerOnButton() {
        bntChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (bound) {
                    boundService.getRetrofitFlickr();
                }
            }
        });
    }

    @Override
    public void onResponseListener(List<FlickrObjet> flickrObjetList) {
        Log.e("response", String.valueOf(flickrObjetList.size()));
        adapter.setListImages(flickrObjetList);
    }
}
