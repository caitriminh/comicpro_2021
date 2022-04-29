package com.triminh.comicpro.model.tonkho;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TonKho {
    @SerializedName("matua")
    @Expose
    private String matua;
    @SerializedName("tuatruyen")
    @Expose
    private String tuatruyen;
    @SerializedName("tacgia")
    @Expose
    private String tacgia;
    @SerializedName("soluong")
    @Expose
    private String soluong;
    @SerializedName("hinh")
    @Expose
    private String hinh;

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

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

}




