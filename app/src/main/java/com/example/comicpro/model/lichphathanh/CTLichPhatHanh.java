package com.example.comicpro.model.lichphathanh;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CTLichPhatHanh {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("malich")
    @Expose
    private String malich;

    @SerializedName("ngayphathanh")
    @Expose
    private String ngayphathanh;

    @SerializedName("tuatruyen")
    @Expose
    private String tuatruyen;

    @SerializedName("giabia")
    @Expose
    private String giabia;

    @SerializedName("ghichu")
    @Expose
    private String ghichu;

    @SerializedName("nguoitd")
    @Expose
    private String nguoitd;

    @SerializedName("damua")
    @Expose
    private Integer damua;

    @SerializedName("tonggiabia")
    @Expose
    private String tonggiabia;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getMalich() {
        return malich;
    }
    public void setMalich(String malich) {
        this.malich = malich;
    }

    public String getNgayphathanh() {
        return ngayphathanh;
    }
    public void setNgayphathanh(String ngayphathanh) {
        this.ngayphathanh = ngayphathanh;
    }

    public String getTuatruyen() {
        return tuatruyen;
    }
    public void setTuatruyen(String tuatruyen) {
        this.tuatruyen = tuatruyen;
    }

    public String getGiabia() {
        return giabia;
    }
    public void setGiabia(String giabia) {
        this.giabia = giabia;
    }

    public String getGhichu() {
        return ghichu;
    }
    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Integer getDamua() {
        return damua;
    }
    public void setDamua(Integer damua) {
        this.damua = damua;
    }

    public String getTonggiabia() {
        return tonggiabia;
    }
    public void setTonggiabia(String tonggiabia) {
        this.tonggiabia = tonggiabia;
    }
}
