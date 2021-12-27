package com.example.comicpro.model.thongke;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ThongKeTheoNCC {

    @SerializedName("stt")
    @Expose
    private Integer stt;

    @SerializedName("tuatruyen")
    @Expose
    private String tuatruyen;

    @SerializedName("tap")
    @Expose
    private Integer tap;

    @SerializedName("thanhtien")
    @Expose
    private Double thanhtien;

    @SerializedName("tongthanhtien")
    @Expose
    private Double tongthanhtien;

    @SerializedName("slnhap")
    @Expose
    private Integer slnhap;

    @SerializedName("dongia")
    @Expose
    private Double dongia;

    @SerializedName("tongso")
    @Expose
    private Integer tongso;

    @SerializedName("madonvi")
    @Expose
    private String madonvi;

    @SerializedName("donvi")
    @Expose
    private String donvi;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("ngaynhap")
    @Expose
    private Date ngaynhap;

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

    public Double getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(Double thanhtien) {
        this.thanhtien = thanhtien;
    }

    public Integer getSlnhap() {
        return slnhap;
    }

    public void setSlnhap(Integer slnhap) {
        this.slnhap = slnhap;
    }

    public Integer getTongso() {
        return tongso;
    }

    public void setTongso(Integer tongso) {
        this.tongso = tongso;
    }

    public String getDonvi() {
        return donvi;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

    public String getMadonvi() {
        return madonvi;
    }

    public void setMadonvi(String madonvi) {
        this.madonvi = madonvi;
    }

    public Double getTongthanhtien() {
        return tongthanhtien;
    }

    public void setTongthanhtien(Double tongthanhtien) {
        this.tongthanhtien = tongthanhtien;
    }

    public Integer getTap() {
        return tap;
    }

    public void setTap(Integer tap) {
        this.tap = tap;
    }

    public Double getDongia() {
        return dongia;
    }

    public void setDongia(Double dongia) {
        this.dongia = dongia;
    }

    public String getTuatruyen() {
        return tuatruyen;
    }

    public void setTuatruyen(String tuatruyen) {
        this.tuatruyen = tuatruyen;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Date getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(Date ngaynhap) {
        this.ngaynhap = ngaynhap;
    }
}

