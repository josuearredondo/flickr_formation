package com.josue.flickr;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FlickrServiceListener {
    private FlickrService boundService;
    boolean bound = false;
    ListView listView;
    Button searchButton;
    EditText editText;
    Context context;

    AdapterFlickr adapter;
    List<FlickrObjet> listFlickr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        searchButton = (Button) findViewById(R.id.buttonChange);
        editText = (EditText) findViewById(R.id.input_edit_text);
        adapter = new AdapterFlickr (this);
        listView.setAdapter(adapter);
        addListenerOnButton();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (bound && !editText.getText().toString().equals("")) {
                    boundService.getRetrofitFlickr(editText.getText().toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (bound) {
                    boundService.getRetrofitFlickr(editText.getText().toString());
                }
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }

            }
        });
    }

    /*public void addListenerOnRow() {
        linearLayoutRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*if (bound) {
                    boundService.getRetrofitFlickr(editText.getText().toString());
                }
            }
        });SSS
    }*/

    @Override
    public void onResponseListener(List<FlickrObjet> flickrObjetList) {
        //Log.e("response", String.valueOf(flickrObjetList.size()));
        adapter.setListImages(flickrObjetList);
    }
}
