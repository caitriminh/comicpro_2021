package com.triminh.comicpro.model.lichphathanh;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LichPhatHanh {

    @SerializedName("malich")
    @Expose
    private String malich;
    @SerializedName("manxb")
    @Expose
    private String manxb;
    @SerializedName("nhaxuatban")
    @Expose
    private String nhaxuatban;
    @SerializedName("ngaythang")
    @Expose
    private String ngaythang;
    @SerializedName("ghichu")
    @Expose
    private String ghichu;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("nam")
    @Expose
    private int nam;

    public String getMalich() {
        return malich;
    }

    public void setMalich(String malich) {
        this.malich = malich;
    }

    public String getManxb() {
        return manxb;
    }

    public void setManxb(String manxb) {
        this.manxb = manxb;
    }

    public String getNgaythang() {
        return ngaythang;
    }

    public void setNgaythang(String ngaythang) {
        this.ngaythang = ngaythang;
    }

    public String getNhaxuatban() {
        return nhaxuatban;
    }

    public void setNhaxuatban(String nhaxuatban) {
        this.nhaxuatban = nhaxuatban;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
    public String getHinh() {
        return hinh;
    }

    public void setHinh(String ghichu) {
        this.hinh = hinh;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }
}

