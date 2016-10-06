package com.example.vinoth.blogreader.model;

/**
 * Created by vinoth on 3/10/16.
 */

public class BlogPost  {

    private String blogtitle;
    private String description;
    private  String url;

    public BlogPost() {
    }

    public String getBlogtitle() {
        return blogtitle;
    }

    public BlogPost(String titleblog, String description, String url) {

        this.blogtitle = titleblog;
        this.description = description;
        this.url = url;

    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
    
    
    
}
