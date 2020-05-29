package com.pucit.hostelhubupdated;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.pucit.hostelhubupdated.Aysnc_Tasks.delete_manager_account_asynctask_hostelmanagerhome_activity;
import com.pucit.hostelhubupdated.Aysnc_Tasks.get_user_asynctask_hostelmanagerhome_activity;
import com.pucit.hostelhubupdated.SessionManager.SessionManager;

public class HostelManagerHome extends AppCompatActivity {

    private Button viewHostels;
    private Button addHostel;
    private Button editProfile;
    private Button logout;
    private Button viewNotifications;
    private Button communicate;
    private Button deleteAccount;

    private TextView name,email,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_manager_home);

        findViews();
        if (getIntent().getExtras() == null){
            String cnic = SessionManager.getUserCNIC(HostelManagerHome.this);
            new get_user_asynctask_hostelmanagerhome_activity(HostelManagerHome.this,name,email,phone).execute(cnic);
        }else{
            Bundle b = getIntent().getExtras();
            name.setText(b.getString("name").toUpperCase() + "");
            email.setText(b.getString("email") + "");
            phone.setText(b.getString("phone") + "");
        }


        viewHostels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(), HostelsList.class);
                startActivityForResult(i,100);
            }
        });

        addHostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HostelManagerHome.this, AddHostel.class);
                startActivity(i);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HostelManagerHome.this, SignUp.class);
                startActivityForResult(i,100);
            }
        });

        viewNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HostelManagerHome.this, NotificationList.class);
                startActivity(i);
            }
        });

        communicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HostelManagerHome.this, NotificationList.class);
                startActivity(i);
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = askOption();
                alert.show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SessionManager.destroyUser(HostelManagerHome.this);
                Intent i = new Intent(HostelManagerHome.this,Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

                if (getCallingActivity() != null) {
                    if (getCallingActivity().getClassName().toString().equalsIgnoreCase("com.pucit.hostelhubupdated.MapsActivity")){
                        finish();
                    }
                }


            }
        });



    }

    private AlertDialog askOption() {

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure? All managed hostels will also be deleted!")
                .setIcon(R.drawable.ic_delete_icon)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        new delete_manager_account_asynctask_hostelmanagerhome_activity(HostelManagerHome.this).execute();
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

    private void findViews(){
        viewHostels=findViewById(R.id.btn_view_hostel_ll_hostel_manager);
        addHostel=findViewById(R.id.btn_add_Hostel_ll_hostel_manager_home_activity);
        editProfile=findViewById(R.id.btn_Edit_hostel_ll_hostel_manager);
        logout=findViewById(R.id.btn_logout);
        viewNotifications =findViewById(R.id.btn_view_notification_ll_hostel_manager);
        communicate=findViewById(R.id.btn_communicate_ll_hostel_manager);
        deleteAccount = findViewById(R.id.btn_delete_account_ll_hostel_manager_home_activity);
        name = findViewById(R.id.tv_name_hostelmanagerhome_activity);
        email = findViewById(R.id.tv_email_hostelmanagerhome_activity);
        phone = findViewById(R.id.tv_phone_hostelmanagerhome_activity);
    }
}
