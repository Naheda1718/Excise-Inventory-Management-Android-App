package com.excise.excisemanagement;

public class Item {

  String datetime;
  String recvby;
  String rcvfrom;
  String toteid;
  String productname;
  String producttype;
  String lot;
  String weight;
  String quantity;
  String location;

    public Item(String datetime, String recvby, String rcvfrom, String toteid, String productname, String producttype, String lot, String weight, String quantity, String location) {
        this.datetime = datetime;
        this.recvby = recvby;
        this.rcvfrom = rcvfrom;
        this.toteid = toteid;
        this.productname = productname;
        this.producttype = producttype;
        this.lot = lot;
        this.weight = weight;
        this.quantity = quantity;
        this.location = location;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRecvby() {
        return recvby;
    }

    public void setRecvby(String recvby) {
        this.recvby = recvby;
    }

    public String getRcvfrom() {
        return rcvfrom;
    }

    public void setRcvfrom(String rcvfrom) {
        this.rcvfrom = rcvfrom;
    }

    public String getToteid() {
        return toteid;
    }

    public void setToteid(String toteid) {
        this.toteid = toteid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
// Add getters and setters for more details if needed
}
