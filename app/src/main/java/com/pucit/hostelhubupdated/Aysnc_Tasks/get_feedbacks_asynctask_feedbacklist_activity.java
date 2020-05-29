package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Adapters.FeedbackListAdapter;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Models.FeedbackModel;
import java.util.ArrayList;

public class get_feedbacks_asynctask_feedbacklist_activity extends AsyncTask<String,Void,Void> {

    private RecyclerView recyclerView;
    private Context context;
    private ProgressDialog progressDialog;
    private ArrayList<FeedbackModel> arrayList;
    private FeedbackListAdapter adapter;

    public get_feedbacks_asynctask_feedbacklist_activity(Context context,RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        arrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Loading Results!");
        progressDialog.show();

    }

    @Override
    protected Void doInBackground(String... strings) {

        Query q = Database.isAlreadyAHostel(strings[0]);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            String key;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    key = ds.getKey();
                }
                Query q1 = Database.getAllFeedbacks(key);
                q1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() > 0){
                            for (DataSnapshot ds: dataSnapshot.getChildren()){
                                arrayList.add(ds.getValue(FeedbackModel.class));
                            }
                            progressDialog.dismiss();
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                            adapter = new FeedbackListAdapter(context, arrayList);
                            recyclerView.setAdapter(adapter);
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(context,"No Feedback!",Toast.LENGTH_SHORT).show();
                            ((Activity)context).finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return null;
    }

}
