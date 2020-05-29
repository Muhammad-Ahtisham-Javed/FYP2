package com.pucit.hostelhubupdated.Aysnc_Tasks;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.OTPConfirm;

import java.util.Random;

public class get_user_asynctask_reset_password_login_activity extends AsyncTask<String, Void, Query> {

    private Context context;
    private String cnic;
    private ProgressDialog progressDialog;

    public get_user_asynctask_reset_password_login_activity(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...!");
        progressDialog.show();
    }

    @Override
    protected Query doInBackground(String... strings) {
        cnic = strings[0];
        return Database.isAlreadyAUser(strings[0]);
    }

    @Override
    protected void onPostExecute(Query query) {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String email = ds.child("email").getValue().toString();

                        Random random = new Random();
                        String rand = String.format("%04d", random.nextInt(10000));

                        Intent i = new Intent(context, OTPConfirm.class);
                        i.putExtra("cnic", cnic);
                        i.putExtra("code", rand);

                        new send_email_asynctask().execute(rand.toString(), email);
                        progressDialog.dismiss();
                        Toast.makeText(context, "Code has been sent to email!", Toast.LENGTH_SHORT).show();
                        context.startActivity(i);

                    }
                } else {
                    Toast.makeText(context, "User with such CNIC does not exist!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
