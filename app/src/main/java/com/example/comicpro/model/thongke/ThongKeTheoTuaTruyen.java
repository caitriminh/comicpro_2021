package com.example.comicpro.model.thongke;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThongKeTheoTuaTruyen {

    @SerializedName("stt")
    @Expose
    private Integer stt;

    @SerializedName("matua")
    @Expose
    private String matua;

    @SerializedName("tuatruyen")
    @Expose
    private String tuatruyen;

    @SerializedName("thanhtien")
    @Expose
    private String thanhtien;

    @SerializedName("thanhtien2")
    @Expose
    private String thanhtien2;

    @SerializedName("socuon")
    @Expose
    private Integer socuon;

    @SerializedName("tongsocuon")
    @Expose
    private Integer tongsocuon;

    public Integer getStt() {
        return stt;
    }
    public void setStt(Integer stt) {
        this.stt = stt;
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

    public String getThanhtien() {
        return thanhtien;
    }
    public void setThanhtien(String thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getThanhtien2() {
        return thanhtien2;
    }
    public void setThanhtien2(String thanhtien2) {
        this.thanhtien2 = thanhtien2;
    }

    public Integer getSocuon() {
        return socuon;
    }
    public void setSocuon(Integer socuon) {
        this.socuon = socuon;
    }

    public Integer getTongsocuon() {
        return tongsocuon;
    }
    public void setTongsocuon(Integer tongsocuon) {
        this.tongsocuon = tongsocuon;
    }



}

