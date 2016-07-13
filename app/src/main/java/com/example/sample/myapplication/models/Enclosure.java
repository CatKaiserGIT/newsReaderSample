package com.example.sample.myapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Enclosure {

    @JsonProperty(value = "link")
    private String link;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "length")
    private int length;

    public String getLink() {
        return link;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }
}
