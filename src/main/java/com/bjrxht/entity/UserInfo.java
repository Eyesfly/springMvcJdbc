package com.bjrxht.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/25.
 */
public class UserInfo implements Serializable {
    private int id;
    private String uname;
    private int unumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getUnumber() {
        return unumber;
    }

    public void setUnumber(int unumber) {
        this.unumber = unumber;
    }

    @Override
    public String toString() {
        return "uname="+uname+"===========unumber:"+unumber;
    }
}
