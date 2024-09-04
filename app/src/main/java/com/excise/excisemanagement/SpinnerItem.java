package com.excise.excisemanagement;

public class SpinnerItem {
    private int id;
    private String label;

    public SpinnerItem(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}

