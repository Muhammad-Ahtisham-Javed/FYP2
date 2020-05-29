package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.os.AsyncTask;

import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Models.UserModel;

public class add_user_asynctask extends AsyncTask<UserModel, Void, Void> {
    @Override
    protected Void doInBackground(UserModel... userModels) {
        Database.insertUser(userModels[0]);
        return null;
    }
}
