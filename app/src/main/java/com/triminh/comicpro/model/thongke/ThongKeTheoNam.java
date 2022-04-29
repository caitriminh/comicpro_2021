package com.triminh.comicpro.model.thongke;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThongKeTheoNam {

    @SerializedName("stt")
    @Expose
    private Integer stt;

    @SerializedName("nam")
    @Expose
    private Integer nam;

    @SerializedName("thanhtien")
    @Expose
    private Double thanhtien;

    @SerializedName("thanhtien2")
    @Expose
    private Double thanhtien2;

    @SerializedName("socuon")
    @Expose
    private Integer socuon;

    @SerializedName("tongsocuon")
    @Expose
    private Integer tongsocuon;

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }


    public Integer getNam() {
        return nam;
    }

    public void setNam(Integer nam) {
        this.nam = nam;
    }

    public Double getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(Double thanhtien) {
        this.thanhtien = thanhtien;
    }

    public Double getThanhtien2() {
        return thanhtien2;
    }

    public void setThanhtien2(Double thanhtien2) {
        this.thanhtien2 = thanhtien2;
    }

    public Integer getSocuon() {
        return socuon;
    }

    public void setSocuon(Integer socuon) {
        this.socuon = socuon;
    }

    public Integer getTongSocuon() {
        return tongsocuon;
    }

    public void setTongSocuon(Integer tongSocuon) {
        this.tongsocuon = tongSocuon;
    }

}

