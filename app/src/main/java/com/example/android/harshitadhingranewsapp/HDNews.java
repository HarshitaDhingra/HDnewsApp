package com.example.android.harshitadhingranewsapp;

/**
 * Created by PC on 30-07-2017.
 */

public class HDNews {
    private String title;
    private String publishDate;
    private String section;
    private String mUrl;
    public HDNews(String a,String c,String d,String e) {
        title=a;
        publishDate=c;
        section=d;
        mUrl=e;
    }
    public String getTitle() {
        return title;
    }
    public String getPublishDate() {
        return publishDate;
    }
    public String getUrl() {
        return mUrl;
    }
    public String getSection(){return section;}
}
