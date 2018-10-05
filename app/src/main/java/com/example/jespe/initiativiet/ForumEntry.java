package com.example.jespe.initiativiet;



/**
 * Created by jespe on 02-12-2017.
 */

public class ForumEntry {
    private String type;
    private String title;
    private String content;
    private String dateCreated;
    private String dateModified;
    private String tagId;
    private String tagName;
    private String authorID;

    public String getAuthorID() { return  authorID; };

    public void setAuthorID(String authorID) { this.authorID = authorID; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}