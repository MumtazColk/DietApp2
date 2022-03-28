package com.example.dietapp6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Scanner extends AppCompatActivity {
    public static EditText Barcode;
    TextView Scanname,Grammwerte;
    ListView Produkte;
    Button Scan, Hauptmenu, Hinzufuegen;
    ImageView bild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        Barcode = findViewById(R.id.Barcode);
        Scan = findViewById(R.id.Scannen);
        Hauptmenu = findViewById(R.id.Hauptmenu);
        Hinzufuegen = findViewById(R.id.hinzufuegen1);
        bild = findViewById(R.id.bild);
         Scanname = findViewById(R.id.Scanname);
         Grammwerte = findViewById(R.id.Grammwerte);
         Produkte = findViewById(R.id.Produkte);

         Datenbank db = new Datenbank( Scanner.this );
         db.getWritableDatabase();

        Hinzufuegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodDataService result = new FoodDataService(Scanner.this);
                Toast.makeText(Scanner.this,  Barcode.getText().toString(), Toast.LENGTH_SHORT).show();

                result.Information(Barcode.getText().toString(), new FoodDataService.FoodData() {
                    @Override
                    public void onError(String nachricht) {
                        Toast.makeText(Scanner.this, ":(", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<ScanReportModel> scanReportModel) {
                        Toast.makeText(Scanner.this,  "2222", Toast.LENGTH_SHORT).show();
                        Scanname.setText( scanReportModel.get(0).getName() );
                        Grammwerte.setText( Double.toString( scanReportModel.get(0).getKalorieng() ) );
                        Glide.with(Scanner.this).load( scanReportModel.get(0).getBild() ).into( bild );

                        Datenbank db = new Datenbank( Scanner.this );
                        db.getWritableDatabase();

                        db.addScan( scanReportModel.get(0) );
                    }
                });
            }
        });

        ArrayList<ScanReportModel> scanList= db.getSelectQueryResult( "SELECT * FROM scan ORDER BY s_id DESC LIMIT 0, 10", "ScanReportModel" );

        if( scanList.size() > 0 ){

            AdapterDatei arrayAdapter = new AdapterDatei( Scanner.this, scanList );
            Produkte.setAdapter(arrayAdapter);
            Produkte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Grammwerte.setText( Double.toString( scanList.get( position ).getKalorieng() ) );
                    Scanname.setText( scanList.get( position ).getName() );
                    Glide.with(Scanner.this).load( scanList.get(position).getBild() ).into( bild );
                }
            });
        }



        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.BarcodeScanner.class));
            }
        });

        Hauptmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.MainActivity.class));
            }
        });
    }
}