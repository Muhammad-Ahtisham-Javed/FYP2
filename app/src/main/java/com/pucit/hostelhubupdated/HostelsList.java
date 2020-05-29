package com.pucit.hostelhubupdated;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.pucit.hostelhubupdated.Aysnc_Tasks.get_hostels_asynctask_hostelslist_activity;
import com.pucit.hostelhubupdated.Aysnc_Tasks.search_for_hostels_asynctask_hostels_list_activity;
import com.pucit.hostelhubupdated.SessionManager.SessionManager;

public class HostelsList extends AppCompatActivity {

    private Spinner type,bed,price,others;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostels_list);

        findViews();

        if (getCallingActivity().getClassName().toString().equalsIgnoreCase("com.pucit.hostelhubupdated.HostelManagerHome")) {

            linearLayout.setVisibility(View.GONE);

            String cnic = SessionManager.getUserCNIC(HostelsList.this);
            RecyclerView temp = (RecyclerView) findViewById(R.id.rv_hostel_list_activity_main);
            new get_hostels_asynctask_hostelslist_activity(HostelsList.this, temp).execute(cnic);

        } else if (getCallingActivity().getClassName().toString().equalsIgnoreCase("com.pucit.hostelhubupdated.MapsActivity")) {

            Double myLat = getIntent().getExtras().getDouble("latitude");
            Double myLong = getIntent().getExtras().getDouble("longitude");
            int rad = getIntent().getExtras().getInt("radius");
            RecyclerView temp = (RecyclerView) findViewById(R.id.rv_hostel_list_activity_main);
            new search_for_hostels_asynctask_hostels_list_activity(HostelsList.this, temp,rad,type,bed,price,others).execute(myLat,myLong);

        }

    }

    private void findViews(){
        type = findViewById(R.id.spinner_type_hostelslist_activity);
        bed = findViewById(R.id.spinner_bed_hostelslist_activity);
        price = findViewById(R.id.spinner_price_hostelslist_activity);
        others = findViewById(R.id.spinner_others_hostelslist_activity);
        linearLayout = findViewById(R.id.ll_container_filters_hostelslist_activity);
    }

}
