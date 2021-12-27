package com.example.comicpro.model.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {

    @SerializedName("mamenu")
    @Expose
    private String mamenu;
    @SerializedName("menu")
    @Expose
    private String menu;

    @SerializedName("hinh")
    @Expose
    private String hinh;


    public String getMamenu() {
        return mamenu;
    }

    public void setMamenu(String mamenu) {
        this.mamenu = mamenu;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
