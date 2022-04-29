package com.triminh.comicpro.model.phieunhap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhieuNhap {
    @SerializedName("maphieu")
    @Expose
    private String maphieu;

    @SerializedName("maphieu2")
    @Expose
    private String maphieu2;

    @SerializedName("madonvi")
    @Expose
    private String madonvi;
    @SerializedName("donvi")
    @Expose
    private String donvi;
    @SerializedName("ngaynhap")
    @Expose
    private String ngaynhap;

    @SerializedName("ngaynhap2")
    @Expose
    private String ngaynhap2;

    @SerializedName("diengiai")
    @Expose
    private String diengiai;

    @SerializedName("tongtien")
    @Expose
    private String tongtien;

    @SerializedName("makho")
    @Expose
    private String makho;

    @SerializedName("tenkho")
    @Expose
    private String tenkho;

    @SerializedName("nguoilap")
    @Expose
    private String nguoilap;

    @SerializedName("tongthanhtien")
    @Expose
    private String tongthanhtien;

    public String getMaphieu() {
        return maphieu;
    }
    public void setMaphieu(String maphieu) {
        this.maphieu = maphieu;
    }

    public String getMadonvi() {
        return madonvi;
    }
    public void setMadonvi(String madonvi) {
        this.madonvi = madonvi;
    }

    public String getDonvi() {
        return donvi;
    }
    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

    public String getNgaynhap() {
        return ngaynhap;
    }
    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public String getDiengiai() {
        return diengiai;
    }
    public void setDiengiai(String diengiai) {
        this.diengiai = diengiai;
    }

    public String getTongtien() {
        return tongtien;
    }

    public String getTongthanhtien() {
        return tongthanhtien;
    }
    public void setTongthanhtien(String tongthanhtien) {
        this.tongthanhtien = tongthanhtien;
    }




}
