package com.pucit.hostelhubupdated;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.pucit.hostelhubupdated.Aysnc_Tasks.insert_feedback_asynctask_hostel_detail_activity;
import com.pucit.hostelhubupdated.SessionManager.SessionManager;

public class FeedbackActivity extends AppCompatActivity {

    private RatingBar messRB,cleanRB,safeRB,disciplineRB,timeRB,overallRatingRB;
    private String messRat = "";
    private String cleanRat = "";
    private String safeRat = "";
    private String disciplineRat = "";
    private String timeRat = "";
    private String overallRat = "";
    private EditText et_comment;
    private Button btn_save;
    private String hostelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        hostelId = getIntent().getExtras().getString("hostelID");
        findViews();

        messRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                FeedbackActivity.this.messRat = "" + rating;
            }
        });

        safeRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                FeedbackActivity.this.safeRat = "" + rating;
            }
        });

        disciplineRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                FeedbackActivity.this.disciplineRat = "" + rating;
            }
        });

        timeRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                FeedbackActivity.this.timeRat = "" + rating;
            }
        });

        overallRatingRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                FeedbackActivity.this.overallRat = "" + rating;
            }
        });

        cleanRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                FeedbackActivity.this.cleanRat = "" + rating;
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messRat.equals("") || safeRat.equals("") || cleanRat.equals("")  || disciplineRat.equals("") || timeRat.equals("") || overallRat.equals("")){
                    Toast.makeText(FeedbackActivity.this,"Please rate for all attributes!",Toast.LENGTH_SHORT).show();
                }
                else{
                    String cnic = SessionManager.getUserCNIC(FeedbackActivity.this);
                    if (cnic.equals(""))
                        Toast.makeText(FeedbackActivity.this, "Uable to retrieve CNIC!", Toast.LENGTH_SHORT).show();
                    else{
                        new insert_feedback_asynctask_hostel_detail_activity(FeedbackActivity.this).execute(cnic, hostelId, messRat,cleanRat,safeRat,disciplineRat,timeRat,overallRat, et_comment.getText().toString().trim());
                    }

                }
            }
        });

    }

    private void findViews(){
        messRB = findViewById(R.id.ratingbar_mess_feedback_activity);
        cleanRB = findViewById(R.id.ratingbar_cleanliness_feedback_activity);
        safeRB = findViewById(R.id.ratingbar_safety_feedback_activity);
        disciplineRB = findViewById(R.id.ratingbar_discipline_feedback_activity);
        timeRB = findViewById(R.id.ratingbar_time_strictness_feedback_activity);
        overallRatingRB = findViewById(R.id.ratingbar_overall_rating_feedback_activity);
        et_comment = findViewById(R.id.et_comment_feedback_activity);
        btn_save = findViewById(R.id.btn_save_rating_feedback_activity);
    }
}
