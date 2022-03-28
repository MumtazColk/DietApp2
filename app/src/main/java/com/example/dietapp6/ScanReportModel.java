package com.example.dietapp6;

public class ScanReportModel {
    int id;
    String name;
    String bild;
    String barcode;
    double kalorieng;

    public ScanReportModel( String name, String bild, String barcode, double kalorieng) {
        this.id = -1;
        this.kalorieng = kalorieng;
        this.name = name;
        this.barcode = barcode;
        this.bild = bild;
    }

    public ScanReportModel( int id, String name, String bild, String barcode, double kalorieng) {
        this.id = id;
        this.kalorieng = kalorieng;
        this.name = name;
        this.barcode = barcode;
        this.bild = bild;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getKalorieng() {
        return kalorieng;
    }

    public void setKalorieng(double kalorieng) {
        this.kalorieng = kalorieng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        barcode = barcode;
    }

    public String getBild() {
        return bild;
    }

    public void setBild(String bild) {
        bild = bild;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", kalorieng=" + kalorieng;
    }
}
