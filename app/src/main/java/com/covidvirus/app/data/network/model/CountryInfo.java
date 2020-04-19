package com.covidvirus.app.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CountryInfo implements Serializable {

    @Expose
    @SerializedName("iso2")
    private String isoName;

    @Expose
    @SerializedName("flag")
    private String flag;

    public String getIsoName() {
        return isoName;
    }

    public void setIsoName(String isoName) {
        this.isoName = isoName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
