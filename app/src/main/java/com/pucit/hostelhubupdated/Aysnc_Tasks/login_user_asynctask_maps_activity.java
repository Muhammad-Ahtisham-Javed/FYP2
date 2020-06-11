package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.HostelManagerHome;
import com.pucit.hostelhubupdated.HostelSeekerHome;
import com.pucit.hostelhubupdated.Models.UserModel;

public class login_user_asynctask_maps_activity extends AsyncTask<String,Void,Void> {

    private Context context;
    private ProgressDialog progressDialog;

    public login_user_asynctask_maps_activity(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Logging In! Please wait...");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(String... strings) {
        Database.isAlreadyAUser(strings[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){
                    UserModel temp = null;
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        temp = ds.getValue(UserModel.class);
                    }
                    if (temp.role.equalsIgnoreCase("manager")){
                        Intent i = new Intent(context, HostelManagerHome.class);
                        i.putExtra("name",temp.name);
                        i.putExtra("email",temp.email);
                        i.putExtra("phone",temp.phone);
                        progressDialog.dismiss();
                        ((Activity)context).startActivityForResult(i,100);
                    }
                    else if (temp.role.equalsIgnoreCase("seeker")){
                        Intent i = new Intent(context, HostelSeekerHome.class);
                        i.putExtra("name",temp.name);
                        i.putExtra("email",temp.email);
                        i.putExtra("phone",temp.phone);
                        progressDialog.dismiss();
                        ((Activity)context).startActivityForResult(i,100);
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context,"Uable to login. Please try again",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return null;
    }
}
