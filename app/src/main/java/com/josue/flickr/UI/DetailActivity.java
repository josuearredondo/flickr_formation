package com.josue.flickr.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.josue.flickr.MO.FlickrObjet;
import com.josue.flickr.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity {
    TextView textViewTitle;
    ImageView imageView;
    FlickrObjet flickrObjet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = getIntent();
        flickrObjet = (FlickrObjet)i.getSerializableExtra("FlickrObjet");
        textViewTitle = (TextView) findViewById(R.id.textTitle);
        textViewTitle.setText((flickrObjet.getTitle()));
        imageView = (ImageView) findViewById(R.id.imageRow);
        Picasso.with(this).load(flickrObjet.getUrl()).resize(200, 200).placeholder(R.drawable.flickr).centerCrop().into(imageView);
    }
}
