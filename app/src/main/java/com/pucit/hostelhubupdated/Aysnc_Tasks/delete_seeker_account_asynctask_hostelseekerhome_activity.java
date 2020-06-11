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
import com.pucit.hostelhubupdated.Login;
import com.pucit.hostelhubupdated.SessionManager.SessionManager;

public class delete_seeker_account_asynctask_hostelseekerhome_activity extends AsyncTask<Void,Void,Void> {

    private Context context;
    private ProgressDialog progressDialog;
    private String cnic;

    public delete_seeker_account_asynctask_hostelseekerhome_activity(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Deleting account. Please wait...!");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        cnic = SessionManager.getUserCNIC(context);
        Database.isAlreadyAUser(cnic).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Database.deleteUser(ds.getKey());
                }
                progressDialog.dismiss();
                SessionManager.destroyUser(context);
                Intent i = new Intent(context, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
                Toast.makeText(context,"User has been deleted successfully!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return null;
    }
}
