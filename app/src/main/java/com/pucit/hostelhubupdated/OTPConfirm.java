package com.pucit.hostelhubupdated;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.pucit.hostelhubupdated.Aysnc_Tasks.is_already_user_asynctask_maps_activity;
import com.pucit.hostelhubupdated.Models.UserModel;

public class OTPConfirm extends AppCompatActivity {

    private Button btn;
    private EditText et_input;
    private UserModel obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpconfirm);

        btn = (Button) findViewById(R.id.btn_confrim_confrimotp);
        et_input = (EditText) findViewById(R.id.et_mobilecode_otpconfirm);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getCallingActivity() != null){

                    Bundle b = getIntent().getExtras();
                    String code = b.getString("code");
                    obj = (UserModel) b.getSerializable("user");

                    String temp = et_input.getText().toString();

                    if (temp.equals("") == true) {
                        et_input.setError("Please enter code!");
                    }
                    else if (temp.equals(code) == false){
                        et_input.setError("Incorrect Code");
                    }else {
                        new is_already_user_asynctask_maps_activity(OTPConfirm.this).execute(obj);
                    }

                }
                else {

                    //code to let user update his password

                    Bundle b = getIntent().getExtras();
                    String code = b.getString("code");
                    String cnic = b.getString("cnic");

                    String temp = et_input.getText().toString();

                    if (temp.equals("") == true) {
                        et_input.setError("Please enter code!");
                    }
                    else if (temp.equals(code) == false) {
                        et_input.setError("Incorrect Code");
                    }
                    else
                    {
                        Intent i = new Intent(OTPConfirm.this, NewPassword.class);
                        i.putExtra("cnic",cnic);
                        startActivity(i);
                    }

                }

            }
        });

    }


}
