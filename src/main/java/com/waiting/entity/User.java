package com.waiting.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fish on 17/03/2017.
 */
@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "openid")
    private String openid;
    @Column(name = "studentid")
    private String studentid;
    @Column(name = "last_submit")
    private Date lastSubmit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Date getLastSubmit() {
        return lastSubmit;
    }

    public void setLastSubmit(Date lastSubmit) {
        this.lastSubmit = lastSubmit;
    }
}
