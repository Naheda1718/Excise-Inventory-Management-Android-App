package com.excise.excisemanagement;

public class Inprocessitems {
    String datetime;



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

    public String getStamptaken() {
        return stamptaken;
    }

    public void setStamptaken(String stamptaken) {
        this.stamptaken = stamptaken;
    }

    public String getStampmatches() {
        return stampmatches;
    }

    public void setStampmatches(String stampmatches) {
        this.stampmatches = stampmatches;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getPackagedate() {
        return packagedate;
    }

    public void setPackagedate(String packagedate) {
        this.packagedate = packagedate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStamptoinv() {
        return stamptoinv;
    }

    public void setStamptoinv(String stamptoinv) {
        this.stamptoinv = stamptoinv;
    }

    public String getDamagestamp() {
        return damagestamp;
    }

    public void setDamagestamp(String damagestamp) {
        this.damagestamp = damagestamp;
    }

    public String getReasndamage() {
        return reasndamage;
    }

    public void setReasndamage(String reasndamage) {
        this.reasndamage = reasndamage;
    }

    public String getDoneby() {
        return doneby;
    }

    public void setDoneby(String doneby) {
        this.doneby = doneby;
    }

    public String getInprocessid() {
        return inprocessid;
    }

    public void setInprocessid(String inprocessid) {
        this.inprocessid = inprocessid;
    }

    public Inprocessitems(String inprocessid,String datetime, String province, String stamptaken, String stampmatches, String product, String lot, String packagedate, String location, String stamptoinv, String damagestamp, String reasndamage, String doneby) {
        this.datetime = datetime;
        this.province = province;
        this.stamptaken = stamptaken;
        this.stampmatches = stampmatches;
        this.product = product;
        this.lot = lot;
        this.packagedate = packagedate;
        this.location = location;
        this.stamptoinv = stamptoinv;
        this.damagestamp = damagestamp;
        this.reasndamage = reasndamage;
        this.doneby = doneby;
        this.inprocessid=inprocessid;
    }

    String province;
    String stamptaken;
    String stampmatches;
    String product;
    String lot;
    String packagedate;
    String location;
    String stamptoinv;
    String damagestamp;
    String reasndamage;
    String doneby;

    String inprocessid;


}
