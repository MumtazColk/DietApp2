package com.example.dietapp6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Essensangabe extends AppCompatActivity {
        TextView Gramm,Titel,Kalorien,Menge;
        ListView RS, Angabe;
        Button Zurueck, hinzufuegen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essensangabe);
        Gramm = findViewById(R.id.Gramm);
        Titel = findViewById(R.id.title);
        Kalorien = findViewById(R.id.Kalorien);
        Menge = findViewById(R.id.Menge);
        RS = findViewById(R.id.RS);
        Zurueck = findViewById(R.id.Zurueck);
        hinzufuegen = findViewById(R.id.hinzufuegen);
        Angabe = findViewById(R.id.Angabe);

        ArrayList combinedList = new ArrayList();

        Datenbank db = new Datenbank(Essensangabe.this);
        db.getWritableDatabase();

        Date timestamp = new Date();
        SimpleDateFormat iso8601 =  new SimpleDateFormat( "dd-MM-yyyy" );

        if( db.getSelectQueryResult( "SELECT * FROM diet", "DietReportModel" ).size() > 0 ){

            ArrayList ateToday = db.getSelectQueryResult("SELECT * FROM diet WHERE timestamp LIKE '" + iso8601.format( timestamp ) + "%'", "DietReportModel");

            ArrayAdapter adapter = new ArrayAdapter( Essensangabe.this, android.R.layout.simple_list_item_1, ateToday );
            Angabe.setAdapter(adapter);

            double kcalSumme = 0;

            for( int i = 0; i < ateToday.size(); i++ ){

                kcalSumme = kcalSumme + (( DietReportModel ) ateToday.get( i )).getKcal();
            }

            if( db.getSelectQueryResult("SELECT * FROM config", "ConfigReportModel").size() > 0 && db.getSelectQueryResult("SELECT * FROM progress WHERE weight IS NOT NULL ORDER BY p_id DESC LIMIT 0, 1", "ProgressReportModel").size() > 0 ){

                ConfigReportModel config = (ConfigReportModel) db.getSelectQueryResult("SELECT * FROM config WHERE c_id = 1", "ConfigReportModel").get(0);
                ProgressReportModel lastWeightReport = ((ProgressReportModel) db.getSelectQueryResult("SELECT * FROM progress WHERE weight IS NOT NULL ORDER BY p_id DESC LIMIT 0, 1", "ProgressReportModel").get(0));
                double lastKnownWeight = lastWeightReport.getWeight();
                double kalorienbedarf;
                long alter;

                Date geburtsdatum = new Date(config.getDate().getTime());
                Date heute = new Date();
                alter = (heute.getTime() - geburtsdatum.getTime()) / 1000 / 60 / 60 / 24 / 365;

                if (config.getSex().equals( "Frau" ) ) {

                    kalorienbedarf = 655.1 + (9.6 * lastKnownWeight) + (1.8 * config.getHeight()) - (4.7 * alter);
                } else {

                    kalorienbedarf = 66.47 + (13.7 * lastKnownWeight) + (5 * config.getHeight()) - (6.8 * alter);
                }

                Kalorien.setText( Double.toString( kcalSumme ) + " /" + Double.toString( kalorienbedarf ) + " kcal" );
            }
            else{

                Kalorien.setText( Double.toString( kcalSumme ) + " kcal" );
            }
        }

        if( db.getSelectQueryResult("SELECT * FROM scan", "ScanReportModel").size() > 0 ) {
            combinedList.addAll(db.getSelectQueryResult("SELECT * FROM scan", "ScanReportModel"));
        }
        if( db.getSelectQueryResult("SELECT * FROM receipt", "ReceiptReportModel").size() > 0 ) {
            combinedList.addAll(db.getSelectQueryResult("SELECT * FROM receipt", "ReceiptReportModel"));
        }

        ArrayAdapter adapter = new ArrayAdapter( Essensangabe.this, android.R.layout.simple_list_item_1, combinedList );
        RS.setAdapter(adapter);
        RS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if( combinedList.get(position).getClass().getSimpleName().equals( "ScanReportModel" ) ){

                    Gramm.setText( Double.toString( ((ScanReportModel) combinedList.get(position)).getKalorieng() ) );
                    Titel.setText( ((ScanReportModel) combinedList.get(position)).getName() );

                    hinzufuegen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SimpleDateFormat iso8601 =  new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss" );
                            double kcalCount =  ( (Double.parseDouble( Gramm.getText().toString() )) / 100 ) * (Double.parseDouble( Menge.getText().toString() ));
                            db.diet( iso8601.format(timestamp),Double.toString(kcalCount), ((ScanReportModel) combinedList.get(position)).getId(), "s_id"  );
                            startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Essensangabe.class));
                        }
                    });
                }
                else{

                    Gramm.setText( Double.toString( ((ReceiptReportModel) combinedList.get(position)).getKcal() ) );
                    Titel.setText( ((ReceiptReportModel) combinedList.get(position)).getName() );

                    hinzufuegen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SimpleDateFormat iso8601 =  new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss" );
                            double kcalCount =  ( (Double.parseDouble( Gramm.getText().toString() )) / 100 ) * (Double.parseDouble( Menge.getText().toString() ));
                            db.diet( iso8601.format(timestamp), Double.toString(kcalCount), ((ReceiptReportModel) combinedList.get(position)).getId(), "r_id"  );
                            startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Essensangabe.class));
                        }
                    });
                }
            }
        });

        Zurueck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.MainActivity.class));
            }
        });
    }
}