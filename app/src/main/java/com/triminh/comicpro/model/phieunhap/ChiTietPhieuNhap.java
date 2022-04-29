package com.triminh.comicpro.model.phieunhap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ChiTietPhieuNhap {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("matua")
    @Expose
    private String matua;

    @SerializedName("matruyen")
    @Expose
    private String matruyen;

    @SerializedName("tuatruyen")
    @Expose
    private String tuatruyen;

    @SerializedName("tentruyen")
    @Expose
    private String tentruyen;

    @SerializedName("tentruyen2")
    @Expose
    private String tentruyen2;

    @SerializedName("slnhap")
    @Expose
    private Integer slnhap;

    @SerializedName("tap")
    @Expose
    private String tap;

    @SerializedName("dongia")
    @Expose
    private Double dongia;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("ngaynhap")
    @Expose
    private Date ngaynhap;

    @SerializedName("status")
    @Expose
    private String status;


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

    public String getMatruyen() {
        return matruyen;
    }
    public void setMatruyen(String matruyen) {
        this.matruyen = matruyen;
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

    public String getTentruyen2() {
        return tentruyen2;
    }
    public void setTentruyen2(String tentruyen2) {
        this.tentruyen2 = tentruyen2;
    }

    public String getTap() {
        return tap;
    }

    public void setTap(String tap) {
        this.tap = tap;
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

    public Date getNgaynhap() {
        return ngaynhap;
    }
    public void setNgaynhap(Date ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public String getHinh() {
        return hinh;
    }
    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


