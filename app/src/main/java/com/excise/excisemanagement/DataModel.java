package com.excise.excisemanagement;

public class DataModel {
    private String id;
    private String itemname;
    private int itemimage;

    public DataModel(String id, String itemname, int itemimage) {
        this.id = id;
        this.itemname = itemname;
        this.itemimage = itemimage;
    }

    public String getId() {
        return id;
    }

    public String getItemname() {
        return itemname;
    }

    public int getitemimage() {
        return itemimage;
    }
}