package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DatabaseError;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Models.HostelModel;

import java.util.ArrayList;

public class search_for_hostels_asynctask_hostels_list_activity extends AsyncTask<Double,Void, Void> {

    private ArrayList<String> keysList;
    private Context context;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private int radius;

    private Spinner type,bed,price,others;

    public search_for_hostels_asynctask_hostels_list_activity(Context context, RecyclerView recyclerView, int radius,
                                                              Spinner type, Spinner bed, Spinner price, Spinner others) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.radius = radius;
        this.type = type;
        this.bed = bed;
        this.price = price;
        this.others = others;
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Loading results! Please wait...");
    }

    @Override
    protected void onPreExecute() {
        keysList = new ArrayList<>();
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Double... doubles) {

        GeoFire geoFire = new GeoFire(Database.getDbReference().child("hostels_location"));
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(doubles[0], doubles[1]),radius);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                keysList.add(key);
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Log.d("mytest","mytest");
                new get_all_hostels_asynctask_hostels_list_activity(context,progressDialog,recyclerView,type,bed,price,others).execute(keysList);
            }
        },5000);


    }

}
