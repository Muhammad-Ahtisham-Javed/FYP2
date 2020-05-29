package com.pucit.hostelhubupdated;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.pucit.hostelhubupdated.Aysnc_Tasks.get_feedbacks_asynctask_feedbacklist_activity;
import com.pucit.hostelhubupdated.Models.HostelModel;

public class FeedbackList extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);

        Bundle b = getIntent().getExtras();
        HostelModel hostel = (HostelModel) b.getSerializable("hostel");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_feedback_list);
        new get_feedbacks_asynctask_feedbacklist_activity(FeedbackList.this,recyclerView).execute(hostel.city_locality_name);

//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));


    }
}
