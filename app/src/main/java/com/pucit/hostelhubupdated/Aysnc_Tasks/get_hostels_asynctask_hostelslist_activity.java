package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Adapters.HostelListAdapter;
import com.pucit.hostelhubupdated.Adapters.RecyclerTouchListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.HostelDetail;
import com.pucit.hostelhubupdated.Models.HostelModel;

import java.util.ArrayList;

public class get_hostels_asynctask_hostelslist_activity extends AsyncTask<String,Void,Void> {

    private Context context;
    private ProgressDialog progressDialog;
    private ArrayList<HostelModel> hostelsList;
    private RecyclerView recyclerView;
    private HostelListAdapter hLAdapter;


    public get_hostels_asynctask_hostelslist_activity(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {
        hostelsList = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Results!");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(String... strings) {

        Database.getAllHostels(strings[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){
                    for (DataSnapshot ds: dataSnapshot.getChildren()) {
                        hostelsList.add(ds.getValue(HostelModel.class));
                    }
                    progressDialog.dismiss();
                    hLAdapter = new HostelListAdapter(hostelsList);
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(mLayoutManager);

                    // adding inbuilt divider line
                    recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(hLAdapter);

                    // row click listener
                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            HostelModel hostel = hostelsList.get(position);
                            Intent i = new Intent(context, HostelDetail.class);
                            i.putExtra("hostel", hostel);
                            context.startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(context,"No hostel registered!",Toast.LENGTH_SHORT).show();
                    ((Activity)context).finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return null;
    }

}
