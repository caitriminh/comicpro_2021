package com.example.comicpro.model.nguoidung;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("tendangnhap")
    @Expose
    private String tendangnhap;

    @SerializedName("hoten")
    @Expose
    private String hoten;

    @SerializedName("matkhau")
    @Expose
    private String matkhau;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("truycap")
    @Expose
    private String truycap;

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
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

    public String getTruycap() {
        return truycap;
    }

    public void setTruycap(String truycap) {
        this.truycap = truycap;
    }
}
