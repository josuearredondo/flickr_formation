package com.josue.flickr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView textViewTitle;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String title = getIntent().getExtras().getString("EXTRA_TITLE");
        String url = getIntent().getExtras().getString("EXTRA_URL_IMAGE");

        textViewTitle = (TextView) findViewById(R.id.textTitle);
        textViewTitle.setText(title);
        imageView = (ImageView) findViewById(R.id.imageRow);
        Picasso.with(this).load(url).resize(200, 200).placeholder(R.drawable.flickr).centerCrop().into(imageView);
    }
}
