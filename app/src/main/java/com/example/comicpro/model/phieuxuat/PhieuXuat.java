package com.example.comicpro.model.phieuxuat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhieuXuat {
    @SerializedName("maphieu")
    @Expose
    private String maphieu;
    @SerializedName("madonvi")
    @Expose
    private String madonvi;
    @SerializedName("donvi")
    @Expose
    private String donvi;
    @SerializedName("ngaynhap")
    @Expose
    private String ngaynhap;
    @SerializedName("diengiai")
    @Expose
    private String diengiai;

    @SerializedName("thanhtien")
    @Expose
    private Double thanhtien;

    @SerializedName("tongthanhtien")
    @Expose
    private Double tongthanhtien;

    @SerializedName("nguoilap")
    @Expose
    private String nguoilap;

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

    public Double getThanhtien() {
        return thanhtien;
    }
    public void setThanhtien(Double thanhtien) {
        this.thanhtien = thanhtien;
    }

    public Double getTongthanhtien() {
        return tongthanhtien;
    }
    public void setTongthanhtien(String tenkho) {
        this.tongthanhtien = tongthanhtien;
    }

    public String getNguoilap() {
        return nguoilap;
    }
    public void setNguoilap(String nguoilap) {
        this.nguoilap = nguoilap;
    }


}
