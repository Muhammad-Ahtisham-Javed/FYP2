package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Models.UserModel;

public class get_user_asynctask_hostelmanagerhome_activity extends AsyncTask<String,Void,Void> {

    private Context context;
    private String cnic;
    private UserModel user;
    private ProgressDialog progressDialog;
    private TextView name,email,phone;

    public get_user_asynctask_hostelmanagerhome_activity(Context context, TextView name, TextView email, TextView phone) {
        this.context = context;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Loading...!");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(String... strings) {

        cnic = strings[0];
        Database.isAlreadyAUser(cnic).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    user = ds.getValue(UserModel.class);
                }
                name.setText(user.name.toUpperCase() + "");
                email.setText(user.email + "");
                phone.setText(user.phone + "");
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return null;
    }
}
