package com.adelean.xmlReader.models;

import org.jsoup.Jsoup;

public class Description {
    String content;
    String contentShort;

    public Description(String contentHtml, String shortContentHtml) {
        // sanitize contentHtml
        this.content = Jsoup.parse(contentHtml).text();
        this.contentShort = Jsoup.parse(shortContentHtml).text();
    }

    public String getContent() {
        return content;
    }

    public String getContentShort() {
        return contentShort;
    }
}
