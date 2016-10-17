package com.josue.flickr;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FlickrService boundService;
    boolean bound = false;
    ListView listView;
    Button bntChange;

    AdapterFlickr adapter;
    List<String> list1;
    List<String> list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        bntChange = (Button) findViewById(R.id.buttonChange);

        list1 = new ArrayList<>();
        list1.add("Toto");
        list1.add("Titi");
        list1.add("Tata");
        adapter = new AdapterFlickr (this);
        listView.setAdapter(adapter);
        adapter.setListText(list1);
        addListenerOnButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, FlickrService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
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
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    public void addListenerOnButton() {
        bntChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (bound) {
                    boundService.getToastText();
                }
                /*list2 = new ArrayList<>();
                list2.add("Nono");
                list2.add("Nini");
                list2.add("Nana");
                adapter.setListText(list2);*/


            }
        });
    }
}
