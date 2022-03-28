package com.example.dietapp6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Rezept_ansehen extends AppCompatActivity {



    TextView rname,kcalg,Beschreibunga;
    Button RezeptB,RezeptH,Zurueck;
    ListView Liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezept_ansehen);
        rname = findViewById(R.id.Rezeptname);
        kcalg= findViewById(R.id.Kcalg);
        Beschreibunga = findViewById(R.id.Beschreibunga);
        RezeptB = findViewById(R.id.RezeptB);
        RezeptH = findViewById(R.id.hinzufuegen);
        Zurueck = findViewById(R.id.Zurueck);
        Liste = findViewById(R.id.Produktea);


        Datenbank db =  new Datenbank( Rezept_ansehen.this );

        if( db.getSelectQueryResult("SELECT * FROM receipt", "ReceiptReportModel").size() > 0 ){

            ArrayList<ReceiptReportModel> receipts = db.getSelectQueryResult("SELECT * FROM receipt", "ReceiptReportModel");
            ArrayAdapter<ReceiptReportModel> adapter = new ArrayAdapter(Rezept_ansehen.this, android.R.layout.simple_list_item_1,receipts);
            Liste.setAdapter(adapter);

            Liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    rname.setText( receipts.get(position).getName() );
                    kcalg.setText( Double.toString( receipts.get(position).getKcal() ));
                    Beschreibunga.setText( receipts.get(position).getDesc() );

                    RezeptB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(Rezept_ansehen.this, Rezept_bearbeiten.class);
                            intent.putExtra("receipt_id", Integer.toString( receipts.get(position).getId() ));
                            startActivity(intent);
                            //startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Rezept_bearbeiten.class));
                        }
                    });
                }
            });


        }

        RezeptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Rezept_bearbeiten.class));
            }
        });

        RezeptH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Rezept_hinzufugen.class));
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