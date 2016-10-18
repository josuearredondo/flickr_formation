package com.josue.flickr;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterFlickr extends BaseAdapter {
    private List<FlickrObjet> listFlickr;
    private Context context;

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
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.raw_layout, parent, false);
        }
        Log.e("getView", String.valueOf(listFlickr.get(position).getTitle()));
        Log.e("getView", String.valueOf(listFlickr.get(position).getUrl()));
        TextView textView = (TextView) convertView.findViewById(R.id.textRow);
        textView.setText(listFlickr.get(position).getTitle());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageRow);
        Picasso.with(context).load(listFlickr.get(position).getUrl()).resize(100, 100).placeholder(R.drawable.flickr).centerCrop().into(imageView);

        return convertView;
    }

    public void setListText(List listText) {
        this.listFlickr = listText;
        notifyDataSetChanged();
    }
    public void setListImages(List<FlickrObjet> flickrList) {
        this.listFlickr = flickrList;
        notifyDataSetChanged();
    }
}


