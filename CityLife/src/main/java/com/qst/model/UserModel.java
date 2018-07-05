/*
 * Copyright (c) 2018
 * Author : Luoming Xu
 * Project Name : CityLife
 * File Name : UserModel.java
 * CreateTime: 2018/07/05 16:31:48
 * LastModifiedDate : 18-7-5 下午4:30
 */

package com.qst.model;

import java.util.Date;

public class UserModel {
    private String userName;

    private String password;

    private String nickName;

    private Date createtime;

    private Integer isDel;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString()
    {
        return "UserModel{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", createtime=" + createtime +
                ", isDel=" + isDel +
                '}';
    }
}