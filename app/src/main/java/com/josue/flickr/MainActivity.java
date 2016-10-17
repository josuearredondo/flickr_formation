package com.josue.flickr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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
    public void addListenerOnButton() {
        bntChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                list2 = new ArrayList<>();
                list2.add("Nono");
                list2.add("Nini");
                list2.add("Nana");
                adapter.setListText(list2);
            }
        });
    }
}
