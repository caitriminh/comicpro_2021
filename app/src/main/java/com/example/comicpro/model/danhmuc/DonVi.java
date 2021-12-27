package com.example.comicpro.model.danhmuc;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonVi {

    @SerializedName("madonvi")
    @Expose
    private String madonvi;

    @SerializedName("donvi")
    @Expose
    private String donvi;
    @SerializedName("diachi")
    @Expose
    private String diachi;
    @SerializedName("sodt")
    @Expose
    private String sodt;
    @SerializedName("sofax")
    @Expose
    private String sofax;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("ghichu")
    @Expose
    private String ghichu;

    @SerializedName("status")
    @Expose
    private String status;

    public String getMadonvi() {
        return madonvi;
    }

    public void setMadonvi(String madonvi) {
        this.madonvi = madonvi;
    }


    public String getDonvi() {
        return donvi;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

