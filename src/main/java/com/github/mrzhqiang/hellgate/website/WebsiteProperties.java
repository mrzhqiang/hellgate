package com.github.mrzhqiang.hellgate.website;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("website")
public class WebsiteProperties {
    private String author;
    private String favicon;
    private String title;
    private String copyRight;
    private Boolean rememberMe;
    private String[] ignorePath;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String[] getIgnorePath() {
        return ignorePath;
    }

    public void setIgnorePath(String[] ignorePath) {
        this.ignorePath = ignorePath;
    }
}
