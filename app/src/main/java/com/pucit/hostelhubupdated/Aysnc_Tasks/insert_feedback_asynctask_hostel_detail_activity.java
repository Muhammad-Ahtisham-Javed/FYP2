package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;

public class insert_feedback_asynctask_hostel_detail_activity extends AsyncTask<String, Void, Query> {

    private Context context;
    private ProgressDialog progressDialog;
    private String cnic, hostel, comment, hostel_key, name;
    private String messRat,cleanRat,safeRat,disciplineRat,timeRat,overallRat;

    public insert_feedback_asynctask_hostel_detail_activity(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Saving Feedback. Please wait...!");
        progressDialog.show();
    }


    @Override
    protected Query doInBackground(String... strings) {

        cnic = strings[0];
        hostel = strings[1];
        messRat = strings[2];
        cleanRat = strings[3];
        safeRat = strings[4];
        disciplineRat = strings[5];
        timeRat = strings[6];
        overallRat = strings[7];
        comment = strings[8];

        Query q = Database.isAlreadyAHostel(hostel);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    hostel_key = ds.getKey();
                }
                Query q1 = Database.isAlreadyAUser(cnic);
                q1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String role = null;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            name = ds.child("name").getValue().toString();
                            role = ds.child("role").getValue().toString();
                        }
                        if (role.equalsIgnoreCase("manager")) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "You are a manager so your feedback cannot be saved!", Toast.LENGTH_SHORT).show();
                        } else {
                            Query q2 = Database.isAlreadyFeedbackByUser(hostel_key, cnic);
                            q2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() > 0) {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Already a feedback from this user for this particular hostel!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Database.insertFeedback(hostel_key, cnic, name,messRat,cleanRat,safeRat,disciplineRat,timeRat,overallRat, comment);
                                        new cal_avg_ratings_feedback_activity(context,progressDialog).execute(hostel_key);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
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

