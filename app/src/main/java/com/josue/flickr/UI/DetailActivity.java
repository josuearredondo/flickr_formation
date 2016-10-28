package com.josue.flickr.UI;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.josue.flickr.DB.FlickrObjet;
import com.josue.flickr.DB.PersistenceManager;
import com.josue.flickr.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity implements LocationListener {
    TextView textViewTitle;
    ImageView imageView;
    ImageButton imageButton;
    FlickrObjet flickrObjet;
    PersistenceManager persistenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = getIntent();
        flickrObjet = (FlickrObjet)i.getSerializableExtra("FlickrObjet");
        imageButton = (ImageButton) findViewById(R.id.favorite);

        /*if () {
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.button_focused));
        } else {
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.button_normal));
        }*/
        imageButton.setTag("normal");
        textViewTitle = (TextView) findViewById(R.id.textTitle);
        textViewTitle.setText((flickrObjet.getTitle()));
        imageView = (ImageView) findViewById(R.id.imageRow);
        Picasso.with(this).load(flickrObjet.getUrl()).resize(200, 200).placeholder(R.drawable.flickr).centerCrop().into(imageView);

        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (imageButton.getTag().equals("focused")){
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.button_normal));
                    imageButton.setTag("normal");
                } else {
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.button_focused));
                    persistenceManager.save(flickrObjet);
                    imageButton.setTag("focused");
                }

            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {
        
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
