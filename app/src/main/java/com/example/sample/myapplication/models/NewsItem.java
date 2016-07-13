

package com.example.sample.myapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsItem {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "link")
    private String link;

    @JsonProperty(value = "guid")
    private String guid;

    @JsonProperty(value = "pubDate")
    private String pubDate;

    @JsonProperty(value = "author")
    private String author;

    @JsonProperty(value = "thumbnail")
    private String thumbnail;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "content")
    private String content;

    @JsonProperty(value = "enclosure")
    private Enclosure enclosure;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getGuid() {
        return guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Field f : this.getClass().getDeclaredFields()) {
            try {
                Object o = f.get(this);
                if(o instanceof String){
                    sb.append(f.getName());
                    sb.append(":");
                    sb.append(o);
                    sb.append("\n");
                }
            } catch (Exception e) {
//                sb.append(e);
            }
        }

        return sb.toString();
    }
}
