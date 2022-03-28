package com.example.dietapp6;
import android.content.Context;
import android.graphics.Path;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterDatei extends BaseAdapter {

    Context context;
    List<ScanReportModel> scan;

    public AdapterDatei(Context context, List<ScanReportModel> scan) {
        this.context = context;
        this.scan = scan;
    }

    @Override
    public int getCount() {
        return scan.size();
    }

    @Override
    public Object getItem(int position) {
        return scan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_list_style,parent,false);
        }
        TextView Produktname,barcode,Kal;
        ImageView Bild;
        Bild = convertView.findViewById(R.id.Images);
        Produktname = convertView.findViewById(R.id.ProduktN);
        Kal = convertView.findViewById(R.id.kcal);
        barcode = convertView.findViewById(R.id.barcode);


        Double K = scan.get(position).getKalorieng();

        Kal.setText("Kalorien: "+K.toString());
        Produktname.setText("Name: " + scan.get(position).getName());
        Glide.with(convertView).load(scan.get(position).getBild()).into(Bild);
        barcode.setText( "barcode: " + scan.get(position).getBarcode() );
        return convertView;
    }
}
