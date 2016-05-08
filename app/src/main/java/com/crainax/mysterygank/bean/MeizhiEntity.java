package com.crainax.mysterygank.bean;

import java.util.Date;

/**
 * Created by Crainax on 2016/4/17.
 */
public class MeizhiEntity implements Comparable<MeizhiEntity> {

    /**
     * _id : 57136f0e67765974f885bf10
     * createdAt : 2016-04-17T19:10:06.853Z
     * desc : 4.18
     * publishedAt : 2016-04-18T12:05:28.120Z
     * source : chrome
     * type : 福利
     * url : http://ww1.sinaimg.cn/large/7a8aed7bjw1f2zwrqkmwoj20f00lg0v7.jpg
     * used : true
     * who : 张涵宇
     */

    private String _id;
    private Date createdAt;
    private String desc;
    private Date publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public MeizhiEntity() {

    }

    public MeizhiEntity(String id) {
        this._id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public String toString() {
        return "MeizhiEntity{" +
                "_id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                '}';
    }

    @Override
    public int compareTo(MeizhiEntity another) {
        return publishedAt.compareTo(another.publishedAt);
    }
}
