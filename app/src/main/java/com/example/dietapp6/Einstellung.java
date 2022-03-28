package com.example.dietapp6;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Einstellung extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener {

    EditText geburtstag, große, wunschgewicht,fat,Uhrzeit;
    Spinner  geschlecht;
    Button Speichern, zurueck;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Uhrzeit.setText( hourOfDay + " : " + minute);
    }


  public static   class Zeiteinstellung extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellung);

        geburtstag = findViewById(R.id.GeburtstagD);
        geschlecht = findViewById(R.id.geschlecht);
        Speichern = findViewById(R.id.Speichern);
        große = findViewById(R.id.Große);
        wunschgewicht =findViewById(R.id.Wgewicht);
        fat= findViewById(R.id.Fat);
        Uhrzeit = findViewById(R.id.Uhrzeit);
        zurueck = findViewById(R.id.Zurueck);

        Datenbank db = new Datenbank( Einstellung.this );

        if( db.getSelectQueryResult( "SELECT * FROM config", "ConfigReportModel" ).size() > 0 ) {

            ConfigReportModel configData = (ConfigReportModel) db.getSelectQueryResult("SELECT * FROM config WHERE c_id = 1", "ConfigReportModel").get(0);

            SimpleDateFormat iso8601 = new SimpleDateFormat("dd-MM-yyyy");
            geburtstag.setText(iso8601.format(configData.getDate()));
            große.setText(Double.toString(configData.getHeight()));

            if (configData.getFatGoal() != -1) {

                fat.setText(Double.toString(configData.getFatGoal()).substring(0, Double.toString(configData.getFatGoal()).indexOf(".") + 2));

            }

            wunschgewicht.setText(Double.toString(configData.getWeightGoal()).substring(0, Double.toString(configData.getWeightGoal()).indexOf(".") + 2));
        }
        //Die funktion zum Kalender Anzeigen
        geburtstag.setInputType(InputType.TYPE_NULL);

        geburtstag.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar Kalender = Calendar.getInstance();
                int Tag = Kalender.get(Calendar.DAY_OF_MONTH);
                int Monat = Kalender.get(Calendar.MONTH);
                int Jahr = Kalender.get(Calendar.YEAR);

                DatePickerDialog Auswahl = new DatePickerDialog(Einstellung.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        geburtstag.setText(dayOfMonth+"-"+(month+1)+"-"+ year);
                    }
                },Jahr,Monat,Tag);
                Auswahl.show();
            }
        });

        //Drop Down funktion

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Einstellung.this,R.array.geschlecht, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        geschlecht.setAdapter(adapter);
        geschlecht.setOnItemSelectedListener(this);



        Speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Datenbank db = new Datenbank( Einstellung.this );

                if( db.getSelectQueryResult( "SELECT * FROM config", "ConfigReportModel" ).size() == 0 ){

                    db.addPersonD( große.getText().toString().substring( 0, große.getText().toString().indexOf( "." ) + 2 ), geburtstag.getText().toString(), geschlecht.getSelectedItem().toString(), wunschgewicht.getText().toString().substring( 0, wunschgewicht.getText().toString().indexOf( "." ) + 2 ), fat.getText().toString().substring( 0, fat.getText().toString().indexOf( "." ) + 2 ) );
                    Toast.makeText(Einstellung.this, "Toasty!", Toast.LENGTH_SHORT).show();
                }
                else{

                    db.updateConfig( große.getText().toString().substring( 0, große.getText().toString().indexOf( "." ) + 2 ), geburtstag.getText().toString(), geschlecht.getSelectedItem().toString(), wunschgewicht.getText().toString().substring( 0, wunschgewicht.getText().toString().indexOf( "." ) + 2 ), fat.getText().toString().substring( 0, fat.getText().toString().indexOf( "." ) + 2 ), 1 );
                    Toast.makeText(Einstellung.this, "Toasty!", Toast.LENGTH_SHORT).show();
                }
                //Edittexte
                große.getText().toString();
                geburtstag.getText().toString();
                wunschgewicht.getText().toString();
                fat.getText().toString();

                String Wert = geschlecht.getSelectedItem().toString();

                db.addPersonD( "175.0", "17-08-1996", "Mann", "76.9", "20.0" );
                ConfigReportModel test = ( ConfigReportModel ) db.getSelectQueryResult( "SELECT * FROM config", "ConfigReportModel" ).get(0);
                Toast.makeText(Einstellung.this, Double.toString( test.getHeight() ), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.Einstellung.class));

            }
        });

        zurueck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.dietapp6.MainActivity.class));
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       /* String geschlechtA= parent.getItemAtPosition(position).toString();
        Toast.makeText(this, geschlechtA, Toast.LENGTH_SHORT).show();*/

    }

    void test(){
        AdapterView<?> parent = null;
    String test = parent.getItemAtPosition(0).toString();
        Toast.makeText(this, test, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}