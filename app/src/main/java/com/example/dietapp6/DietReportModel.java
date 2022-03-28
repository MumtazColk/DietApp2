package com.example.dietapp6;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DietReportModel {

    private int id;
    private Date date;
    private double kcal;
    private int s_id;
    private int r_id;

    public DietReportModel(int id, String date, double kcal) {

        DateFormat iso8601= new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss" );

        try {

            this.id = id;
            this.date = iso8601.parse(date);
            this.kcal = kcal;
            r_id = -1;
            s_id = -1;

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public DietReportModel(int id, String date, double kcal, int foreignId, String foreignTable) {

        DateFormat iso8601= new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss" );

        try {

            this.id = id;
            this.date = iso8601.parse(date);
            this.kcal = kcal;

            switch(foreignTable){

                case "scan":

                    s_id = foreignId;
                    r_id = -1;
                break;

                case "receipt":
                    r_id = foreignId;
                    s_id = -1;
                break;
                default:

                    r_id = -1;
                    s_id = -1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    @Override
    public String toString() {
        return "kcal=" + kcal;
    }
}
