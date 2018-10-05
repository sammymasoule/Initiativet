package com.example.jespe.initiativiet;

/**
 * Created by jespe on 02-12-2017.
 */

public class Tag {
    private String tagId;
    private String tagName;
    private int journalCount;

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

    public int getJournalCount() {
        return journalCount;
    }

    public void setJournalCount(int journalCount) {
        this.journalCount = journalCount;
    }
}
