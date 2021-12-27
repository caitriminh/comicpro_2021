package com.example.comicpro.model.danhmuc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhaXuatBan {
    @SerializedName("manxb")
    @Expose
    private String manxb;

    @SerializedName("manxb2")
    @Expose
    private String manxb2;

    @SerializedName("nhaxuatban")
    @Expose
    private String nhaxuatban;
    @SerializedName("diachi")
    @Expose
    private String diachi;
    @SerializedName("sodt")
    @Expose
    private String sodt;

    @SerializedName("sofax")
    @Expose
    private String sofax;

    @SerializedName("ghichu")
    @Expose
    private String ghichu;

    public String getManxb() {
        return manxb;
    }

    public void setManxb(String manxb) {
        this.manxb = manxb;
    }

    public String getManxb2() {
        return manxb2;
    }

    public void setManxb2(String manxb2) {
        this.manxb2 = manxb2;
    }

    public String getNhaxuatban() {
        return nhaxuatban;
    }

    public void setNhaxuatban(String nhaxuatban) {
        this.nhaxuatban = nhaxuatban;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSodt() {
        return sodt;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }

    public String getSofax() {
        return sofax;
    }

    public void setSofax(String sofax) {
        this.sofax = sofax;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

}
