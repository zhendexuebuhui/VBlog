package com.xtm.model;

/**
 * @author:藏剑
 * @date:2019/6/29 16:20
 */
public class AdminClick {

    private Long click;

    private String author;

    @Override
    public String toString() {
        return "AdminClick{" +
                "click=" + click +
                ", author='" + author + '\'' +
                '}';
    }

    public Long getClick() {
        return click;
    }

    public void setClick(Long click) {
        this.click = click;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

