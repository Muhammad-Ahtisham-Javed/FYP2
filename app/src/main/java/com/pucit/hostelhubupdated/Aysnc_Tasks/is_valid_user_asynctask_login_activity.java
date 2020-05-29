package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.HostelManagerHome;
import com.pucit.hostelhubupdated.HostelSeekerHome;
import com.pucit.hostelhubupdated.Models.UserModel;
import com.pucit.hostelhubupdated.SessionManager.SessionManager;

public class is_valid_user_asynctask_login_activity extends AsyncTask<UserModel,Void, Query> {

    private Context context;
    private UserModel obj;
    private ProgressDialog progressDialog;

    public is_valid_user_asynctask_login_activity(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Logging In! Please wait...");
        progressDialog.show();
    }

    @Override
    protected Query doInBackground(UserModel... userModels) {
        obj = userModels[0];
        return Database.isValidUser(obj.login,obj.password);
    }

    @Override
    protected void onPostExecute(Query query) {

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){
                    UserModel temp = null;
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        temp = ds.getValue(UserModel.class);
                    }
                    SessionManager.setUser(context,temp.cnic,temp.role);
                    if (temp.role.equalsIgnoreCase("manager")){
                        Intent i = new Intent(context, HostelManagerHome.class);
                        i.putExtra("name",temp.name);
                        i.putExtra("email",temp.email);
                        i.putExtra("phone",temp.phone);
                        progressDialog.dismiss();
                        context.startActivity(i);
                    }
                    else if (temp.role.equalsIgnoreCase("seeker")){
                        Intent i = new Intent(context, HostelSeekerHome.class);
                        i.putExtra("name",temp.name);
                        i.putExtra("email",temp.email);
                        i.putExtra("phone",temp.phone);
                        progressDialog.dismiss();
                        context.startActivity(i);
                    }

                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(context,"Invalid Credentials!",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
