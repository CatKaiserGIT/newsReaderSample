package com.example.sample.myapplication.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sample.myapplication.R;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public final TextView mContentView;
    public final ImageView imageView;
    public NewsItem mItem;

    public NewsViewHolder(View view) {
        super(view);
        mView = view;
        mContentView = (TextView) view.findViewById(R.id.content);
        imageView = (ImageView)view.findViewById(R.id.img);
    }
}
