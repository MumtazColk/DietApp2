package com.example.dietapp6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Rezept_bearbeiten extends AppCompatActivity {

    EditText Nameb,Kcalgb,Beschreibung;
    Button Zuruck,ubernehmen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezept_bearbeiten);
        Nameb = findViewById(R.id.Nameb);
        Kcalgb = findViewById(R.id.Kcalgb);
        Beschreibung = findViewById(R.id.Beschreibungb);
        Zuruck = findViewById(R.id.Zurueck);
        ubernehmen = findViewById(R.id.uebernehmen);

        String sessionId = getIntent().getStringExtra("receipt_id");
        Datenbank db =  new Datenbank( Rezept_bearbeiten.this );
        db.getWritableDatabase();

        ReceiptReportModel toEdit = (ReceiptReportModel) db.getSelectQueryResult( "SELECT * FROM receipt WHERE r_id = " + sessionId, "ReceiptReportModel" ).get(0);
        Nameb.setText( toEdit.getName() );
        Kcalgb.setText( Double.toString( toEdit.getKcal() ) );
        Beschreibung.setText( toEdit.getDesc() );

        Zuruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Rezept_ansehen.class));
            }
        });

        ubernehmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.updateReceipt( Nameb.getText().toString(), Double.parseDouble( Kcalgb.getText().toString() ), Beschreibung.getText().toString(), Integer.parseInt( sessionId ) );
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Rezept_ansehen.class));
            }
        });
    }
}