package com.example.comicpro.model.phieuxuat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ChiTietPhieuXuat {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("matua")
    @Expose
    private String matua;
    @SerializedName("tuatruyen")
    @Expose
    private String tuatruyen;
    @SerializedName("tentruyen")
    @Expose
    private String tentruyen;

    @SerializedName("tap")
    @Expose
    private Integer tap;

    @SerializedName("slxuat")
    @Expose
    private Integer slxuat;

    @SerializedName("dongia")
    @Expose
    private Double dongia;

    @SerializedName("ngaynhap")
    @Expose
    private Date ngaynhap;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Double getDongia() {
        return dongia;
    }

    public void setTap(Integer tap) {
        this.tap = tap;
    }

    public Date getNgaynhap() {
        return ngaynhap;
    }

    public void setDongia(Double dongia) {
        this.dongia = dongia;
    }

    public void setSlxuat(Integer slxuat) {
        this.slxuat = slxuat;
    }

    public Integer getSlxuat() {
        return slxuat;
    }

    public void setNgaynhap(Date ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public Integer getTap() {
        return tap;
    }

}


