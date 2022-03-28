package com.example.dietapp6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Rezept_hinzufugen extends AppCompatActivity {
    EditText Name,kcalgramm,beschreibung;
    Button Zuruck,hinzufugen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezept_hinzufugen);

        Name = findViewById(R.id.Name);
        kcalgramm = findViewById(R.id.Kcalgramm);
        Zuruck = findViewById(R.id.Zurueck);
        hinzufugen = findViewById(R.id.hinzufuegen);
        beschreibung = findViewById(R.id.Beschreibung1);


        Zuruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Rezept_ansehen.class));
            }
        });

        hinzufugen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Datenbank db = new Datenbank(Rezept_hinzufugen.this);
               db.receipt(Name.getText().toString(),kcalgramm.getText().toString(),beschreibung.getText().toString());
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Rezept_ansehen.class));
            }
        });
    }
}