package com.example.comicpro.model.danhmuc;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuaTang {

    @SerializedName("maquatang")
    @Expose
    private String maquatang;

    @SerializedName("quatang")
    @Expose
    private String quatang;

    @SerializedName("status")
    @Expose
    private String status;

    public String getMaquatang() {
        return maquatang;
    }

    public void setMaquatang(String maquatang) {
        this.maquatang = maquatang;
    }

    public String getQuatang() {
        return quatang;
    }

    public void setQuatang(String quatang) {
        this.quatang = quatang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

