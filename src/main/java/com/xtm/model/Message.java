package com.xtm.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author:藏剑
 * @date:2019/6/18 10:46
 */
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer id;

    @Column(length = 15)
    private String strangerName;

    @Column(length = 15)
    private String contact;

    @Column(name = "message_content", length = 50)
    private String content;

    @Column
    private Date logTime;

    @Column(columnDefinition = "boolean")
    private Integer readStatus;

    @Column
    private Integer adminId;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", strangerName='" + strangerName + '\'' +
                ", contact='" + contact + '\'' +
                ", content='" + content + '\'' +
                ", logTime=" + logTime +
                ", readStatus=" + readStatus +
                ", adminId=" + adminId +
                '}';
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStrangerName() {
        return strangerName;
    }

    public void setStrangerName(String strangerName) {
        this.strangerName = strangerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }
}
