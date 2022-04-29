package com.triminh.comicpro.model.tentruyen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChiTietTenTruyen_TheoNXB {

    @SerializedName("matruyen")
    @Expose
    private String matruyen;

    @SerializedName("manxb")
    @Expose
    private String manxb;

    @SerializedName("dongia")
    @Expose
    private String dongia;

    @SerializedName("ngaynhap")
    @Expose
    private String ngaynhap;

    @SerializedName("tap")
    @Expose
    private String tap;

    @SerializedName("tuatruyen")
    @Expose
    private String tuatruyen;

    @SerializedName("TongTien")
    @Expose
    private Double TongTien;


    public String getMatruyen() {
        return matruyen;
    }

    public void setMatruyen(String matruyen) {
        this.matruyen = matruyen;
    }

    public String getManxb() {
        return manxb;
    }

    public void setManxb(String manxb) {
        this.manxb = manxb;
    }

    public String getDongia() {
        return dongia;
    }

    public void setDongia(String dongia) {
        this.dongia = dongia;
    }

    public String getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public String getTap() {
        return tap;
    }

    public void setTap(String tap) {
        this.tap = tap;
    }

    public String getTuatruyen() {
        return tuatruyen;
    }

    public void setTuatruyen(String tuatruyen) {
        this.tuatruyen = tuatruyen;
    }

    public Double getTongTien() {
        return TongTien;
    }

    public void setTongTien(Double tongTien) {
        TongTien = tongTien;
    }
}

