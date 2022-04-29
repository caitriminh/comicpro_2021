package com.triminh.comicpro.model.tusach;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TuSachChiTiet {
    @SerializedName("id_tusach")
    @Expose
    private Integer id_tusach;

    @SerializedName("tusach")
    @Expose
    private String tusach;

    @SerializedName("matua")
    @Expose
    private String matua;

    @SerializedName("tuatruyen")
    @Expose
    private String tuatruyen;

    @SerializedName("sotap")
    @Expose
    private Integer sotap;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("status")
    @Expose
    private String status;


    public Integer getId_tusach() {
        return id_tusach;
    }

    public void setId_tusach(Integer id_tusach) {
        this.id_tusach = id_tusach;
    }

    public String getTusach() {
        return tusach;
    }

    public void setTusach(String tusach) {
        this.tusach = tusach;
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

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Integer getSotap() {
        return sotap;
    }

    public void setSotap(Integer sotap) {
        this.sotap = sotap;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}




