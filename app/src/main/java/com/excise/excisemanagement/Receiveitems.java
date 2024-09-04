package com.excise.excisemanagement;

public class Receiveitems {

    String datetime;

    public Receiveitems(String datetime, String province, String excise, String stamp, String location, String addcommnt, String doneby) {
        this.datetime = datetime;
        this.province = province;
        this.excise = excise;
        this.cmnt = cmnt;
        this.stamp = stamp;
        this.location = location;
      //  this.document = document;
        this.addcommnt = addcommnt;
        this.doneby = doneby;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getExcise() {
        return excise;
    }

    public void setExcise(String excise) {
        this.excise = excise;
    }

    public String getCmnt() {
        return cmnt;
    }

    public void setCmnt(String cmnt) {
        this.cmnt = cmnt;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

//    public String getDocument() {
//        return document;
//    }
//
//    public void setDocument(String document) {
//        this.document = document;
//    }

    public String getAddcommnt() {
        return addcommnt;
    }

    public void setAddcommnt(String addcommnt) {
        this.addcommnt = addcommnt;
    }

    public String getDoneby() {
        return doneby;
    }

    public void setDoneby(String doneby) {
        this.doneby = doneby;
    }

    String province;
    String excise;
    String cmnt;
    String stamp;
    String location;
    //String document;
    String addcommnt;
    String doneby;

}
