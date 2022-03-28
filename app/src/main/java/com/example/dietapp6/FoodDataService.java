package com.example.dietapp6;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;

public class FoodDataService {

    public static final String LINK = "http://fr.openfoodfacts.org/api/v0/product/";

    Context context;

    public FoodDataService(Context context){this.context=context;}

    public interface FoodData{
        void  onError(String nachricht);

        void onResponse(List<ScanReportModel> scanReportModel);
    }

    public void Information(String Barcode,FoodData foodData){
        String url = LINK + Barcode;
        List<ScanReportModel> Info = new ArrayList<>();


        JsonObjectRequest b =new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject c = response.getJSONObject("product");
                    JSONObject d = c.getJSONObject("nutriments");

                    ScanReportModel Produktdata= new ScanReportModel( c.getString("product_name"), c.getString("image_url"), Barcode, d.getDouble("energy-kcal_100g") );

                    Info.add(Produktdata);

                    foodData.onResponse(Info);

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        com.example.dietapp6.MySingleton.getInstance(context).addToRequestQueue(b);





    }



}
