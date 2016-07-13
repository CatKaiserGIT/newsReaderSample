package com.example.sample.myapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsPackage {

    @JsonProperty(value = "items")
    private ArrayList<NewsItem> _items;

    public ArrayList<NewsItem> getItems(){
        if(_items == null){
            return new ArrayList<>();
        }
        return _items;
    }
}
