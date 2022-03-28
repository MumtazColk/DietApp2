package com.example.dietapp6;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Datenbank extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "ProduktDB";
    private static final String TBNAME = "Config";
    private static final String TBNAME2 = "Receipt";
    private static final String TBNAME3 = "Scan";
    private static final String TBNAME4 = "progress";
    private static final String TBNAME5 = "diet";




    public Datenbank(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String TB1= "CREATE TABLE "+TBNAME+"(" +
                "c_ID INTEGER PRIMARY KEY ," +
                "height FLOAT NOT NULL ," +
                "date_of_birth DATE NOT NULL ," +
                "SEX TEXT NOT NULL ," +
                "Weight_goal FLOAT NOT NULL ," +
                "fat_percantage_goal FLOAT " +
                ")";

        String TB2= "CREATE TABLE "+TBNAME2+"(" +
                "r_ID INTEGER PRIMARY KEY," +
                "Name TEXT NOT NULL,  " +
                "kcal_per_100g FLOAT , " +
                "beschreibung TEXT " +
                ")";

        String TB3= "CREATE TABLE "+TBNAME3+"(" +
                "s_id INTEGER PRIMARY KEY NOT NULL, " +
                "name TEXT NOT NULL, " +
                "url TEXT , " +
                "barcode INTEGER NOT NULL, " +
                "kcal_per_100g FLOAT " +
                ")";

        String TB4= "CREATE TABLE "+TBNAME4+"(" +
                "p_id INTEGER PRIMARY KEY NOT NULL, " +
                "timestamp DATE NOT NULL, " +
                "weight FLOAT , " +
                "fat_percentage FLOAT " +
                ")";

        String TB5="CREATE TABLE "+TBNAME5+"(" +
                "d_id INTEGER PRIMARY KEY NOT NULL, " +
                "timestamp DATE NOT NULL, " +
                "kcal FLOAT NOT NULL, " +
                "s_id INTEGER , " +
                "r_id INTEGER , " +
                "CONSTRAINT FK_diet FOREIGN KEY (s_id) REFERENCES Scan(s_id), " +
                "CONSTRAINT FK_diet2 FOREIGN KEY (r_id) REFERENCES Receipt(r_id) " +
                ")";

        db.execSQL(TB1);
        db.execSQL(TB2);
        db.execSQL(TB3);
        db.execSQL(TB4);
        db.execSQL(TB5);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    void addScan(ScanReportModel F1){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name",F1.getName());
        values.put("url",F1.getBild());
        values.put("Barcode",F1.getBarcode());
        values.put("kcal_per_100g",F1.getKalorieng());

        db.insert(TBNAME3,null,values);
        db.close();


    }

    void addPersonD(String height, String Date,String sex, String weight, String fat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues wert = new ContentValues();

        float height1 = Float.parseFloat(height);
        float weight1 = Float.parseFloat(weight);
        float fat1 = Float.parseFloat(fat);

        wert.put("height",height1);
        wert.put("date_of_birth",Date);
        wert.put("sex", sex);
        wert.put("weight_goal",weight1);
        wert.put("fat_percantage_goal",fat1);

        db.insert(TBNAME,null,wert);
        db.close();

    }

    void receipt(String name,String Kcalg, String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues wert = new ContentValues();

        float Kalorien = Float.parseFloat(Kcalg);

        wert.put("Name", name);
        wert.put("kcal_per_100g", Kalorien );
        wert.put("beschreibung", desc);

        db.insert(TBNAME2,null,wert);
        db.close();

    }

    void progress2(String Date, String value, String valueType){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues wert = new ContentValues();

        float weight1 = Float.parseFloat(value);


        wert.put("timestamp", Date );

        if( valueType.equals( "fat" ) ){

            wert.put("fat_percentage", weight1);
        }
        else if(  valueType.equals( "weight" ) ){

            wert.put("weight",weight1);
        }

        db.insert(TBNAME4,null,wert);
        db.close();
    }

    void progress(String Date, String weight, String fat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues wert = new ContentValues();

        float weight1 = Float.parseFloat(weight);


        wert.put("timestamp", Date );
        wert.put("weight",weight1);
        wert.put("fat_percentage", fat);

        db.insert(TBNAME4,null,wert);
        db.close();
    }

    void diet(String timestamp, String kcal,int id, String target){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues wert = new ContentValues();

        float kcal1 = Float.parseFloat(kcal);
        //int id_parsed = Integer.parseInt( id );
        wert.put( "timestamp", timestamp );
        wert.put("kcal",kcal1);

        if( target.equals( "s_id" ) ){

            wert.put("s_id", id);
        }
        else if( target.equals( "r_id" ) ){

            wert.put("r_id", id);
        }



        db.insert(TBNAME5,null,wert);
        db.close();


    }

    List<ScanReportModel> getScandata(){

        List<ScanReportModel> PersonList = new ArrayList<>();
        String selectQuery1 = "SELECT * FROM " + "scan";
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor1 = db1.rawQuery(selectQuery1,null);
        int idxanzahl = cursor1.getCount();


        String selectQuery = "SELECT * FROM " + "scan" + " WHERE id ="+ idxanzahl;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                ScanReportModel P1 = new ScanReportModel( cursor.getInt(0), cursor.getString(1), cursor.getString( 2 ), cursor.getString(3), cursor.getDouble( 4 ) );

                PersonList.add(P1);


            }while (cursor.moveToNext());
        }

        return PersonList;
    }

    public ArrayList getSelectQueryResult( String query, String objectType ){

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery( query, null );
        cursor.moveToFirst();

        ArrayList result = new ArrayList();

        Log.d( "count:", Integer.toString( cursor.getCount() ) );

        for( int i = 0; i < cursor.getCount(); i++, cursor.moveToNext() ){

            try{

            Object resultObject = new Integer( -1 );

                switch ( objectType ){

                    case "ConfigReportModel":
                        Log.d( "POSITION", Integer.toString( cursor.getPosition() ) );
                        if( !cursor.isNull( 5 ) ){
                            resultObject = new ConfigReportModel(cursor.getInt(0), cursor.getDouble(1), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getDouble( 4 ), cursor.getDouble( 5 ) );
                        }
                        else{

                            resultObject = new ConfigReportModel( cursor.getInt(0), cursor.getDouble(1), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getDouble( 4 ) );
                        }
                    break;

                    case "ReceiptReportModel":

                        resultObject = new ReceiptReportModel( cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString( 3 ) );
                        break;

                    case "ScanReportModel":

                        resultObject = new ScanReportModel( cursor.getInt(0), cursor.getString(1), cursor.getString( 2 ), cursor.getString(3), cursor.getDouble( 4 ) );
                    break;

                    case "DietReportModel":

                        if( cursor.isNull( 3 ) && cursor.isNull( 4 ) ){

                            resultObject = new DietReportModel( cursor.getInt(0), cursor.getString(1), cursor.getDouble( 2 ) );
                        }
                        else if( !cursor.isNull( 3 ) ){

                            resultObject = new DietReportModel( cursor.getInt(0), cursor.getString(1), cursor.getDouble( 2 ), cursor.getInt(3), "scan" );
                        }
                        else{

                            resultObject = new DietReportModel( cursor.getInt(0), cursor.getString(1), cursor.getDouble( 2 ), cursor.getInt(4), "receipt" );
                        }
                    break;

                    case "ProgressReportModel":

                        if( cursor.isNull(2 ) || cursor.isNull(3 ) ){

                            if( !cursor.isNull(2 ) ){

                                resultObject = new ProgressReportModel( cursor.getInt(0), cursor.getString(1), cursor.getDouble( 2 ), "weight" );
                            }
                            else{

                                resultObject = new ProgressReportModel( cursor.getInt(0), cursor.getString(1), cursor.getDouble( 3 ), "fat" );
                            }
                        }
                        else{

                            resultObject = new ProgressReportModel( cursor.getInt(0), cursor.getString(1), cursor.getDouble( 2 ), cursor.getDouble(3) );
                        }
                    break;
                }

                result.add( resultObject );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    void updateConfig( String groesse, String geburtstag, String geschlecht, String wGewicht, String wFett, int id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put( "height", groesse );
        values.put( "date_of_birth", geburtstag );
        values.put( "sex", geschlecht );
        values.put( "weight_goal", wGewicht );
        values.put( "fat_percantage_goal", wFett );

        db.update( "config", values, "c_id = ?", new String[]{ String.valueOf( id ) } );
    }

    void updateReceipt( String name, double kcal, String desc, int id ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put( "name", name );
        values.put( "kcal_per_100g", kcal );
        values.put( "beschreibung", desc );

        db.update( "receipt", values, "r_id = ?", new String[]{ String.valueOf( id ) } );
    }
}
