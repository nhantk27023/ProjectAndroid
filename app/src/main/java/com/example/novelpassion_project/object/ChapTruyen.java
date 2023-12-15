package com.example.novelpassion_project.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChapTruyen implements Serializable {
    @SerializedName("tenchap")

    private String tenChap;
    @SerializedName("ngaynhap")

    private String ngayDang;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    public ChapTruyen() {}

    public ChapTruyen(String id,String tenChap, String ngayDang) {
        this.id=id;
        this.tenChap = tenChap;
        this.ngayDang = ngayDang;
    }

    public String getTenChap() {
        return tenChap;
    }

    public void setTenChap(String tenChap) {
        this.tenChap = tenChap;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }
}
