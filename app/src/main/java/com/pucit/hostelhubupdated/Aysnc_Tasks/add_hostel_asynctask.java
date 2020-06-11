package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.pucit.hostelhubupdated.AddressActivity;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Models.HostelModel;
import com.pucit.hostelhubupdated.SessionManager.SessionManager;

public class add_hostel_asynctask extends AsyncTask<HostelModel, Void, String>{

    private HostelModel obj;
    private Context context;
    private Button btn_anonymous, btn_choose,btn_upload;
    private ProgressDialog progressDialog;

    public add_hostel_asynctask(Context context, ProgressDialog progressDialog, Button btn_anonymous, Button btn_choose, Button btn_upload) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.btn_anonymous = btn_anonymous;
        this.btn_choose = btn_choose;
        this.btn_upload = btn_upload;
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context,"Hostel has been added successfully! Now upload images",Toast.LENGTH_LONG).show();
        AddressActivity.hostelID = s;
        btn_choose.setVisibility(View.VISIBLE);
        btn_upload.setVisibility(View.VISIBLE);
        btn_anonymous.setVisibility(View.GONE);
        progressDialog.dismiss();
    }

    @Override
    protected String doInBackground(HostelModel... hostelModels) {
        obj = hostelModels[0];
        obj.manager_cnic = SessionManager.getUserCNIC(context);
        return Database.insertHostel(obj);
    }
}
