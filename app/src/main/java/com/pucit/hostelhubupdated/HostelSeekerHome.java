package com.pucit.hostelhubupdated;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.pucit.hostelhubupdated.Aysnc_Tasks.delete_seeker_account_asynctask_hostelseekerhome_activity;
import com.pucit.hostelhubupdated.SessionManager.SessionManager;

public class HostelSeekerHome extends AppCompatActivity {

    private Button btn_request_res;
    private Button btn_communicate;
    private Button btn_delete_account;
    private Button btn_edit_profile;
    private Button btn_logout;

    private TextView name,email,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_seeker_home);

        Bundle b = getIntent().getExtras();

        findViews();
        name.setText(b.getString("name").toUpperCase() + "");
        email.setText(b.getString("email") + "");
        phone.setText(b.getString("phone") + "");

        btn_request_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Request module needs to be implemented!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.destroyUser(HostelSeekerHome.this);
                Intent i = new Intent(HostelSeekerHome.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

                if (getCallingActivity() != null) {
                    if (getCallingActivity().getClassName().toString().equalsIgnoreCase("com.pucit.hostelhubupdated.MapsActivity")){
                        finish();
                    }
                }
            }
        });

        btn_communicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HostelSeekerHome.this, MainActivity.class);
                startActivity(i);
            }
        });

        btn_delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = askOption();
                alert.show();
            }
        });

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HostelSeekerHome.this, SignUp.class);
                startActivityForResult(i, 100);
            }
        });

    }

    private AlertDialog askOption() {

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure?")
                .setIcon(R.drawable.ic_delete_icon)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        new delete_seeker_account_asynctask_hostelseekerhome_activity(HostelSeekerHome.this).execute();
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

    public void findViews() {

        btn_request_res = (Button) findViewById(R.id.btn_request_reg_hostel_seeker_home_activity);
        btn_communicate = (Button) findViewById(R.id.btn_communicate_hostel_seeker_home_activity);
        btn_delete_account = (Button) findViewById(R.id.btn_delete_profile_hostel_seeker_home_activity);
        btn_edit_profile = (Button) findViewById(R.id.btn_edit_profile_hostel_seeker_home_activity);
        btn_logout = (Button) findViewById(R.id.btn_logout_hostel_seeker_home_activity);
        name = findViewById(R.id.tv_name_hostelseekerhome_activity);
        email = findViewById(R.id.tv_email_hostelseekerhome_activity);
        phone = findViewById(R.id.tv_phone_hostelseekerhome_activity);

    }

}
