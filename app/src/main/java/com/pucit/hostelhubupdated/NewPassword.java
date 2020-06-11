package com.pucit.hostelhubupdated;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pucit.hostelhubupdated.Aysnc_Tasks.update_password_asynctask_new_password_activity;

public class NewPassword extends AppCompatActivity {

    private Button btn_reset;
    private EditText et_newPass,et_confirmPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        btn_reset = (Button) findViewById(R.id.btn_reset_newpassword_activity);
        et_newPass = (EditText) findViewById(R.id.et_newPassword_newPassword_activity);
        et_confirmPass = (EditText) findViewById(R.id.et_confirmNewPass_newPassword_activity);


        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateNewPassword() && validateConfirmPassword() && et_newPass.getText().toString().equals(et_confirmPass.getText().toString()) )
                {
                    String cnic = getIntent().getExtras().getString("cnic");
                    new update_password_asynctask_new_password_activity(NewPassword.this).execute(cnic,et_newPass.getText().toString());
                }
                else
                {
                    et_confirmPass.setError("Password does not match!");
                }
            }
        });

    }

    private boolean validateNewPassword(){
        if (et_newPass.getText().toString().equals("")){
            et_newPass.setError("Please input Password!");
            return false;
        }
        else if (et_newPass.getText().toString().length() < 7){
            et_newPass.setError("Please input password containing more than 6 characters!");
            return false;
        }
        return true;
    }

    private boolean validateConfirmPassword(){
        if (et_confirmPass.getText().toString().equals("")){
            et_confirmPass.setError("Please input Password!");
            return false;
        }
        else if (et_confirmPass.getText().toString().length() < 7){
            et_confirmPass.setError("Please input password containing more than 6 characters!");
            return false;
        }
        return true;
    }

}
