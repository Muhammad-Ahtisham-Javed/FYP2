package com.pucit.hostelhubupdated.SessionManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager extends Activity {

    private static SharedPreferences sp;

    public static SharedPreferences getMySharedPreferences(Context context){
        if (sp == null)
            sp = context.getSharedPreferences("user_info", MODE_PRIVATE);
        return sp;
    }

    public static boolean setUser(Context context, String cnic, String role){

        SharedPreferences.Editor editor = getMySharedPreferences(context).edit();
        editor.putString("cnic",cnic);
        editor.putString("role",role.toLowerCase());
        if (editor.commit() == true)
            return true;
        else
            return false;
    }


    public static boolean destroyUser(Context context){

        SharedPreferences.Editor editor = getMySharedPreferences(context).edit();
        editor.clear();
        editor.commit();
        return true;
    }


    public static String getUserCNIC(Context context){
        return getMySharedPreferences(context).getString("cnic","");
    }

    public static String getUserType(Context context){
        return getMySharedPreferences(context).getString("role","");
    }

}
