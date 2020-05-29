package com.pucit.hostelhubupdated.Aysnc_Tasks;

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

public class update_user_asynctask_address_activity extends AsyncTask<UserModel, Void, Void> {

    private Context context;
    private ProgressDialog progressDialog;
    private UserModel userModel;

    public update_user_asynctask_address_activity(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Updating info. Please wait...");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(UserModel... userModels) {
        this.userModel = userModels[0];
        Database.isAlreadyAUser(userModel.cnic).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = null;
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    key = ds.getKey();
                }
                if (key != null){
                    Database.updateUser(key,userModel);
                    Toast.makeText(context,"User info has been updated successfully!",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    if (userModel.role.equalsIgnoreCase("seeker")){
                        Intent i = new Intent(context,HostelSeekerHome.class);
                        i.putExtra("name",userModel.name);
                        i.putExtra("email",userModel.email);
                        i.putExtra("phone",userModel.phone);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                    } else{
                        Intent i = new Intent(context,HostelManagerHome.class);
                        i.putExtra("name",userModel.name);
                        i.putExtra("email",userModel.email);
                        i.putExtra("phone",userModel.phone);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return null;
    }
}
