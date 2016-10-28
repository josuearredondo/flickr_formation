package com.josue.flickr.UI;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.josue.flickr.DB.FlickrObjet;
import com.josue.flickr.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterFlickr extends BaseAdapter {
    private List<FlickrObjet> listFlickr;
    FlickrObjet flickrObjet;
    private LinearLayout linearLayoutRow;
    private Context context;
    private int position;

    public AdapterFlickr(Context context) {
        listFlickr = new ArrayList<FlickrObjet>();
        this.context = context;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listFlickr.size();
    }
    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        this.position = position;
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.raw_layout, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.textRow);
        textView.setText(listFlickr.get(position).getTitle());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageRow);
        Picasso.with(context).load(listFlickr.get(position).getUrl()).resize(100, 100).placeholder(R.drawable.flickr).centerCrop().into(imageView);
        linearLayoutRow = (LinearLayout) convertView.findViewById(R.id.linearRow);
        linearLayoutRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                flickrObjet = listFlickr.get(position);
                intent.putExtra("FlickrObjet", flickrObjet);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void setListImages(List<FlickrObjet> flickrList) {
        this.listFlickr = flickrList;
        notifyDataSetChanged();
    }
}


