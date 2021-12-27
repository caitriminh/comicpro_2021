package com.example.comicpro.model.tentruyen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TenTruyen {
    @SerializedName("matua")
    @Expose
    private String matua;

    @SerializedName("matruyen")
    @Expose
    private String matruyen;

    @SerializedName("tentruyen")
    @Expose
    private String tentruyen;

    @SerializedName("tap")
    @Expose
    private Integer tap;

    @SerializedName("sotap")
    @Expose
    private Integer sotap;

    @SerializedName("giabia")
    @Expose
    private Double giabia;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("pdf")
    @Expose
    private String pdf;

    @SerializedName("ngayxuatban")
    @Expose
    private Date ngayxuatban;

    @SerializedName("sohuu")
    @Expose
    private String sohuu;

    @SerializedName("slton")
    @Expose
    private Integer slton;

    @SerializedName("ghichu")
    @Expose
    private String ghichu;

    @SerializedName("maquatang")
    @Expose
    private String maquatang;

    @SerializedName("quatang")
    @Expose
    private String quatang;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("loaibia")
    @Expose
    private String loaibia;

    @SerializedName("maloaibia")
    @Expose
    private Integer maloaibia;

    @SerializedName("donvitinh")
    @Expose
    private String donvitinh;

    @SerializedName("madvt")
    @Expose
    private Integer madvt;

    @SerializedName("sotrang")
    @Expose
    private Integer sotrang;

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

    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public Integer getTap() {
        return tap;
    }

    public void setTap(Integer tap) {
        this.tap = tap;
    }

    public Double getGiabia() {
        return giabia;
    }

    public void setGiabia(Double giabia) {
        this.giabia = giabia;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Date getNgayxuatban() {
        return ngayxuatban;
    }

    public void setNgayxuatban(Date ngayxuatban) {
        this.ngayxuatban = ngayxuatban;
    }


    public String getSohuu() {
        return sohuu;
    }

    public void setSohuu(String sohuu) {
        this.sohuu = sohuu;
    }

    public Integer getSlton() {
        return slton;
    }

    public void setSlton(Integer slton) {
        this.slton = slton;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getQuatang() {
        return quatang;
    }

    public void setQuatang(String quatang) {
        this.quatang = quatang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoaibia() {
        return loaibia;
    }

    public void setLoaibia(String loaibia) {
        this.loaibia = loaibia;
    }

    public Integer getMaloaibia() {
        return maloaibia;
    }

    public void setMaloaibia(Integer maloaibia) {
        this.maloaibia = maloaibia;
    }

    public String getDonvitinh() {
        return donvitinh;
    }

    public void setDonvitinh(String donvitinh) {
        this.donvitinh = donvitinh;
    }

    public Integer getMadvt() {
        return madvt;
    }

    public void setMadvt(Integer madvt) {
        this.madvt = madvt;
    }

    public Integer getSotrang() {
        return sotrang;
    }

    public void setSotrang(Integer sotrang) {
        this.sotrang = sotrang;
    }

    public String getMaquatang() {
        return maquatang;
    }

    public void setMaquatang(String maquatang) {
        this.maquatang = maquatang;
    }

    public Integer getSotap() {
        return sotap;
    }

    public void setSotap(Integer sotap) {
        this.sotap = sotap;
    }
}

