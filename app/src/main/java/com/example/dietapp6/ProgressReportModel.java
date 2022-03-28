package com.example.dietapp6;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProgressReportModel {
    private int id;
    private Date date;
    private double weight;
    private double fatPercentage;

    public ProgressReportModel(int id, String date, double weight, double fatPercentage) {

        DateFormat iso8601= new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss" );

        try {
            this.id = id;
            this.date = iso8601.parse(date);
            this.weight = weight;
            this.fatPercentage = fatPercentage;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ProgressReportModel(int id, String date, double value, String valueType) {

        DateFormat iso8601= new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss" );

        try {
            this.id = id;
            this.date = iso8601.parse(date);

            switch( valueType ){

                case "weight":

                    weight = value;
                    fatPercentage = -1;
                break;
                case "fat":

                    fatPercentage = value;
                    weight = -1;
                break;
                default:

                    weight = -1;
                    fatPercentage = -1;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getFatPercentage() {
        return fatPercentage;
    }

    public void setFatPercentage(double fatPercentage) {
        this.fatPercentage = fatPercentage;
    }

    @Override
    public String toString() {
        return "ProgressReportModel{" +
                "id=" + id +
                ", date=" + date +
                ", weight=" + weight +
                ", fatPercentage=" + fatPercentage +
                '}';
    }
}
