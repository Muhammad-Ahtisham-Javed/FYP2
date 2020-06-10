package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Models.FeedbackModel;

import java.text.DecimalFormat;

public class cal_avg_ratings_feedback_activity extends AsyncTask<String,Void,Void> {

    private ProgressDialog progressDialog;
    private Context context;
    private double cleanRat = 0;
    private double discplineRat = 0;
    private double messRat = 0;
    private double safetyRat = 0;
    private double timeRat = 0;
    private double overallRat = 0;

    public cal_avg_ratings_feedback_activity(Context context, ProgressDialog progressDialog) {
        this.context = context;
        this.progressDialog = progressDialog;
    }

    @Override
    protected Void doInBackground(String... strings) {

        final String hostel_key = strings[0];
        Database.getAllFeedbacks(hostel_key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    FeedbackModel feedbackModel = ds.getValue(FeedbackModel.class);
                    cleanRat = cleanRat + Double.parseDouble(feedbackModel.cleanliness_rating);
                    discplineRat = discplineRat + Double.parseDouble(feedbackModel.discipline_rating);
                    messRat = messRat + Double.parseDouble(feedbackModel.mess_rating);
                    safetyRat = safetyRat + Double.parseDouble(feedbackModel.safety_rating);
                    timeRat = timeRat + Double.parseDouble(feedbackModel.time_strictness_rating);
                    overallRat = overallRat + Double.parseDouble(feedbackModel.overall_rating);
                    count++;
                }
                cleanRat = cleanRat / count;
                discplineRat = discplineRat / count;
                messRat = messRat / count;
                safetyRat = safetyRat / count;
                timeRat = timeRat / count;
                overallRat = overallRat / count;

                DecimalFormat df = new DecimalFormat("#.##");
                Database.putAvgRatings(hostel_key,df.format(cleanRat),df.format(discplineRat),df.format(messRat),
                        df.format(safetyRat),df.format(timeRat),df.format(overallRat));
                progressDialog.dismiss();
                Toast.makeText(context, "Feedback saved successfully!", Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return null;
    }


}
