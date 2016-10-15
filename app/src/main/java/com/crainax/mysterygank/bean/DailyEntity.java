package com.crainax.mysterygank.bean;

/**
 * Created by crainax on 2016/10/15.
 */
public class DailyEntity {

    /**
     * _id : 58005225421aa95ddb9cb5e5
     * content : 。。。
     * publishedAt : 2016-10-14T11:31:00.0Z
     * title : 今日力推：教你用 Sketch 画 icon / Google 开源了一个专为 VR 设计的相机 App / JS 实现的 60 多种语言的 OCR 库 / Random-ideas，有什么想法，就在这里发 issue 吧！
     */

    private String _id;
    private String content;
    private String publishedAt;
    private String title;
    private String imageUrl;

    public String get_id() {
        return _id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
