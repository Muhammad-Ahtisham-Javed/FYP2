package com.pucit.hostelhubupdated;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pucit.hostelhubupdated.Aysnc_Tasks.get_user_asynctask_reset_password_login_activity;
import com.pucit.hostelhubupdated.Aysnc_Tasks.is_valid_user_asynctask_login_activity;
import com.pucit.hostelhubupdated.Models.UserModel;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private Button loginBtn;
    private Button signUpBtn;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText textinputusername = (EditText) findViewById(R.id.manager_login_username);
        final EditText textinputpassword = (EditText) findViewById(R.id.manager_login_password);
        signUpBtn = (Button) findViewById(R.id.btn_signup_ll_hostel_manager_login_activity);
        loginBtn = (Button) findViewById(R.id.btn_login_hostel_manager_login_activity);
        forgotPassword = findViewById(R.id.tv_forgot_password_hostel_manager_login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = textinputusername.getText().toString();
                final String pass = textinputpassword.getText().toString();

                if (isValidusername(username) && isValidPassword(pass)) {

                    UserModel obj = new UserModel();
                    obj.login = username;
                    obj.password = pass;

                    new is_valid_user_asynctask_login_activity(Login.this).execute(obj);
                    textinputusername.setText("");
                    textinputpassword.setText("");

                } else {

                    if (!isValidusername(username)) {
                        textinputusername.setError("Invalid Username");
                    }
                    if (!isValidPassword(pass)) {
                        textinputpassword.setError("Invalid Password");
                    }
                }


            }


        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivityForResult(intent,100);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_pop_up_cnic, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(findViewById(R.id.ll_whole_login_activity), Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });


                //Setting Input Validation on CNIC Popup
                Button submit = (Button) popupView.findViewById(R.id.btn_submit_popup_cnic_activity);
                final EditText editText = (EditText) popupView.findViewById(R.id.et_cnic_popup_cnic_activity);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editText.getText().toString().equals(""))
                            editText.setError("Please input CNIC");
                        else{
                            new get_user_asynctask_reset_password_login_activity(Login.this).execute(editText.getText().toString());
                            popupWindow.dismiss();
                        }
                    }
                });



            }
        });
    }

    private boolean isValidusername(String user) {
        if (!user.equals("")) {
            return true;
        }
        return false;
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }


}
