package com.lm.demo.slow_life.bean;

/**
 * Created by Administrator on 2016/12/11.
 */
public class DetailsBean {
    private String title;
    private String source;
    private String content;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "DetailsBean{" +
                "title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
