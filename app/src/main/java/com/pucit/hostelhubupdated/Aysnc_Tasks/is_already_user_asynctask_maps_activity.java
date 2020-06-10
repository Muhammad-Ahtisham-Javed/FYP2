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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.HostelManagerHome;
import com.pucit.hostelhubupdated.HostelSeekerHome;
import com.pucit.hostelhubupdated.SessionManager.SessionManager;
import com.pucit.hostelhubupdated.Models.UserModel;

public class is_already_user_asynctask_maps_activity extends AsyncTask<UserModel,Void,Query> {

    private Context context;
    private UserModel obj;
    private ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Creating Account! Please Wait...");
        progressDialog.show();
    }

    public is_already_user_asynctask_maps_activity(Context context) {
        this.context = context;
    }

    @Override
    protected Query doInBackground(UserModel... userModels) {
        obj = userModels[0];
        return Database.isAlreadyAUser(obj.cnic);
    }

    @Override
    protected void onPostExecute(Query q) {

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Toast.makeText(context, "Already a user with such CNIC!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    ((Activity)context).finish();
                }
                else {

                    new add_user_asynctask().execute(obj);

                    SessionManager.setUser(context,obj.cnic,obj.role);

                    if (obj.role.toString().equalsIgnoreCase("seeker")){
                        Intent i = new Intent(context, HostelSeekerHome.class);
                        i.putExtra("name",obj.name);
                        i.putExtra("email",obj.email);
                        i.putExtra("phone",obj.phone);
                        progressDialog.dismiss();
                        ((Activity)context).startActivityForResult(i,100);
                        ((Activity)context).finish();
                        Toast.makeText(context, "User has been added successfully!", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent i = new Intent(context, HostelManagerHome.class);
                        i.putExtra("name",obj.name);
                        i.putExtra("email",obj.email);
                        i.putExtra("phone",obj.phone);
                        progressDialog.dismiss();
                        ((Activity)context).startActivityForResult(i,100);
                        ((Activity)context).finish();
                        Toast.makeText(context, "User has been added successfully!", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
