package com.example.android.harshitadhingranewsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PC on 30-07-2017.
 */

public class HDNewsAdapter extends ArrayAdapter<HDNews> {
    private int mResource;
    public HDNewsAdapter(Context context, List<HDNews> news) {
        super(context, 0, news);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.newslist, parent, false);
        }
        HDNews e = (HDNews) getItem(position);
        TextView tit = (TextView) convertView.findViewById(R.id.ltitle);
        tit.setText("" + e.getTitle());
        TextView sec=(TextView) convertView.findViewById(R.id.section);
        sec.setText(""+e.getSection());
        TextView date = (TextView) convertView.findViewById(R.id.ldate);
        date.setText("Dated on : "+e.getPublishDate());
        return convertView;
    }
}