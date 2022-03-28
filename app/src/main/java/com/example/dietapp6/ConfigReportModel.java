package com.example.dietapp6;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfigReportModel{

   private int id;
   private double height;
   private Date date;
   private String sex;
   private double weightGoal;
   private double fatGoal;

    public ConfigReportModel(int id, double height, String date, String sex, double weightGoal) {

        DateFormat iso8601= new SimpleDateFormat( "dd-MM-yyyy" );

        try {
            this.id = id;
            this.height = height;
            this.date = iso8601.parse(date);
            this.sex = sex;
            this.weightGoal = weightGoal;
            this.fatGoal = -1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ConfigReportModel(int id, double height, String date, String sex, double weightGoal, double fatGoal) {

        DateFormat iso8601= new SimpleDateFormat( "dd-MM-yyyy" );
        try {
            this.id = id;
            this.height = height;
            this.date = iso8601.parse(date);
            this.sex = sex;
            this.weightGoal = weightGoal;
            this.fatGoal = fatGoal;
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(double weightGoal) {
        this.weightGoal = weightGoal;
    }

    public double getFatGoal() {
        return fatGoal;
    }

    public void setFatGoal(double fatGoal) {
        this.fatGoal = fatGoal;
    }

    @Override
    public String toString() {
        return "ConfigReportModel{" +
                "id=" + id +
                ", height=" + height +
                ", date=" + date +
                ", sex='" + sex + '\'' +
                ", weightGoal=" + weightGoal +
                ", fatGoal=" + fatGoal +
                '}';
    }
}
