package com.triminh.comicpro.model.tonkho;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ChiTietTonKho {

    @SerializedName("matruyen")
    @Expose
    private String matruyen;

    @SerializedName("tap")
    @Expose
    private Integer tap;

    @SerializedName("sotap")
    @Expose
    private Integer sotap;

    @SerializedName("tuatruyen")
    @Expose
    private String tuatruyen;

    @SerializedName("tentruyen")
    @Expose
    private String tentruyen;

    @SerializedName("soducuoiky")
    @Expose
    private String soducuoiky;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("giabia")
    @Expose
    private double giabia;

    @SerializedName("ngayxuatban")
    @Expose
    private Date ngayxuatban;

    @SerializedName("quatang")
    @Expose
    private String quatang;

    public String getMatruyen() {
        return matruyen;
    }
    public void setMatruyen(String matruyen) {
        this.matruyen = matruyen;
    }

    public Integer getTap() {
        return tap;
    }
    public void setTap(Integer tap) {
        this.tap = tap;
    }

    public Integer getSotap() {
        return sotap;
    }

    public void setSotap(Integer sotap) {
        this.sotap = sotap;
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

    public String getSoducuoiky() {
        return soducuoiky;
    }
    public void setSoducuoiky(String soducuoiky1) {
        this.soducuoiky = soducuoiky;
    }

    public String getHinh() {
        return hinh;
    }
    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public double getGiabia() {
        return giabia;
    }
    public void setGiabia(double giabia) {
        this.giabia = giabia;
    }

    public Date getNgayxuatban() {
        return ngayxuatban;
    }

    public void setNgayxuatban(Date ngayxuatban) {
        this.ngayxuatban = ngayxuatban;
    }

    public String getQuatang() {
        return quatang;
    }

    public void setQuatang(String quatang) {
        this.quatang = quatang;
    }
}

