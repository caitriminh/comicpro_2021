package com.example.comicpro.model.phieunhap;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CTPhieuNhapNCC {


    @SerializedName("ngaynhap")
    @Expose
    private String ngaynhap;
    @SerializedName("tuatruyen")
    @Expose
    private String tuatruyen;
    @SerializedName("tentruyen")
    @Expose
    private String tentruyen;
    @SerializedName("slnhap_dongia")
    @Expose
    private String slnhapDongia;
    @SerializedName("hinh")
    @Expose
    private String hinh;



    public String getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
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

    public String getSlnhapDongia() {
        return slnhapDongia;
    }

    public void setSlnhapDongia(String slnhapDongia) {
        this.slnhapDongia = slnhapDongia;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

}
