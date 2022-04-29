package com.triminh.comicpro.model.tusach;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TuSach {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("tusach")
    @Expose
    private String tusach;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTusach() {
        return tusach;
    }

    public void setTusach(String tusach) {
        this.tusach = tusach;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}

