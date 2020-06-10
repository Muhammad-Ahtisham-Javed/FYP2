package com.pucit.hostelhubupdated;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Aysnc_Tasks.delete_hostel_asynctask_hosteldetail_activity;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Models.HostelModel;
import com.pucit.hostelhubupdated.SessionManager.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HostelDetail extends AppCompatActivity {

    private HostelModel hostel;

    private ViewFlipper viewFlipper;
    private TextView tv_name;
    private TextView tv_location;
    private TextView tv_type;
    private TextView tv_tRooms;
    private TextView tv_aRooms;
    private TextView tv_mess;
    private TextView tv_gas;
    private TextView tv_electricity;
    private TextView tv_wifi;
    private TextView tv_ac;
    private TextView tv_aBaths;
    private LinearLayout ll_beds;
    private Button btn_feedback;
    private TextView tv_rat_clean,tv_rat_discipline,tv_rat_mess,tv_rat_safety,tv_rat_time,tv_rat_overall;
    private TableLayout tbl_ratings;

    private String cnic, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_detail);

        Bundle b = getIntent().getExtras();
        hostel = (HostelModel) b.getSerializable("hostel");

        findViewsOfHostelDetailActivity();
        assignText();

        cnic = SessionManager.getUserCNIC(HostelDetail.this);
        role = SessionManager.getUserType(HostelDetail.this);

        if (role.equalsIgnoreCase("manager") || cnic.equalsIgnoreCase("")) {
            btn_feedback.setVisibility(View.GONE);
        }

        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HostelDetail.this,FeedbackActivity.class);
                i.putExtra("hostelID",hostel.city_locality_name.toString());
                startActivity(i);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (role.equalsIgnoreCase("manager")) {
            inflater.inflate(R.menu.men_for_manger_hostel_detail_activity, menu);
        } else
            inflater.inflate(R.menu.menu_for_seeker_hostel_detail_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item1_ratings_feedbacks_menu_for_seeker_hostel_detail_activity:
            case R.id.item3_feedback_menu_for_manager_hostel_detail_activity:
                Intent i = new Intent(HostelDetail.this, FeedbackList.class);
                i.putExtra("hostel", hostel);
                startActivity(i);
                return true;
            case R.id.item1_update_menu_for_manager_hostel_detail_activity:
                Intent i2 = new Intent(HostelDetail.this,AddHostel.class);
                i2.putExtra("hostel",hostel);
                startActivityForResult(i2,100);
                return true;
            case R.id.item2_delete_menu_for_manager_hostel_detail_activity:
                AlertDialog alert = askOption();
                alert.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void assignText() {

        final ArrayList<String> imageUris = new ArrayList<>();

        Database.getImagesForHostel(hostel.city_locality_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        for (DataSnapshot im : ds.child("images").getChildren()) {
                            imageUris.add(im.child("filelink").getValue().toString());
                        }
                    }
                    for (int i = 0; i < imageUris.size(); i++) {
                        ImageView imageView = new ImageView(HostelDetail.this);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        Picasso.get().load(imageUris.get(i)).into(imageView);
                        viewFlipper.addView(imageView);

                    }
                    if (imageUris.size() == 0) {
                        ImageView imageView = new ImageView(HostelDetail.this);
                        imageView.setImageResource(R.drawable.list_icon);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        viewFlipper.addView(imageView);
                        viewFlipper.stopFlipping();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tv_name.setText("Name:- " + hostel.name.toUpperCase());
        tv_location.setText("Location:-   House No " + hostel.house_no + ", Street No " + hostel.street_no + ", " +
                hostel.locality + ", " + hostel.city.toUpperCase() + "    Postal Code: " + hostel.postal_code);
        tv_type.setText("Type: " + hostel.type.toUpperCase());
        tv_tRooms.setText("Total Rooms: " + hostel.total_rooms);
        tv_aRooms.setText("Available Rooms: " + hostel.available_rooms);

        if (hostel.mess == true)
            tv_mess.setText("Yes");
        else
            tv_mess.setText("No");

        if (hostel.gas == true)
            tv_gas.setText("Yes");
        else
            tv_gas.setText("No");

        if (hostel.electricity == true)
            tv_electricity.setText("Yes");
        else
            tv_electricity.setText("No");

        if (hostel.wifi == true)
            tv_wifi.setText("Yes");
        else
            tv_wifi.setText("No");

        if (hostel.ac == true)
            tv_ac.setText("Yes");
        else
            tv_ac.setText("No");

        if (hostel.attached_baths == true)
            tv_aBaths.setText("Yes");
        else
            tv_aBaths.setText("No");

        if (hostel.overall_rating != null){
            tv_rat_clean.setText(hostel.cleanliness_rating + "");
            tv_rat_discipline.setText(hostel.discipline_rating + "");
            tv_rat_mess.setText(hostel.mess_rating + "");
            tv_rat_safety.setText(hostel.safety_rating + "");
            tv_rat_time.setText(hostel.time_strictness_rating + "");
            tv_rat_overall.setText(hostel.overall_rating + "");
        }else
            tbl_ratings.setVisibility(View.GONE);

        if (hostel.single_bed_rent != 0) {
            TextView tv = new TextView(HostelDetail.this);
            tv.setText("Single Bed:-   " + hostel.single_bed_rent + " Rs.");
            tv.setPadding(8, 0, 6, 0);
            tv.setTextSize(15);
            tv.setTextColor(getResources().getColor(R.color.white));
            ll_beds.addView(tv);
        }
        if (hostel.double_bed_rent != 0) {
            TextView tv = new TextView(HostelDetail.this);
            tv.setText("Double Bed:-   " + hostel.double_bed_rent + " Rs.");
            tv.setPadding(8, 0, 6, 0);
            tv.setTextSize(15);
            tv.setTextColor(getResources().getColor(R.color.white));
            ll_beds.addView(tv);
        }
        if (hostel.dormitory_rent != 0) {
            TextView tv = new TextView(HostelDetail.this);
            tv.setText("Dormitory Bed:-   " + hostel.dormitory_rent + " Rs.");
            tv.setPadding(8, 0, 6, 0);
            tv.setTextSize(15);
            tv.setTextColor(getResources().getColor(R.color.white));
            ll_beds.addView(tv);
        }


    }


    private void findViewsOfHostelDetailActivity() {

        tv_name = (TextView) findViewById(R.id.tv_name_activity_hostel_detail);
        tv_location = (TextView) findViewById(R.id.tv_location_activity_hostel_detail);
        tv_type = (TextView) findViewById(R.id.tv_type_activity_hostel_detail);
        tv_tRooms = (TextView) findViewById(R.id.tv_tRooms_activity_hostel_detail);
        tv_aRooms = (TextView) findViewById(R.id.tv_aRooms_activity_hostel_detail);
        tv_mess = (TextView) findViewById(R.id.tv_mess_activity_hostel_detail);
        tv_gas = (TextView) findViewById(R.id.tv_gas_activity_hostel_detail);
        tv_electricity = (TextView) findViewById(R.id.tv_electricity_activity_hostel_detail);
        tv_wifi = (TextView) findViewById(R.id.tv_wifi_activity_hostel_detail);
        tv_ac = (TextView) findViewById(R.id.tv_ac_activity_hostel_detail);
        tv_aBaths = (TextView) findViewById(R.id.tv_aBaths_activity_hostel_detail);
        ll_beds = (LinearLayout) findViewById(R.id.ll_bed_activity_hostel_detail);
        tv_rat_clean = (TextView) findViewById(R.id.tv_cleanliness_rating_tbl_ratings_hosteldetail_activity);
        tv_rat_discipline = (TextView) findViewById(R.id.tv_discipline_rating_tbl_ratings_hosteldetail_activity);
        tv_rat_mess = (TextView) findViewById(R.id.tv_mess_rating_tbl_ratings_hosteldetail_activity);
        tv_rat_safety = (TextView) findViewById(R.id.tv_safety_rating_tbl_ratings_hosteldetail_activity);
        tv_rat_time = (TextView) findViewById(R.id.tv_time_rating_tbl_ratings_hosteldetail_activity);
        tv_rat_overall = (TextView) findViewById(R.id.tv_overall_rating_tbl_ratings_hosteldetail_activity);
        btn_feedback = (Button) findViewById(R.id.btn_save_rating_hostel_detail_activity);
        tbl_ratings = (TableLayout) findViewById(R.id.tbl_ratings_activity_hostel_detail);

        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper_hostel_detail_activity);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(HostelDetail.this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(HostelDetail.this, android.R.anim.slide_out_right);

    }

    private AlertDialog askOption() {

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to delete this hostel?")
                .setIcon(R.drawable.ic_delete_icon)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        new delete_hostel_asynctask_hosteldetail_activity(HostelDetail.this).execute(hostel.city_locality_name);
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .create();

        return myQuittingDialogBox;
    }

}

