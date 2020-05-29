package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Models.HostelModel;

public class is_already_a_hostel_asynctask_address_activity extends AsyncTask<HostelModel,Void, Query> {

    private Context context;
    private HostelModel obj;

    public is_already_a_hostel_asynctask_address_activity(Context context) {
        this.context = context;
    }

    @Override
    protected Query doInBackground(HostelModel... hostelModels) {
        obj = hostelModels[0];
        return Database.isAlreadyAHostel( obj.city + "_" + obj.locality + "_" + obj.name);
    }

    @Override
    protected void onPostExecute(Query query) {

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){
                    Toast.makeText(context,"Already a hostel with such attributes!",Toast.LENGTH_SHORT).show();
                }
                else{
                    new add_hostel_asynctask(context).execute(obj);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
