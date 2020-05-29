package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.pucit.hostelhubupdated.AddressActivity;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Models.HostelModel;
import com.pucit.hostelhubupdated.SessionManager.SessionManager;

public class add_hostel_asynctask extends AsyncTask<HostelModel, Void, String>{

    private HostelModel obj;
    private Context context;

    public add_hostel_asynctask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context,"Hostel has been added successfully! Now upload images",Toast.LENGTH_LONG).show();
        AddressActivity.hostelID = s;
    }

    @Override
    protected String doInBackground(HostelModel... hostelModels) {
        obj = hostelModels[0];
        obj.manager_cnic = SessionManager.getUserCNIC(context);
        return Database.insertHostel(obj);
    }
}
