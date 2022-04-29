package com.triminh.comicpro.model.danhmuc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoaiTruyen {

    @SerializedName("maloai")
    @Expose
    private String maloai;

    @SerializedName("loaitruyen")
    @Expose
    private String loaitruyen;

    @SerializedName("status")
    @Expose
    private String status;

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

    public String getLoaitruyen() {
        return loaitruyen;
    }

    public void setLoaitruyen(String loaitruyen) {
        this.loaitruyen = loaitruyen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
