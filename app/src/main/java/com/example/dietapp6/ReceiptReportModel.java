package com.example.dietapp6;

public class ReceiptReportModel {
    private int id;
    private String name;
    private double kcal;
    private String desc;

    public ReceiptReportModel(String name, double kcal, String desc) {
        this.id = -1;
        this.name = name;
        this.kcal = kcal;
        this.desc = desc;
    }

    public ReceiptReportModel(int id, String name, double kcal, String desc) {
        this.id = id;
        this.name = name;
        this.kcal = kcal;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return
                "name='" + name + '\'' +
                ", kcal=" + kcal ;
    }
}
