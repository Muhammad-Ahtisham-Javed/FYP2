package com.pucit.hostelhubupdated;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pucit.hostelhubupdated.Aysnc_Tasks.is_already_a_hostel_asynctask_address_activity;
import com.pucit.hostelhubupdated.Aysnc_Tasks.send_email_asynctask;
import com.pucit.hostelhubupdated.Aysnc_Tasks.update_hostel_asynctask_address_activity;
import com.pucit.hostelhubupdated.Aysnc_Tasks.update_user_asynctask_address_activity;
import com.pucit.hostelhubupdated.Database.Database;
import com.pucit.hostelhubupdated.Models.HostelModel;
import com.pucit.hostelhubupdated.Models.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class AddressActivity extends AppCompatActivity {

    public static String hostelID = null; //Required for working of storeURL function. Initialized in add_hostel_asynctask.java
    //Actually it is done to get hostel_id for which images have to uploaded.
    private int PICK_IMAGE_REQUEST = 100;
    private EditText house, street, code, locality, city;
    private Button btn_anonymous, btn_choose, btn_upload;
    private ArrayList<Uri> FileList;
    private Uri FileUri;
    private ProgressDialog progressDialog;
    private int upload_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        FileList = new ArrayList<Uri>();
        progressDialog = new ProgressDialog(AddressActivity.this);
        progressDialog.setMessage("Uploading Images. Please Wait...");

        findViews();

        if (getCallingActivity().getClassName().equalsIgnoreCase("com.pucit.hostelhubupdated.SignUp")) {

            if (getIntent().getExtras().getSerializable("user") != null) {

                //It means an existing user is updating his information!

                Bundle b = getIntent().getExtras();
                UserModel obj = (UserModel) b.getSerializable("user");

                btn_anonymous.setText("Update");
                btn_choose.setVisibility(View.INVISIBLE);
                btn_upload.setVisibility(View.INVISIBLE);

                house.setText("" + obj.house_no);
                street.setText("" + obj.street_no);
                code.setText("" + obj.postal_code);
                locality.setText("" + obj.locality);
                city.setText("" + obj.city);

                btn_anonymous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int h = -1;
                        int s = -1;
                        int c = -1;

                        if (house.getText().toString().equals("")) {
                            house.setError("Please enter valid Number");
                        } else {
                            h = Integer.parseInt(house.getText().toString());
                        }

                        if (street.getText().toString().equals("")) {
                            street.setError("Please enter valid Number");
                        } else {
                            s = Integer.parseInt(street.getText().toString());
                        }

                        if (code.getText().toString().equals("")) {
                            code.setError("Please enter valid Number");
                        } else {
                            c = Integer.parseInt(code.getText().toString());
                        }

                        String l = locality.getText().toString();
                        String cty = city.getText().toString();

                        if (isValidString(l) && isValidString(cty)) {

                            Bundle b = getIntent().getExtras();
                            UserModel obj = (UserModel) b.getSerializable("user");

                            if (obj.name.equalsIgnoreCase(b.getString("name")) == false)
                                obj.name = b.getString("name");
                            if (obj.login.equalsIgnoreCase(b.getString("login")) == false)
                                obj.login = b.getString("login");
                            if (obj.password.equalsIgnoreCase(b.getString("pass")) == false)
                                obj.password = b.getString("pass");
                            if (obj.role.equalsIgnoreCase(b.getString("role")) == false)
                                obj.role = b.getString("role");
                            if (obj.phone.equalsIgnoreCase(b.getString("cell")) == false)
                                obj.phone = b.getString("cell");
                            if (obj.email.equalsIgnoreCase(b.getString("email")) == false)
                                obj.email = b.getString("email");

                            obj.street_no = s;
                            obj.house_no = h;
                            obj.postal_code = c;
                            obj.locality = l;
                            obj.city = cty;

                            new update_user_asynctask_address_activity(AddressActivity.this).execute(obj);


                        } else if (isValidString(l) == false)
                            locality.setError("Please enter valid name!");
                        else if (isValidString(cty) == false)
                            city.setError("Please enter valid name!");


                    }
                });


            } else {

                //It means a new user is going to create an account

                btn_anonymous.setText("Send Code");
                btn_choose.setVisibility(View.INVISIBLE);
                btn_upload.setVisibility(View.INVISIBLE);

                btn_anonymous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int h = -1;
                        int s = -1;
                        int c = -1;

                        if (house.getText().toString().equals("")) {
                            house.setError("Please enter valid Number");
                        } else {
                            h = Integer.parseInt(house.getText().toString());
                        }

                        if (street.getText().toString().equals("")) {
                            street.setError("Please enter valid Number");
                        } else {
                            s = Integer.parseInt(street.getText().toString());
                        }

                        if (code.getText().toString().equals("")) {
                            code.setError("Please enter valid Number");
                        } else {
                            c = Integer.parseInt(code.getText().toString());
                        }

                        String l = locality.getText().toString();
                        String cty = city.getText().toString();

                        if (isValidString(l) && isValidString(cty)) {

                            Bundle b = getIntent().getExtras();
                            UserModel obj = new UserModel();

                            obj.role = b.getString("role");
                            obj.name = b.getString("name");
                            obj.login = b.getString("login");
                            obj.password = b.getString("pass");
                            obj.cnic = b.getString("cnic");
                            obj.phone = b.getString("cell");
                            obj.email = b.getString("email");

                            obj.street_no = s;
                            obj.house_no = h;
                            obj.postal_code = c;
                            obj.locality = l;
                            obj.city = cty;

                            Random random = new Random();
                            String rand = String.format("%04d", random.nextInt(10000));

                            Intent i = new Intent(AddressActivity.this, OTPConfirm.class);
                            i.putExtra("user", obj);
                            i.putExtra("code", rand);

                            new send_email_asynctask().execute(rand.toString(), obj.email);
                            Toast.makeText(AddressActivity.this, "Code has been sent to email. Email may take few minutes!", Toast.LENGTH_SHORT).show();
                            startActivityForResult(i, 100);
                            finish();


                        } else if (isValidString(l) == false)
                            locality.setError("Please enter valid name!");
                        else if (isValidString(cty) == false)
                            city.setError("Please enter valid name!");


                    }
                });

            }


        } else if (getCallingActivity().getClassName().equalsIgnoreCase("com.pucit.hostelhubupdated.AddHostel")) {

            if (getIntent().getExtras().getSerializable("hostel") != null) {
                //It means a user is updating hostel information

                final Bundle b = getIntent().getExtras();
                final HostelModel hostel = (HostelModel) b.getSerializable("hostel");
                final String key = b.getString("key");

                house.setText(hostel.house_no + "");
                street.setText(hostel.street_no + "");
                code.setText(hostel.postal_code + "");
                locality.setText(hostel.locality + "");
                city.setText(hostel.city + "");

                btn_anonymous.setText("Update");
                btn_choose.setVisibility(View.INVISIBLE);
                btn_upload.setVisibility(View.INVISIBLE);

                btn_anonymous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int h = -1;
                        int s = -1;
                        int c = -1;

                        if (house.getText().toString().equals("")) {
                            house.setError("Please enter valid Number");
                        } else {
                            h = Integer.parseInt(house.getText().toString());
                        }

                        if (street.getText().toString().equals("")) {
                            street.setError("Please enter valid Number");
                        } else {
                            s = Integer.parseInt(street.getText().toString());
                        }

                        if (code.getText().toString().equals("")) {
                            code.setError("Please enter valid Number");
                        } else {
                            c = Integer.parseInt(code.getText().toString());
                        }

                        String l = locality.getText().toString();
                        String cty = city.getText().toString();

                        if (isValidString(l) && isValidString(cty)) {

                            hostel.street_no = s;
                            hostel.house_no = h;
                            hostel.postal_code = c;
                            hostel.locality = l;
                            hostel.city = cty;

                            new update_hostel_asynctask_address_activity(AddressActivity.this,key).execute(hostel);

                        } else if (isValidString(l) == false)
                            locality.setError("Please enter valid name!");
                        else if (isValidString(cty) == false)
                            city.setError("Please enter valid name!");


                    }
                });

            } else {

                //It means a new hostel is going to be added!

                btn_anonymous.setText("Add Hostel");
                btn_choose.setVisibility(View.INVISIBLE);
                btn_upload.setVisibility(View.INVISIBLE);

                btn_anonymous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int h = -1;
                        int s = -1;
                        int c = -1;

                        if (house.getText().toString().equals("")) {
                            house.setError("Please enter valid Number");
                        } else {
                            h = Integer.parseInt(house.getText().toString());
                        }

                        if (street.getText().toString().equals("")) {
                            street.setError("Please enter valid Number");
                        } else {
                            s = Integer.parseInt(street.getText().toString());
                        }

                        if (code.getText().toString().equals("")) {
                            code.setError("Please enter valid Number");
                        } else {
                            c = Integer.parseInt(code.getText().toString());
                        }

                        String l = locality.getText().toString();
                        String cty = city.getText().toString();

                        if (isValidString(l) && isValidString(cty)) {

                            Bundle b = getIntent().getExtras();
                            HostelModel obj = new HostelModel();

                            obj.name = b.getString("name");
                            obj.total_rooms = b.getInt("t_rooms");
                            obj.available_rooms = b.getInt("a_rooms");
                            obj.single_bed_rent = b.getInt("s_bed");
                            obj.double_bed_rent = b.getInt("d_bed");
                            obj.dormitory_rent = b.getInt("dorm");
                            obj.wifi = b.getBoolean("wifi");
                            obj.gas = b.getBoolean("gas");
                            obj.electricity = b.getBoolean("electricity");
                            obj.attached_baths = b.getBoolean("a_baths");
                            obj.mess = b.getBoolean("mess");
                            obj.ac = b.getBoolean("ac");
                            obj.type = b.getString("type");

                            obj.street_no = s;
                            obj.house_no = h;
                            obj.postal_code = c;
                            obj.locality = l;
                            obj.city = cty;


                            new is_already_a_hostel_asynctask_address_activity(AddressActivity.this,btn_anonymous,btn_choose,btn_upload).execute(obj);


                        } else if (isValidString(l) == false)
                            locality.setError("Please enter valid name!");
                        else if (isValidString(cty) == false)
                            city.setError("Please enter valid name!");


                    }
                });

            }


        }


        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                progressDialog.show();
                StorageReference ImageFolder = Database.getStorageReference().child("images");
                if (FileList.size() == 0) {
                    progressDialog.dismiss();
                    Intent i = new Intent(AddressActivity.this,HostelManagerHome.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(i, 100);
//                    hostelID = null;
                    return;
                }
                final int[] temp = {0};
                final int count = FileList.size();
                for (upload_count = 0; upload_count < FileList.size(); upload_count++) {

                    Uri IndividualFile = FileList.get(upload_count);
//                    final StorageReference ImageName = ImageFolder.child(UUID.randomUUID().toString() + "_" + IndividualFile.getLastPathSegment());
                    final StorageReference ImageName = ImageFolder.child(hostelID + "__" + upload_count);
                    ImageName.putFile(IndividualFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = String.valueOf(uri);
                                    storeURL(url);
                                    temp[0]++;
                                    if (temp[0] == count){
                                        progressDialog.dismiss();
                                        Intent i = new Intent(AddressActivity.this,HostelManagerHome.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivityForResult(i, 100);
//                                        hostelID = null;
                                    }
                                }
                            });
                        }
                    });
                }
                FileList.clear();
            }
        });


    }


    private void findViews() {
        btn_anonymous = (Button) findViewById(R.id.btn_anonymous_address_activity);
        btn_choose = (Button) findViewById(R.id.btn_choose_images_address_activity);
        btn_upload = (Button) findViewById(R.id.btn_upload_images_address_activity);
        house = (EditText) findViewById(R.id.et_houseNo_address_activity);
        street = (EditText) findViewById(R.id.et_streetNo_address_activity);
        code = (EditText) findViewById(R.id.et_postalCode_address_activity);
        locality = (EditText) findViewById(R.id.et_locality_address_activity);
        city = (EditText) findViewById(R.id.et_city_address_activity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    int countClipData = data.getClipData().getItemCount();
                    int currentImageSelect = 0;
                    while (currentImageSelect < countClipData) {
                        FileUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                        FileList.add(FileUri);
                        currentImageSelect = currentImageSelect + 1;
                    }
                } else {
                    Toast.makeText(this, "Please Select Multiple Files", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void storeURL(String url) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("filelink", url);
        Database.getDbReference().child("hostels").child(hostelID).child("images").push().setValue(hashMap);

    }

    private boolean isValidString(String str) {
        if (str.equals("")) {
            return false;
        }
        return true;
    }

    private boolean isValidNum(int num) {
        if (num <= 0 || num >= 1000) {
            return false;
        }
        return true;
    }


}
