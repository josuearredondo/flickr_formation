package com.josue.flickr;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterFlickr extends BaseAdapter {
    private List<String> listFlickr;
    private Context context;

    public AdapterFlickr(Context context) {
        listFlickr = new ArrayList<>();
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
        TextView textView = (TextView) convertView.findViewById(R.id.textRow);
        textView.setText(listFlickr.get(position));
        ImageView image = (ImageView) convertView.findViewById(R.id.imageRow);
        image.setImageResource(R.drawable.flickr);
        return convertView;
    }

    public void setListText(List listText) {
        this.listFlickr = listText;
        notifyDataSetChanged();
    }
}


