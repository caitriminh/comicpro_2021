package com.triminh.comicpro.model.danhmuc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoaiBia {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("loaibia")
    @Expose
    private String loaibia;

    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoaibia() {
        return loaibia;
    }

    public void setLoaibia(String loaibia) {
        this.loaibia = loaibia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
