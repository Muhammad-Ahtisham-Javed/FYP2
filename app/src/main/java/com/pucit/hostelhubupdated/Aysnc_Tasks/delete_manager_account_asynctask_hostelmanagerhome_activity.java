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
import java.util.ArrayList;

public class delete_manager_account_asynctask_hostelmanagerhome_activity extends AsyncTask<Void, Void, Void> {

    private Context context;
    private ProgressDialog progressDialog;
    private String cnic;
    private ArrayList<String> hostelKeyList;
    private ArrayList<Integer> hostelImageCountList;

    public delete_manager_account_asynctask_hostelmanagerhome_activity(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        hostelKeyList = new ArrayList<>();
        hostelImageCountList = new ArrayList<>();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Deleting Account. Please wait...");
        progressDialog.show();

    }

    @Override
    protected Void doInBackground(Void... voids) {

        cnic = SessionManager.getUserCNIC(context);
        Database.getAllHostels(cnic).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    hostelKeyList.add(ds.getKey());
                    hostelImageCountList.add((int) ds.child("images").getChildrenCount());
                }
                for (int i = 0; i < hostelKeyList.size(); i++) {
                    final String key = hostelKeyList.get(i);
                    for (int j = 0; j < hostelImageCountList.get(i); j++) {
                        Database.deleteImage(key + "__" + j);
                    }
                    Database.getAllFeedbacks(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Database.deleteFeedback(ds.getKey());
                            }
                            Database.deleteHostel(key);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                Database.isAlreadyAUser(cnic).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            Database.deleteUser(ds.getKey());
                        }
                        SessionManager.destroyUser(context);
                        progressDialog.dismiss();
                        Intent i = new Intent(context, Login.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                        Toast.makeText(context,"User has been deleted successfully!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        return null;
    }

}
