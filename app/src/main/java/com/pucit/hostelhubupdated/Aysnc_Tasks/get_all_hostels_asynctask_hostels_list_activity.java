package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.HostelDetail;
import com.pucit.hostelhubupdated.Adapters.HostelListAdapter;
import com.pucit.hostelhubupdated.Models.HostelModel;
import com.pucit.hostelhubupdated.Adapters.RecyclerTouchListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class get_all_hostels_asynctask_hostels_list_activity extends AsyncTask<ArrayList<String>, Void, Void> {

    private Context context;
    private ProgressDialog progressDialog;
    private ArrayList<HostelModel> hostels;
    private RecyclerView hostelListRV;
    private HostelListAdapter hLAdapter;

    private Spinner type,bed,price,others;
    private int bedTypeSelected = -1;

    public get_all_hostels_asynctask_hostels_list_activity(Context context, ProgressDialog progressDialog, RecyclerView recyclerView,
                                                           Spinner type,Spinner bed,Spinner price,Spinner others) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.hostelListRV = recyclerView;
        this.type = type;
        this.bed = bed;
        this.price = price;
        this.others = others;
    }

    @Override
    protected void onPreExecute() {

        hostels = new ArrayList<>();

        type.setSelection(0,false);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    Toast.makeText(context,"Please select other options",Toast.LENGTH_SHORT).show();
                else if (position == 1){
                    for (int i = 0; i< hostels.size();i++) {
                        if (hostels.get(i).type.equalsIgnoreCase("female")) {
                            hostels.remove(i);
                            i--;
                        }
                    }
                    hLAdapter.notifyDataSetChanged();
                }
                else{
                    for (int i = 0; i< hostels.size();i++) {
                        if (hostels.get(i).type.equalsIgnoreCase("male")) {
                            hostels.remove(i);
                            i--;
                        }
                    }
                    hLAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bed.setSelection(0,false);
        bed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    Toast.makeText(context,"Please select other options",Toast.LENGTH_SHORT).show();
                else if (position == 1){
                    bedTypeSelected = 1;
                    for (int i = 0; i< hostels.size();i++) {
                        if (hostels.get(i).single_bed_rent == 0){
                            hostels.remove(i);
                            i--;
                        }
                    }
                    hLAdapter.notifyDataSetChanged();
                }
                else if (position == 2){
                    bedTypeSelected = 2;
                    for (int i = 0; i< hostels.size();i++) {
                        if (hostels.get(i).double_bed_rent == 0){
                            hostels.remove(i);
                            i--;
                        }
                    }
                    hLAdapter.notifyDataSetChanged();
                }
                else {
                    bedTypeSelected = 3;
                    for (int i = 0; i< hostels.size();i++) {
                        if (hostels.get(i).dormitory_rent == 0){
                            hostels.remove(i);
                            i--;
                        }
                    }
                    hLAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        price.setSelection(0,false);
        price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    Toast.makeText(context,"Please select other options",Toast.LENGTH_SHORT).show();
                else if (position == 1){
                    if (bedTypeSelected == -1)
                        Toast.makeText(context,"Please select bed type first",Toast.LENGTH_SHORT).show();
                    else{
                        Collections.sort(hostels, new Comparator<HostelModel>() {
                            @Override
                            public int compare(HostelModel o1, HostelModel o2) {
                                if (bedTypeSelected == 1)
                                    return Integer.compare(o1.single_bed_rent,o2.single_bed_rent);
                                else if (bedTypeSelected == 2 )
                                    return Integer.compare(o1.double_bed_rent,o2.double_bed_rent);
                                else if (bedTypeSelected == 3 )
                                    return Integer.compare(o1.dormitory_rent,o2.dormitory_rent);
                                return -1;
                            }
                        });
                    }
                    hLAdapter.notifyDataSetChanged();
                }
                else{
                    if (bedTypeSelected == -1)
                        Toast.makeText(context,"Please select bed type first",Toast.LENGTH_SHORT).show();
                    else{
                        Comparator<HostelModel> comparator = new Comparator<HostelModel>() {
                            @Override
                            public int compare(HostelModel o1, HostelModel o2) {
                                if (bedTypeSelected == 1)
                                    return Integer.compare(o1.single_bed_rent,o2.single_bed_rent);
                                else if (bedTypeSelected == 2 )
                                    return Integer.compare(o1.double_bed_rent,o2.double_bed_rent);
                                else if (bedTypeSelected == 3 )
                                    return Integer.compare(o1.dormitory_rent,o2.dormitory_rent);
                                return -1;
                            }
                        };
                        Collections.sort(hostels, comparator.reversed());
                    }
                    hLAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        others.setSelection(0,false);
        others.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    Toast.makeText(context,"Please select other options",Toast.LENGTH_SHORT).show();
                else if (position == 1){
                    for (int i = 0; i< hostels.size();i++) {
                        if (hostels.get(i).mess_rating == null || Double.parseDouble(hostels.get(i).mess_rating) < 3.5){
                            hostels.remove(i);
                            i--;
                        }
                    }
                    hLAdapter.notifyDataSetChanged();
                }
                else if (position == 2){
                    for (int i = 0; i< hostels.size();i++) {
                        if (hostels.get(i).cleanliness_rating == null || Double.parseDouble(hostels.get(i).cleanliness_rating) < 3.5){
                            hostels.remove(i);
                            i--;
                        }
                    }
                    hLAdapter.notifyDataSetChanged();
                }
                else if (position == 3){
                    for (int i = 0; i< hostels.size();i++) {
                        if (hostels.get(i).safety_rating == null || Double.parseDouble(hostels.get(i).safety_rating) < 3.5){
                            hostels.remove(i);
                            i--;
                        }
                    }
                    hLAdapter.notifyDataSetChanged();
                }
                else if (position == 4){
                    for (int i = 0; i< hostels.size();i++) {
                        if (hostels.get(i).discipline_rating == null || Double.parseDouble(hostels.get(i).discipline_rating) < 3.5){
                            hostels.remove(i);
                            i--;
                        }
                    }
                    hLAdapter.notifyDataSetChanged();
                }
                else {
                    for (int i = 0; i< hostels.size();i++) {
                        if (hostels.get(i).time_strictness_rating == null || Double.parseDouble(hostels.get(i).time_strictness_rating) > 3){
                            hostels.remove(i);
                            i--;
                        }
                    }
                    hLAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected Void doInBackground(ArrayList<String>... arrayLists) {

        ArrayList<Query> temp = new ArrayList<>();
        for (int i = 0; i < arrayLists[0].size(); i++) {

            temp.add(Database.getHostel(arrayLists[0].get(i)));
            temp.get(i).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() > 0) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            hostels.add(ds.getValue(HostelModel.class));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();

                hLAdapter = new HostelListAdapter(hostels);

                hostelListRV.setHasFixedSize(true);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                hostelListRV.setLayoutManager(mLayoutManager);

                // adding inbuilt divider line
                hostelListRV.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                hostelListRV.setItemAnimator(new DefaultItemAnimator());
                hostelListRV.setAdapter(hLAdapter);

                // row click listener
                hostelListRV.addOnItemTouchListener(new RecyclerTouchListener(context, hostelListRV, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        HostelModel hostel = hostels.get(position);
                        Intent i = new Intent(context, HostelDetail.class);
                        i.putExtra("hostel", hostel);
                        context.startActivity(i);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

                if (hLAdapter.getItemCount() == 0){
                    ((Activity)context).finish();
                    Toast.makeText(context,"Uable to retrieve results or No results found",Toast.LENGTH_SHORT).show();
                }

            }


        }, 5000);


    }
}
