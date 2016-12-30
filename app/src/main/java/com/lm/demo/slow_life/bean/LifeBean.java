package com.lm.demo.slow_life.bean;

/**
 * Created by Administrator on 2016/12/10.
 */
public class LifeBean {
    private int id ;
    //标题
    private String title;
    //链接
    private String link;
    //图片链接
    private String imgLink;
    //内容
    private String content;
    //发布时间
    private String time;
    //类型
    private int lifeType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLifeType() {
        return lifeType;
    }

    public void setLifeType(int lifeType) {
        this.lifeType = lifeType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "LifeBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", lifeType=" + lifeType +
                '}';
    }
}
