package com.triminh.comicpro.model.danhmuc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonViTinh {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("donvitinh")
    @Expose
    private String donvitinh;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("nguoitd")
    @Expose
    private String nguoitd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDonvitinh() {
        return donvitinh;
    }

    public void setDonvitinh(String donvitinh) {
        this.donvitinh = donvitinh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNguoitd() {
        return nguoitd;
    }

    public void setNguoitd(String nguoitd) {
        this.nguoitd = nguoitd;
    }
}
