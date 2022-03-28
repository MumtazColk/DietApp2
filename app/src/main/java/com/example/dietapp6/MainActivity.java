package com.example.dietapp6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button E,RA,GA,EA,S;
    TextView kalorien, aufgenommen, gewichtT, gewichtTime, fettT, fettTime , zielerreichung;
    LineChart lineGraph1, lineGraph2;
    BarChart barGraph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        E = findViewById(R.id.Einstellung);
        RA = findViewById(R.id.RezepteAnsehen);
        GA = findViewById(R.id.GA);
        EA = findViewById(R.id.Essenangabe);
        S = findViewById(R.id.Scan);

        kalorien = findViewById(R.id.Edit9);
        aufgenommen = findViewById(R.id.Kalorien);
        gewichtT = findViewById(R.id.Edit13);
        gewichtTime = findViewById(R.id.Edit14);
        fettT = findViewById(R.id.Edit15);
        fettTime = findViewById(R.id.Stand);

        Datenbank db = new Datenbank(MainActivity.this);
        SimpleDateFormat iso8601= new SimpleDateFormat( "dd-MM-yyyy" );

        if( db.getSelectQueryResult("SELECT * FROM config", "ConfigReportModel").size() > 0  ) {

            ConfigReportModel config = (ConfigReportModel) db.getSelectQueryResult("SELECT * FROM config WHERE c_id = 1", "ConfigReportModel").get(0);

            if (db.getSelectQueryResult("SELECT * FROM progress", "ProgressReportModel").size() > 0) {

                if( db.getSelectQueryResult("SELECT * FROM progress WHERE weight IS NOT NULL", "ProgressReportModel").size() > 0 ){

                    double kalorienbedarf;
                    long alter;

                    ProgressReportModel lastWeightReport = ((ProgressReportModel) db.getSelectQueryResult("SELECT * FROM progress WHERE weight IS NOT NULL ORDER BY p_id DESC LIMIT 0, 1", "ProgressReportModel").get(0));
                    double lastKnownWeight = lastWeightReport.getWeight();
                    gewichtT.setText(Double.toString(lastKnownWeight).substring(0, Double.toString(lastKnownWeight).indexOf(".") + 2));
                    gewichtTime.setText(iso8601.format(lastWeightReport.getDate()));

                    Date geburtsdatum = new Date(config.getDate().getTime());
                    Date heute = new Date();
                    alter = (heute.getTime() - geburtsdatum.getTime()) / 1000 / 60 / 60 / 24 / 365;
                    if (config.getSex().equals( "Frau" )) {

                        kalorienbedarf = 655.1 + (9.6 * lastKnownWeight) + (1.8 * config.getHeight()) - (4.7 * alter);
                    } else {

                        kalorienbedarf = 66.47 + (13.7 * lastKnownWeight) + (5 * config.getHeight()) - (6.8 * alter);
                    }
                    kalorien.setText(Double.toString(kalorienbedarf).substring(0, Double.toString(kalorienbedarf).indexOf(".") + 2));

                    double summeKcalAufgenommen = 0;
                    ArrayList<DietReportModel> eatenToday = db.getSelectQueryResult("SELECT * FROM diet WHERE timestamp LIKE '" + iso8601.format(heute) +"%'", "DietReportModel");

                    if (eatenToday.size() > 0) {

                        for (int i = 0; i < eatenToday.size(); i++) {

                            summeKcalAufgenommen = summeKcalAufgenommen + ((DietReportModel) eatenToday.get(i)).getKcal();
                        }

                        aufgenommen.setText(Double.toString(summeKcalAufgenommen).substring(0, Double.toString(summeKcalAufgenommen).indexOf(".") + 2) + " /" + Double.toString(kalorienbedarf).substring(0, Double.toString(kalorienbedarf).indexOf(".") + 2));
                    } else {

                        aufgenommen.setText(Double.toString(summeKcalAufgenommen) + " /" + Double.toString(kalorienbedarf).substring(0, Double.toString(kalorienbedarf).indexOf(".") + 2));
                    }
                }

                if( db.getSelectQueryResult("SELECT * FROM progress WHERE fat_percentage IS NOT NULL", "ProgressReportModel").size() > 0 ){

                    ProgressReportModel lastFatReport = ((ProgressReportModel) db.getSelectQueryResult("SELECT * FROM progress WHERE fat_percentage IS NOT NULL ORDER BY p_id DESC LIMIT 0, 1", "ProgressReportModel").get(0));

                    double lastKnownFat = lastFatReport.getFatPercentage();

                    fettT.setText(Double.toString(lastKnownFat).substring(0, Double.toString(lastKnownFat).indexOf(".") + 2));
                    fettTime.setText(iso8601.format(lastFatReport.getDate()));
                }
            }
        }

        E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Einstellung.class));
            }
        });

        RA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Rezept_ansehen.class));
            }
        });
        GA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Gewichtsangabe.class));
            }
        });
        EA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Essensangabe.class));
            }
        });
        S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Scanner.class));
            }
        });



    }
}