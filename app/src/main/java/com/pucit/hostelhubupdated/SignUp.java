package com.pucit.hostelhubupdated;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Models.UserModel;
import com.pucit.hostelhubupdated.SessionManager.SessionManager;

public class SignUp extends AppCompatActivity {

    private Button btncreateaccount;
    private EditText textinputusername,textinputpassword,textinputCNIC,textinputcontact,textinputLoginName,textinputEmail;
    private CheckBox cbtn_hostel_seeker, cbtn_hostel_manager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if (getCallingActivity().getClassName().toString().equalsIgnoreCase("com.pucit.hostelhubupdated.HostelSeekerHome") ||
                getCallingActivity().getClassName().toString().equalsIgnoreCase("com.pucit.hostelhubupdated.HostelManagerHome")){

            //An existing user is going to update his information

            initializeViews();

            progressDialog = new ProgressDialog(SignUp.this);
            progressDialog.setMessage("Loading Info. Please wait...");
            progressDialog.show();

            String cnic = SessionManager.getUserCNIC(SignUp.this);
            final UserModel[] obj = new UserModel[1];
            if (cnic.equals("")){
                progressDialog.dismiss();
                Toast.makeText(SignUp.this,"Uable to retrieve CNIC!",Toast.LENGTH_SHORT).show();
                finish();
            }else{

                Database.isAlreadyAUser(cnic).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                          obj[0] = ds.getValue(UserModel.class);
                        }

                        textinputLoginName.setText(obj[0].login);
                        textinputusername.setText(obj[0].name);
                        textinputpassword.setText(obj[0].password);
                        textinputCNIC.setText(obj[0].cnic);
                        textinputCNIC.setFocusable(false);
                        textinputcontact.setText(obj[0].phone);
                        textinputEmail.setText(obj[0].email);

                        if (obj[0].role.equals("seeker")){
                            cbtn_hostel_seeker.setChecked(true);
                            cbtn_hostel_seeker.setEnabled(false);
                            cbtn_hostel_manager.setEnabled(false);
                        } else{
                            cbtn_hostel_manager.setChecked(true);
                            cbtn_hostel_manager.setEnabled(false);
                            cbtn_hostel_seeker.setEnabled(false);
                        }

                        progressDialog.dismiss();

                        btncreateaccount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String username = textinputusername.getText().toString();
                                String pass = textinputpassword.getText().toString();
                                String CNIC = textinputCNIC.getText().toString();
                                String Cell = textinputcontact.getText().toString();
                                String loginName = textinputLoginName.getText().toString();
                                String email = textinputEmail.getText().toString();
                                String type = get_user_type();

                                if (isValidString(email) && validateUsername(loginName) && isValidString(username) &&
                                        isValidPassword(pass) && isValidCNIC(CNIC) && isValidcontact(Cell) &&
                                        type != "" && type != "both") {


                                    Intent i = new Intent(SignUp.this, AddressActivity.class);
                                    i.putExtra("role",type);
                                    i.putExtra("login",loginName);
                                    i.putExtra("name",username);
                                    i.putExtra("pass",pass);
                                    i.putExtra("cnic",CNIC);
                                    i.putExtra("cell",Cell);
                                    i.putExtra("email",email);
                                    i.putExtra("user",obj[0]);

                                    startActivityForResult(i,100);

                                } else {

                                    if (!isValidString(email)){
                                        textinputEmail.setError("Invalid Email");
                                    }

                                    if (!isValidString(username)) {
                                        textinputusername.setError("Invalid Username");
                                    }


                                    if (!isValidPassword(pass)) {
                                        textinputpassword.setError("Invalid Password");
                                    }

                                    if (!isValidCNIC(CNIC)) {
                                        textinputCNIC.setError("Invalid CNIC");
                                    }

                                    if (!isValidcontact(Cell)) {
                                        textinputcontact.setError("Invalid Contact");
                                    }

                                    if (validateUsername(loginName) == false){
                                        textinputLoginName.setError("Invalid Login Name or login name should not contain _");
                                    }

                                    if (get_user_type() == ""){
                                        cbtn_hostel_manager.setError("Please Select Type");
                                    }

                                    if (get_user_type() == "both") {
                                        cbtn_hostel_manager.setError("Please select one option");
                                    }

                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

        }else{

            //A new user is going to create account

            initializeViews();

            btncreateaccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String username = textinputusername.getText().toString();
                    String pass = textinputpassword.getText().toString();
                    String CNIC = textinputCNIC.getText().toString();
                    String Cell = textinputcontact.getText().toString();
                    String loginName = textinputLoginName.getText().toString();
                    String email = textinputEmail.getText().toString();
                    String type = get_user_type();

                    if (isValidString(email) && validateUsername(loginName) && isValidString(username) && isValidPassword(pass) &&
                            isValidCNIC(CNIC) && isValidcontact(Cell) &&
                            type != "" && type != "both") {


                        Intent i = new Intent(SignUp.this, AddressActivity.class);
                        i.putExtra("role",type);
                        i.putExtra("login",loginName);
                        i.putExtra("name",username);
                        i.putExtra("pass",pass);
                        i.putExtra("cnic",CNIC);
                        i.putExtra("cell",Cell);
                        i.putExtra("email",email);

                        startActivityForResult(i,100);
                        finish();

                    } else {

                        if (!isValidString(email)){
                            textinputEmail.setError("Invalid Email");
                        }

                        if (!isValidString(username)) {
                            textinputusername.setError("Invalid Username");
                        }


                        if (!isValidPassword(pass)) {
                            textinputpassword.setError("Invalid Password");
                        }

                        if (!isValidCNIC(CNIC)) {
                            textinputCNIC.setError("Invalid CNIC");
                        }

                        if (!isValidcontact(Cell)) {
                            textinputcontact.setError("Invalid Contact");
                        }

                        if (validateUsername(loginName) == false){
                            textinputLoginName.setError("Invalid Login Name or login name should not contain _");
                        }

                        if (get_user_type() == ""){
                            cbtn_hostel_manager.setError("Please Select Type");
                        }

                        if (get_user_type() == "both") {
                            cbtn_hostel_manager.setError("Please select one option");
                        }

                    }
                }
            });

        }



    }

    private boolean isValidString(String username) {
        if (!username.equals("")) {
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

    // validating CNIC with retype password
    private boolean isValidCNIC(String cnic) {
        if (cnic != null && cnic.length() == 13) {
            return true;
        }
        return false;
    }

    // validating Contact with retype password
    private boolean isValidcontact(String contact) {
        if (contact != null && contact.length() == 11 && contact.startsWith("03")) {
            return true;
        }
        return false;
    }

    private String get_user_type(){
        if (cbtn_hostel_seeker.isChecked() && cbtn_hostel_manager.isChecked())
            return "both";
        if (cbtn_hostel_manager.isChecked())
            return "Manager";
        else if (cbtn_hostel_seeker.isChecked())
            return "Seeker";
        else
            return "";
    }

    private boolean validateUsername(String username){
        if (username.equals("") || username.indexOf('_') >= 0){
            return false;
        }
        return true;
    }

    private void initializeViews(){

        textinputusername = (EditText) findViewById(R.id.manager_signup_username);
        textinputpassword = (EditText) findViewById(R.id.manager_signup_password);
        textinputCNIC = (EditText) findViewById(R.id.manager_signup_cnic);
        textinputcontact = (EditText) findViewById(R.id.manager_signup_contact);
        textinputLoginName = (EditText) findViewById(R.id.manager_signup_login_name);
        textinputEmail = (EditText) findViewById(R.id.manager_signup_email);
        cbtn_hostel_manager = (CheckBox) findViewById(R.id.cbtn_hostel_manager_signup_activity);
        cbtn_hostel_seeker = (CheckBox) findViewById(R.id.cbtn_hostel_seeker_signup_activity);
        btncreateaccount = (Button) findViewById(R.id.btn_next_signup_activity);

    }

}
