package com.xtm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;

import javax.persistence.*;
import java.util.Date;

/**
 * @author:藏剑
 * @date:2019/6/18 10:46
 */

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Integer id;

    @Column(length = 5)
    private Integer authorId;

    @Column(name = "news_title", length = 100)
    private String title;

    @Column(name = "news_content", columnDefinition = "longtext")
    private String content;

    @Column
    private Date createTime;

    @Column(name = "news_click", length = 5)
    private Integer click;

    public News() {
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", click=" + click +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
