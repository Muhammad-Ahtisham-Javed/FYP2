package com.pucit.hostelhubupdated.Aysnc_Tasks;

import android.os.AsyncTask;

import com.pucit.hostelhubupdated.EmailProvider.GMailSender;

public class send_email_asynctask extends AsyncTask<String,Void,Void> {
    @Override
    protected Void doInBackground(String... strings) {
        GMailSender sender = new GMailSender("hostelhubfyp@gmail.com", "finalyearproject");
        try {
            sender.sendMail("Code Verification for HostelHub",
                    "Code: " + strings[0],
                    "hostelhubfyp@gmail.com",
                    strings[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
