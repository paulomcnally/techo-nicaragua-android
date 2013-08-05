package com.mc.reader.database;

import android.database.Cursor;

public class OdbArticle {
    
    private int count = 0;

    // vars
    private int article_id;
    private String article_author;
    private String article_type;
    private String article_content;
    private String article_title;
    private String article_registered;
    private String article_picture;

    // columnName
    private String column_article_id = "article_id";
    private String column_article_author = "article_author";
    private String column_article_type = "article_type";
    private String column_article_content = "article_content";
    private String column_article_title = "article_title";
    private String column_article_registered = "article_registered";
    private String column_article_picture = "article_picture";

    
    public OdbArticle(){
            super();
    }
    
    public OdbArticle( Cursor c ){
            super();
            
            this.count = c.getCount();

            if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                            this.article_id = c.getInt(c.getColumnIndex(column_article_id));
                            this.article_author = c.getString(c.getColumnIndex(column_article_author));
                            this.article_type = c.getString(c.getColumnIndex(column_article_type));
                            this.article_content = c.getString(c.getColumnIndex(column_article_content));
                            this.article_title = c.getString(c.getColumnIndex(column_article_title));
                            this.article_registered = c.getString(c.getColumnIndex(column_article_registered));
                            this.article_picture = c.getString(c
                                            .getColumnIndex(column_article_picture));
                    }
                    c.close();
            }
            else{
                    c.close();
            }
    }
    
    public int getCount(){
            return this.count;
    }
    
    public int getId() {
            return this.article_id;
    }
    
    public String getAuthor(){
            return this.article_author;
    }
    
    public String getType(){
            return this.article_type;
    }
    
    public String getContent(){
            return this.article_content;
    }
    
    public String getTitle(){
            return this.article_title;
    }
    
    public String getRegistered(){
            return this.article_registered;
    }
    
    public String getPicture(){
            return this.article_picture;
    }

}