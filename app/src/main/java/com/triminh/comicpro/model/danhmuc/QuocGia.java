package com.triminh.comicpro.model.danhmuc;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuocGia {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("quocgia")
    @Expose
    private String quocgia;

    @SerializedName("nguoitd")
    @Expose
    private String nguoitd;

    @SerializedName("hinh")
    @Expose
    private String hinh;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuocgia() {
        return quocgia;
    }

    public void setQuocgia(String quocgia) {
        this.quocgia = quocgia;
    }

    public String getNguoitd() {
        return nguoitd;
    }

    public void setNguoitd(String nguoitd) {
        this.nguoitd = nguoitd;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}

