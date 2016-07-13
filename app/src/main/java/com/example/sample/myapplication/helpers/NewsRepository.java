package com.example.sample.myapplication.helpers;

import com.example.sample.myapplication.models.NewsItem;

import java.util.ArrayList;

public class NewsRepository {

    private static NewsRepository _instance;
    private ArrayList<NewsItem> _newsItems;

    public NewsRepository(){}

    public static NewsRepository I(){
        if(_instance == null){
            _instance = new NewsRepository();
        }
        return _instance;
    }

    public void Add(ArrayList<NewsItem> newsItems){
        _newsItems = newsItems;
    }

    public ArrayList<NewsItem> Get(){
        if(_newsItems == null){
            return new ArrayList<>();
        }
        return _newsItems;
    }
}
