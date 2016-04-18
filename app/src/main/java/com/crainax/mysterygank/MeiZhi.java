package com.crainax.mysterygank;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Crainax on 2016/4/17.
 */
public class MeiZhi {

    @SerializedName("desc")
    private Date date;
    private String title;
    @SerializedName("url")
    private String girlImageURL;

    @Override
    public String toString() {
        return "MeiZhi{" +
                "date=" + date +
                ", title='" + title + '\'' +
                ", girlImageURL='" + girlImageURL + '\'' +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGirlImageURL() {
        return girlImageURL;
    }

    public void setGirlImageURL(String girlImageURL) {
        this.girlImageURL = girlImageURL;
    }
}
