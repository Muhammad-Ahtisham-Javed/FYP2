package com.pucit.hostelhubupdated;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Models.HostelModel;


public class AddHostel extends AppCompatActivity {

    private Button btn_addHostel;
    private EditText hostelName, totalNoOfRooms, availableRooms, s_bed, d_bed, dorm;
    private CheckBox wifi, gas, electricity, mess, baths, ac, male, female;

    private HostelModel hostel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hostel);

        findViews();

        if (getCallingActivity() != null) {

            if (getCallingActivity().getClassName().toString().equalsIgnoreCase("com.pucit.hostelhubupdated.HostelDetail")) {

                //An existing hostel is going to be updated

                final ProgressDialog progressDialog = new ProgressDialog(AddHostel.this);
                progressDialog.setMessage("Loading information!");
                progressDialog.show();
                final String[] key = new String[1];

                Bundle b = getIntent().getExtras();
                hostel = (HostelModel) b.getSerializable("hostel");

                Database.isAlreadyAHostel(hostel.city_locality_name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            key[0] = ds.getKey();
                        }
                        hostelName.setText(hostel.name + "");
                        totalNoOfRooms.setText(hostel.total_rooms + "");
                        availableRooms.setText(hostel.available_rooms + "");
                        s_bed.setText(hostel.single_bed_rent + "");
                        d_bed.setText(hostel.double_bed_rent + "");
                        dorm.setText(hostel.dormitory_rent + "");

                        if (hostel.electricity)
                            electricity.setChecked(true);
                        if (hostel.gas)
                            gas.setChecked(true);
                        if (hostel.wifi)
                            wifi.setChecked(true);
                        if (hostel.ac)
                            ac.setChecked(true);
                        if (hostel.attached_baths)
                            baths.setChecked(true);
                        if (hostel.mess)
                            mess.setChecked(true);
                        if (hostel.type.equalsIgnoreCase("male"))
                            male.setChecked(true);
                        if (hostel.type.equalsIgnoreCase("female"))
                            female.setChecked(true);

                        progressDialog.dismiss();

                        btn_addHostel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (validate_hostel_name() && validate_total_rooms() && validate_available_rooms() &&
                                        validate_single_bed() && validate_double_bed() && validate_dormitory() &&
                                        validateHostelType() && validateSomeParameters()) {

                                    hostel.name = hostelName.getText().toString();
                                    hostel.total_rooms = Integer.parseInt(totalNoOfRooms.getText().toString());
                                    hostel.available_rooms = Integer.parseInt(availableRooms.getText().toString());
                                    hostel.single_bed_rent = Integer.parseInt(s_bed.getText().toString());
                                    hostel.double_bed_rent = Integer.parseInt(d_bed.getText().toString());
                                    hostel.dormitory_rent = Integer.parseInt(dorm.getText().toString());

                                    if (gas.isChecked())
                                        hostel.gas = true;
                                    else
                                        hostel.gas = false;
                                    if (wifi.isChecked())
                                        hostel.wifi = true;
                                    else
                                        hostel.wifi = false;
                                    if (electricity.isChecked())
                                        hostel.electricity = true;
                                    else
                                        hostel.electricity = false;
                                    if (mess.isChecked())
                                        hostel.mess = true;
                                    else
                                        hostel.mess = false;
                                    if (ac.isChecked())
                                        hostel.ac = true;
                                    else
                                        hostel.ac = false;
                                    if (baths.isChecked())
                                        hostel.attached_baths = true;
                                    else
                                        hostel.attached_baths = false;
                                    if (male.isChecked())
                                        hostel.type = "male";
                                    if (female.isChecked())
                                        hostel.type = "female";

                                    Intent i = new Intent(AddHostel.this, AddressActivity.class);
                                    i.putExtra("hostel", hostel);
                                    i.putExtra("key", key[0]);
                                    startActivityForResult(i, 100);

                                }

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

        } else {

            //A new hostel is going to be added
            Toast.makeText(getApplicationContext(),
                    "Please make sure to enter 0 if particular field in irrelevant for you!",
                    Toast.LENGTH_LONG).show();


            btn_addHostel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (validate_hostel_name() && validate_total_rooms() && validate_available_rooms() &&
                            validate_single_bed() && validate_double_bed() && validate_dormitory() &&
                            validateHostelType() && validateSomeParameters()) {

                        Intent i = new Intent(AddHostel.this, AddressActivity.class);
                        i.putExtra("name", hostelName.getText().toString());
                        i.putExtra("t_rooms", Integer.parseInt(totalNoOfRooms.getText().toString()));
                        i.putExtra("a_rooms", Integer.parseInt(availableRooms.getText().toString()));
                        i.putExtra("s_bed", Integer.parseInt(s_bed.getText().toString()));
                        i.putExtra("d_bed", Integer.parseInt(d_bed.getText().toString()));
                        i.putExtra("dorm", Integer.parseInt(dorm.getText().toString()));
                        i.putExtra("wifi", wifi.isChecked());
                        i.putExtra("gas", gas.isChecked());
                        i.putExtra("electricity", electricity.isChecked());
                        i.putExtra("a_baths", baths.isChecked());
                        i.putExtra("ac", ac.isChecked());
                        i.putExtra("mess", mess.isChecked());

                        if (male.isChecked())
                            i.putExtra("type", "male");
                        else
                            i.putExtra("type", "female");

                        startActivityForResult(i, 100);
                        finish();

                    }

                }
            });

        }


    }

    private void findViews() {
        hostelName = findViewById(R.id.et_hostel_name);
        totalNoOfRooms = findViewById(R.id.et_total_no_of_rooms);
        availableRooms = findViewById(R.id.et_available_rooms);
        btn_addHostel = findViewById(R.id.btn_add_Hostel_rl_addHOstel_activity);
        s_bed = (EditText) findViewById(R.id.et_single_bed_beds_activity);
        d_bed = (EditText) findViewById(R.id.et_double_bed_beds_activity);
        dorm = (EditText) findViewById(R.id.et_dormitory_beds_activity);
        wifi = findViewById(R.id.cb_wifi);
        gas = findViewById(R.id.cb_gas);
        electricity = findViewById(R.id.cb_electricity);
        mess = findViewById(R.id.cb_mess);
        baths = findViewById(R.id.cb_attached_baths);
        ac = findViewById(R.id.cb_ac);
        male = findViewById(R.id.cb_male_add_hostel_activity);
        female = findViewById(R.id.cb_female_add_hostel_activity);
    }


    private boolean validate_hostel_name() {
        if (hostelName.getText().toString().equals("")) {
            hostelName.setError("Please enter value!");
            return false;
        }
        return true;
    }

    private boolean validate_total_rooms() {
        if (totalNoOfRooms.getText().toString().equals("")) {
            totalNoOfRooms.setError("Please enter value!");
            return false;
        }
        return true;
    }

    private boolean validate_available_rooms() {
        if (availableRooms.getText().toString().equals("")) {
            availableRooms.setError("Please enter value!");
            return false;
        }
        return true;
    }

    private boolean validate_single_bed() {
        if (s_bed.getText().toString().equals("")) {
            s_bed.setError("Please enter value!");
            return false;
        }
        return true;
    }

    private boolean validate_double_bed() {
        if (d_bed.getText().toString().equals("")) {
            d_bed.setError("Please enter value!");
            return false;
        }
        return true;
    }

    private boolean validate_dormitory() {
        if (dorm.getText().toString().equals("")) {
            dorm.setError("Please enter value!");
            return false;
        }
        return true;
    }

    private boolean validateHostelType() {
        if (male.isChecked() && female.isChecked()) {
            male.setError("Please select one option!");
            female.setError("Please select one option!");
            return false;
        } else if (male.isChecked() == false && female.isChecked() == false) {
            male.setError("Please select one option!");
            female.setError("Please select one option!");
            return false;
        }
        return true;
    }

    private boolean validateSomeParameters(){
        if (Integer.parseInt(totalNoOfRooms.getText().toString()) < Integer.parseInt(availableRooms.getText().toString())){
            availableRooms.setError("Total no of rooms should be less than available no of rooms!");
            return false;
        }
//        if ((Integer.parseInt(s_bed.getText().toString()) < Integer.parseInt(d_bed.getText().toString())) ||
//                (Integer.parseInt(s_bed.getText().toString()) < Integer.parseInt(dorm.getText().toString())) ||
//                (Integer.parseInt(d_bed.getText().toString()) < Integer.parseInt(dorm.getText().toString()))){
//            s_bed.setError("Conflicting values for bed rents!");
//            d_bed.setError("Conflicting values for bed rents!");
//            dorm.setError("Conflicting values for bed rents!");
//            return false;
//        }
        return true;
    }

}
