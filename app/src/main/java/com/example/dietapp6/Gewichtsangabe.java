package com.example.dietapp6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Gewichtsangabe extends AppCompatActivity {
    TextView gewichta,Ls,tg,fa,la,tk;
    Button Zuruck,Hinzufugen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gewichtsangabe);

        gewichta = findViewById(R.id.gewichta);
        Ls = findViewById(R.id.LS);
        tg = findViewById(R.id.TG);
        fa = findViewById(R.id.FA);
        la = findViewById(R.id.LA);
        tk = findViewById(R.id.TK);
        Zuruck = findViewById(R.id.Zurueck);
        Hinzufugen = findViewById(R.id.hinzufuegen);

        Datenbank db = new Datenbank(Gewichtsangabe.this);
        SimpleDateFormat iso8601= new SimpleDateFormat( "dd-MM-yyyy" );

        if( db.getSelectQueryResult("SELECT * FROM progress", "ProgressReportModel").size() >0 ) {

            if( db.getSelectQueryResult("SELECT * FROM progress WHERE weight IS NOT NULL", "ProgressReportModel").size() > 0 ){

                ProgressReportModel lastWeightReport = ((ProgressReportModel) db.getSelectQueryResult("SELECT * FROM progress WHERE weight IS NOT NULL ORDER BY p_id DESC LIMIT 0, 1", "ProgressReportModel").get(0));
                double lastKnownWeight = lastWeightReport.getWeight();
                gewichta.setText(Double.toString(lastKnownWeight).substring(0, Double.toString(lastKnownWeight).indexOf(".") + 2));
                Ls.setText(iso8601.format(lastWeightReport.getDate()));
            }

            if( db.getSelectQueryResult("SELECT * FROM progress WHERE fat_percentage IS NOT NULL", "ProgressReportModel").size() > 0 ){
                ProgressReportModel lastFatReport = ((ProgressReportModel) db.getSelectQueryResult("SELECT * FROM progress WHERE fat_percentage IS NOT NULL ORDER BY p_id DESC LIMIT 0, 1", "ProgressReportModel").get(0));
                double lastKnownFat = lastFatReport.getFatPercentage();
                fa.setText(Double.toString(lastKnownFat).substring(0, Double.toString(lastKnownFat).indexOf(".") + 2));
                la.setText(iso8601.format(lastFatReport.getDate()));
            }
        }

        Hinzufugen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Datenbank db = new Datenbank( Gewichtsangabe.this );
                SimpleDateFormat iso8601= new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss" );
                Date timestamp = new Date();

                if( !tg.getText().toString().equals("") && !tk.getText().toString().equals("") ) {

                    db.progress(iso8601.format(timestamp), tg.getText().toString().substring(0, tg.getText().toString().indexOf(".") + 2), tk.getText().toString().substring(0, tk.getText().toString().indexOf(".") + 2));
                }
                else if( !tg.getText().toString().equals("") ){

                    db.progress2( iso8601.format(timestamp), tg.getText().toString().substring(0, tg.getText().toString().indexOf(".") + 2), "weight");
                }
                else{

                    if( !tk.getText().toString().equals("") ) {
                        db.progress2(iso8601.format(timestamp), tk.getText().toString().substring(0, tk.getText().toString().indexOf(".") + 2), "fat");
                    }
                }
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Gewichtsangabe.class));

            }
        });

        Zuruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.MainActivity.class));
            }
        });
    }
}