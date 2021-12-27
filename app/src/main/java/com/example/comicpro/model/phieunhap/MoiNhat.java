package com.example.comicpro.model.phieunhap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoiNhat {
    @SerializedName("tuatruyen")
    @Expose
    private String tuatruyen;
    @SerializedName("tentruyen")
    @Expose
    private String tentruyen;
    @SerializedName("slnhap")
    @Expose
    private Integer slnhap;
    @SerializedName("dongia")
    @Expose
    private Double dongia;

    @SerializedName("hinhanh2")
    @Expose
    private String hinhanh2;

    @SerializedName("tap")
    @Expose
    private Integer tap;

    public String getTuatruyen() {
        return tuatruyen;
    }

    public void setTuatruyen(String tuatruyen) {
        this.tuatruyen = tuatruyen;
    }

    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public Integer getSlnhap() {
        return slnhap;
    }

    public void setSlnhap(Integer slnhap) {
        this.slnhap = slnhap;
    }

    public Double getDongia() {
        return dongia;
    }

    public void setDongia(Double dongia) {
        this.dongia = dongia;
    }

    public String getHinhanh2() {
        return hinhanh2;
    }

    public void setHinhanh2(String hinhanh2) {
        this.hinhanh2 = hinhanh2;
    }

    public Integer getTap() {
        return tap;
    }

    public void setTap(Integer tap) {
        this.tap = tap;
    }
}
