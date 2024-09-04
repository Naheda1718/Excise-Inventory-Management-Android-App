package com.excise.excisemanagement;

public class Adjustitems {
    String provincename;
    String date;

    public Adjustitems(String doneby) {
        this.doneby = doneby;
    }

    public String getDoneby() {
        return doneby;
    }

    public void setDoneby(String doneby) {
        this.doneby = doneby;
    }

    String doneby;

    public Adjustitems(String date,String provincename, String adjustvalue, String adjustreason,String doneby) {
        this.provincename = provincename;
        this.date = date;
        this.adjustvalue = adjustvalue;
        this.adjustreason = adjustreason;
        this.doneby=doneby;
    }

    String adjustvalue;

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdjustvalue() {
        return adjustvalue;
    }

    public void setAdjustvalue(String adjustvalue) {
        this.adjustvalue = adjustvalue;
    }

    public String getAdjustreason() {
        return adjustreason;
    }

    public void setAdjustreason(String adjustreason) {
        this.adjustreason = adjustreason;
    }

    String adjustreason;
}
