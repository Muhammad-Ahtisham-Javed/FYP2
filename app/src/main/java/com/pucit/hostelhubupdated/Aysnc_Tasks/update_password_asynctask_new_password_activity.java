package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Login;
import com.pucit.hostelhubupdated.NewPassword;

public class update_password_asynctask_new_password_activity extends AsyncTask<String,Void,Void> {

    private Context context;
    private String newPassword;
    private String key;
    private String login_password;

    public update_password_asynctask_new_password_activity(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {

        this.newPassword = strings[1];

        Database.isAlreadyAUser(strings[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                       key = ds.getKey();
                       login_password = ds.child("login_password").getValue().toString();
                    }

                    String login = login_password.substring(0,login_password.indexOf('_'));
                    Database.updatePassword(key,login,newPassword);
                    Intent i = new Intent(context,Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                    Toast.makeText(context,"Password has been updated successfully!",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return null;
    }


}
