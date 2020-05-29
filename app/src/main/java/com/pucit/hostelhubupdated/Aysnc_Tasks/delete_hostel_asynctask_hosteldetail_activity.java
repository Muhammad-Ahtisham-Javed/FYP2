package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.style.IconMarginSpan;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.HostelManagerHome;

public class delete_hostel_asynctask_hosteldetail_activity extends AsyncTask<String,Void,Void> {

    private Context context;
    private ProgressDialog progressDialog;
    private String key;

    public  delete_hostel_asynctask_hosteldetail_activity(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Deleting Hostel!");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(String... strings) {

        Database.isAlreadyAHostel(strings[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int imageCount = 0;
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    key = ds.getKey();
                    imageCount = (int) ds.child("images").getChildrenCount();
                }
                for (int i=0; i< imageCount;i++)
                    Database.deleteImage(key + "__" + i);
                Database.getAllFeedbacks(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            Database.deleteFeedback(ds.getKey());
                        }
                        Database.deleteHostel(key);
                        progressDialog.dismiss();
                        Toast.makeText(context,"Hostel has been deleted successfully! However it is not removed from " +
                                "list. One has to refresh the list to see changes.",Toast.LENGTH_SHORT).show();
                        ((Activity)context).finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

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
