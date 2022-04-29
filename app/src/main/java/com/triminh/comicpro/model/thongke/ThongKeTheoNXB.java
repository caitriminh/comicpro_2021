package com.triminh.comicpro.model.thongke;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThongKeTheoNXB {

    @SerializedName("stt")
    @Expose
    private Integer stt;

    @SerializedName("manxb")
    @Expose
    private String manxb;

    @SerializedName("nhaxuatban")
    @Expose
    private String nhaxuatban;

    @SerializedName("thanhtien")
    @Expose
    private String thanhtien;

    @SerializedName("thanhtien2")
    @Expose
    private String thanhtien2;

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

    public String getManxb() {
        return manxb;
    }

    public void setManxb(String manxb) {
        this.manxb = manxb;
    }

    public String getNhaxuatban() {
        return nhaxuatban;
    }

    public void setNhaxuatban(String nhaxuatban) {
        this.nhaxuatban = nhaxuatban;
    }

    public String getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(String thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getThanhtien2() {
        return thanhtien2;
    }

    public void setThanhtien2(String thanhtien2) {
        this.thanhtien2 = thanhtien2;
    }

    public Integer getSocuon() {
        return socuon;
    }

    public void setSocuon(Integer socuon) {
        this.socuon = socuon;
    }

    public Integer getTongsocuon() {
        return tongsocuon;
    }

    public void setTongsocuon(Integer tongsocuon) {
        this.tongsocuon = tongsocuon;
    }



}

