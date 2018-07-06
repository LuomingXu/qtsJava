/*
 * Copyright (c) 2018
 * Author : Luoming Xu
 * Project Name : CityLife
 * File Name : LogModel.java
 * CreateTime: 2018/07/05 19:01:30
 * LastModifiedDate : 18-7-5 下午6:58
 */

package com.qst.model;

import java.util.Date;

public class LogModel
{
    private Integer id;

    private Date deleteDate;

    private String deletedUsername;

    private String deletedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getDeletedUsername() {
        return deletedUsername;
    }

    public void setDeletedUsername(String deletedUsername) {
        this.deletedUsername = deletedUsername == null ? null : deletedUsername.trim();
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy == null ? null : deletedBy.trim();
    }

    @Override
    public String toString()
    {
        return "LogModel{" +
                "id=" + id +
                ", deleteDate=" + deleteDate +
                ", deletedUsername='" + deletedUsername + '\'' +
                ", deletedBy='" + deletedBy + '\'' +
                '}';
    }

    public String logFormat()
    {
        return String.format("%s#%s#%s#%s", id, deleteDate, deletedUsername, deletedBy);
    }
}