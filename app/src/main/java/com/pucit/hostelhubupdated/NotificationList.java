package com.pucit.hostelhubupdated;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pucit.hostelhubupdated.Adapters.NotificationAdapter;
import com.pucit.hostelhubupdated.Adapters.Notification;

import java.util.List;

public class NotificationList extends AppCompatActivity {
    List<Notification> data;
    RecyclerView recyclerView;
    NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);


        data = Notification.getData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_nlist);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapter = new NotificationAdapter(getApplicationContext(), data);

        recyclerView.setAdapter(adapter);

        /*
        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Hostel hostel = data.get(position);

                //Toast here is just for understanding of flow
                //We'll send intent here to hostel details activity according to the data tapped
                Toast.makeText(getApplicationContext(), hostel.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

         */
    }
}
