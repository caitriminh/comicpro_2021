package com.triminh.comicpro.model.danhmuc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TacGia {
    @SerializedName("matacgia")
    @Expose
    private String matacgia;
    @SerializedName("tacgia")
    @Expose
    private String tacgia;

    @SerializedName("status")
    @Expose
    private String status;

    public String getMatacgia() {
        return matacgia;
    }

    public void setMatacgia(String matacgia) {
        this.matacgia = matacgia;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
