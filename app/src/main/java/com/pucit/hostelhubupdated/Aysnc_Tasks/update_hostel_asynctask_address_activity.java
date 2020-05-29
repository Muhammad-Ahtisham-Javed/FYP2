package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.pucit.hostelhubupdated.AddressActivity;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.HostelDetail;
import com.pucit.hostelhubupdated.HostelsList;
import com.pucit.hostelhubupdated.Models.HostelModel;
import com.pucit.hostelhubupdated.Models.UserModel;

public class update_hostel_asynctask_address_activity extends AsyncTask<HostelModel,Void,Void> {

    private ProgressDialog progressDialog;
    private Context context;
    private HostelModel hostel;
    private String key;

    public update_hostel_asynctask_address_activity(Context context,String key) {
        this.context = context;
        this.key = key;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Updating Information!");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(HostelModel... hostelModels) {

        this.hostel = hostelModels[0];
        Database.updateHostel(key,hostel);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Toast.makeText(context,"Hostel Info Updated!",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, HostelDetail.class);
                i.putExtra("hostel",hostel);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        }, 2000);

    }
}
