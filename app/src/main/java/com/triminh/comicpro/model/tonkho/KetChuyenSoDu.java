package com.triminh.comicpro.model.tonkho;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KetChuyenSoDu {

    @SerializedName("ky")
    @Expose
    private String ky;

    @SerializedName("ky2")
    @Expose
    private String ky2;

    @SerializedName("sodudauky")
    @Expose
    private Integer sodudauky;

    @SerializedName("slnhap")
    @Expose
    private Integer slnhap;

    @SerializedName("slxuat")
    @Expose
    private Integer slxuat;

    @SerializedName("slton")
    @Expose
    private Integer slton;

    public String getKy() {
        return ky;
    }

    public void setKy(String ky) {
        this.ky = ky;
    }

    public String getKy2() {
        return ky2;
    }

    public void setKy2(String ky2) {
        this.ky2 = ky2;
    }

    public Integer getSodauky() {
        return sodudauky;
    }

    public void setSodudauky(Integer sodudauky) {
        this.sodudauky = sodudauky;
    }

    public Integer getSlnhap() {
        return slnhap;
    }

    public void setSlnhap(String slnhap) {
        this.slnhap = slton;
    }

    public Integer getSlxuat() {
        return slxuat;
    }

    public void setSlxuat(Integer slxuat) {
        this.slxuat = slxuat;
    }

    public Integer getSlton() {
        return slton;
    }

    public void setSlton(Integer slton) {
        this.slton = slton;
    }


}

