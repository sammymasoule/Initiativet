package com.example.jespe.initiativiet;

/**
 * Created by sammy on 30-11-2017.
 */

public class Lovforslag {

    String content, title, type, tagName, key;

    Lovforslag(String title, String content, String tagName, String type, String key) {
        //this.author = author;
        this.key = key;
        this.title = title;
        this.content = content;
        this.tagName = tagName;
        this.type = type;
    }

    public String getKey() { return  key; }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getType() { return type; }

    public String getTagName() { return tagName; }
}