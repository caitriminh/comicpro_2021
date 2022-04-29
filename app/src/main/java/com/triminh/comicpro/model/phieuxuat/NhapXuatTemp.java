package com.triminh.comicpro.model.phieuxuat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhapXuatTemp {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("matruyen")
    @Expose
    private String matruyen;

    @SerializedName("tentruyen")
    @Expose
    private String tentruyen;

    @SerializedName("matua")
    @Expose
    private String matua;

    @SerializedName("tuatruyen")
    @Expose
    private String tuatruyen;

    @SerializedName("donvitinh")
    @Expose
    private String donvitinh;

    @SerializedName("soluong")
    @Expose
    private Integer soluong;

    @SerializedName("dongia")
    @Expose
    private Double dongia;

    @SerializedName("thanhtien")
    @Expose
    private Double thanhtien;

    @SerializedName("nguoitd")
    @Expose
    private String nguoitd;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("tap")
    @Expose
    private Integer tap;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatruyen() {
        return matruyen;
    }

    public void setMatruyen(String matruyen) {
        this.matruyen = matruyen;
    }

    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public String getMatua() {
        return matua;
    }

    public void setMatua(String matua) {
        this.matua = matua;
    }

    public String getTuatruyen() {
        return tuatruyen;
    }

    public void setTuatruyen(String tuatruyen) {
        this.tuatruyen = tuatruyen;
    }

    public String getDonvitinh() {
        return donvitinh;
    }

    public void setDonvitinh(String donvitinh) {
        this.donvitinh = donvitinh;
    }

    public String getNguoitd() {
        return nguoitd;
    }

    public void setNguoitd(String nguoitd) {
        this.nguoitd = nguoitd;
    }

    public Double getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(Double thanhtien) {
        this.thanhtien = thanhtien;
    }

    public Double getDongia() {
        return dongia;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setDongia(Double dongia) {
        this.dongia = dongia;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Integer getTap() {
        return tap;
    }

    public void setTap(Integer tap) {
        this.tap = tap;
    }
}
