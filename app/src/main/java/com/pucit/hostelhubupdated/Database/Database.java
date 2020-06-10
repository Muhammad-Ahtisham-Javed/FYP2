package com.pucit.hostelhubupdated.Database;

import android.util.Log;
import androidx.annotation.NonNull;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pucit.hostelhubupdated.Models.HostelModel;
import com.pucit.hostelhubupdated.Models.UserModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private static FirebaseDatabase database;
    private static FirebaseStorage storage;
    private static StorageReference storageReference;
    private static DatabaseReference databaseReference;


    public static DatabaseReference getDbReference(){
        if (database == null){
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference();
            return databaseReference;
        }
        else
            return databaseReference;
    }

    public static StorageReference getStorageReference(){
        if (storage == null){
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            return storageReference;
        }
        else
            return storageReference;
    }

    public static Query isAlreadyAUser(String cnic){

        return getDbReference().child("users").orderByChild("cnic").equalTo(cnic);
    }


    public static void insertUser(UserModel obj){

        String key = getDbReference().child("users").push().getKey();
        Map<String,Object> map= new HashMap<>();

        map.put("role",obj.role.toLowerCase());
        map.put("login",obj.login.toLowerCase());
        map.put("name",obj.name.toLowerCase());
        map.put("password",obj.password);
        map.put("cnic",obj.cnic);
        map.put("phone",obj.phone);
        map.put("email",obj.email.toLowerCase());
        map.put("house_no",obj.house_no);
        map.put("street_no",obj.street_no);
        map.put("postal_code",obj.postal_code);
        map.put("locality",obj.locality.toLowerCase());
        map.put("city",obj.city.toLowerCase());
        map.put("login_password",obj.login.toLowerCase() + "_" + obj.password);

        getDbReference().child("users").child(key).setValue(map);

    }

    public static Query isValidUser(String login, String Pass){
        return getDbReference().child("users").orderByChild("login_password").equalTo(login.toLowerCase() + "_" + Pass);
    }

    public static String insertHostel(HostelModel obj){

        String key = getDbReference().child("hostels").push().getKey();
        Map<String,Object> map= new HashMap<>();

        map.put("manager_cnic",obj.manager_cnic);
        map.put("name",obj.name.toLowerCase());
        map.put("total_rooms",obj.total_rooms);
        map.put("available_rooms",obj.available_rooms);
        map.put("single_bed_rent",obj.single_bed_rent);
        map.put("double_bed_rent",obj.double_bed_rent);
        map.put("dormitory_rent",obj.dormitory_rent);
        map.put("house_no",obj.house_no);
        map.put("street_no",obj.street_no);
        map.put("postal_code",obj.postal_code);
        map.put("locality",obj.locality.toLowerCase());
        map.put("city",obj.city.toLowerCase());
        map.put("wifi",obj.wifi);
        map.put("gas",obj.gas);
        map.put("electricity",obj.electricity);
        map.put("mess",obj.mess);
        map.put("attached_baths",obj.attached_baths);
        map.put("ac",obj.ac);
        map.put("type",obj.type);
        map.put("city_locality_name",obj.city.toLowerCase() + "_" + obj.locality.toLowerCase() + "_" + obj.name.toLowerCase());

        getDbReference().child("hostels").child(key).setValue(map);

        GeoFire geoFire = new GeoFire(getDbReference().child("hostels_location"));
        geoFire.setLocation(key, new GeoLocation(37.7853889, -122.4056973), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error!=null){
                    Log.e("geofireerror","GeoFire Error while saving data " + error);
                }
            }
        });

        return key;

    }

    public static Query isAlreadyAHostel(String city_locality_name){
        return getDbReference().child("hostels").orderByChild("city_locality_name").equalTo(city_locality_name.toLowerCase());
    }

    public static Query getHostel(String key){
        return getDbReference().child("hostels").orderByKey().equalTo(key);
    }

    public static Query getImagesForHostel(String city_locality_name){
        return getDbReference().child("hostels").orderByChild("city_locality_name").equalTo(city_locality_name);
    }

    public static void updatePassword(String key, String login, String newPassword){
        getDbReference().child("users").child(key).child("password").setValue(newPassword);
        getDbReference().child("users").child(key).child("login_password").setValue(login + "_" + newPassword);
    }

    public static void updateUser(String key, UserModel obj){

        getDbReference().child("users").child(key).child("name").setValue(obj.name.toLowerCase());
        getDbReference().child("users").child(key).child("login").setValue(obj.login.toLowerCase());
        getDbReference().child("users").child(key).child("password").setValue(obj.password);
        getDbReference().child("users").child(key).child("phone").setValue(obj.phone);
        getDbReference().child("users").child(key).child("role").setValue(obj.role.toLowerCase());
        getDbReference().child("users").child(key).child("email").setValue(obj.email.toLowerCase());
        getDbReference().child("users").child(key).child("login_password").setValue(obj.login.toLowerCase() + "_" + obj.password);
        getDbReference().child("users").child(key).child("house_no").setValue(obj.house_no);
        getDbReference().child("users").child(key).child("street_no").setValue(obj.street_no);
        getDbReference().child("users").child(key).child("postal_code").setValue(obj.postal_code);
        getDbReference().child("users").child(key).child("locality").setValue(obj.locality.toLowerCase());
        getDbReference().child("users").child(key).child("city").setValue(obj.city.toLowerCase());

    }

    public static Query isAlreadyFeedbackByUser(String hostel_key,String feedbacker_cnic){
        return getDbReference().child("feedbacks").orderByChild("hostel_id||feedbacker").equalTo(hostel_key + "||" + feedbacker_cnic);
    }

    public static void insertFeedback(String hostel_key,String feedbacker_cnic,String feedbacker_name,String messRat,
                                      String cleanRat, String safeRat, String disciplineRat,String timeRat,
                                      String overallRat,String  comment){

        String key = getDbReference().child("feedbacks").push().getKey();

        HashMap<String,Object> map = new HashMap<>();
        map.put("hostel_id",hostel_key);
        map.put("feedbacker",feedbacker_cnic);
        map.put("hostel_id||feedbacker",hostel_key + "||" + feedbacker_cnic);
        map.put("feedbacker_name",feedbacker_name);
        map.put("mess_rating",messRat);
        map.put("cleanliness_rating",cleanRat);
        map.put("safety_rating",safeRat);
        map.put("discipline_rating",disciplineRat);
        map.put("time_strictness_rating",timeRat);
        map.put("overall_rating",overallRat);
        map.put("comment",comment);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        map.put("created_on",date);

        getDbReference().child("feedbacks").child(key).setValue(map);

    }


    public static void putAvgRatings(String hostelKey,String cleanRat,String disciplineRat,String messRat,String safetyRat,
                                      String timeRat,String overallRat){

        getDbReference().child("hostels").child(hostelKey).child("mess_rating").setValue(messRat);
        getDbReference().child("hostels").child(hostelKey).child("cleanliness_rating").setValue(cleanRat);
        getDbReference().child("hostels").child(hostelKey).child("safety_rating").setValue(safetyRat);
        getDbReference().child("hostels").child(hostelKey).child("discipline_rating").setValue(disciplineRat);
        getDbReference().child("hostels").child(hostelKey).child("time_strictness_rating").setValue(timeRat);
        getDbReference().child("hostels").child(hostelKey).child("overall_rating").setValue(overallRat);

    }

    public static Query getAllFeedbacks(String hostelKey){
        return getDbReference().child("feedbacks").orderByChild("hostel_id").equalTo(hostelKey);
    }

    public static Query getAllHostels(String cnic){
        return getDbReference().child("hostels").orderByChild("manager_cnic").equalTo(cnic);
    }


    public static void updateHostel(String key, HostelModel hostel){

        getDbReference().child("hostels").child(key).child("name").setValue(hostel.name.toLowerCase());
        getDbReference().child("hostels").child(key).child("total_rooms").setValue(hostel.total_rooms);
        getDbReference().child("hostels").child(key).child("available_rooms").setValue(hostel.available_rooms);
        getDbReference().child("hostels").child(key).child("single_bed_rent").setValue(hostel.single_bed_rent);
        getDbReference().child("hostels").child(key).child("double_bed_rent").setValue(hostel.double_bed_rent);
        getDbReference().child("hostels").child(key).child("dormitory_rent").setValue(hostel.dormitory_rent);
        getDbReference().child("hostels").child(key).child("type").setValue(hostel.type);
        getDbReference().child("hostels").child(key).child("gas").setValue(hostel.gas);
        getDbReference().child("hostels").child(key).child("wifi").setValue(hostel.wifi);
        getDbReference().child("hostels").child(key).child("electricity").setValue(hostel.electricity);
        getDbReference().child("hostels").child(key).child("mess").setValue(hostel.mess);
        getDbReference().child("hostels").child(key).child("ac").setValue(hostel.ac);
        getDbReference().child("hostels").child(key).child("attached_baths").setValue(hostel.attached_baths);
        getDbReference().child("hostels").child(key).child("house_no").setValue(hostel.house_no);
        getDbReference().child("hostels").child(key).child("street_no").setValue(hostel.street_no);
        getDbReference().child("hostels").child(key).child("postal_code").setValue(hostel.postal_code);
        getDbReference().child("hostels").child(key).child("locality").setValue(hostel.locality.toLowerCase());
        getDbReference().child("hostels").child(key).child("city").setValue(hostel.city.toLowerCase());
        getDbReference().child("hostels").child(key).child("city_locality_name").setValue(hostel.city.toLowerCase() + "_" +
                hostel.locality.toLowerCase() + "_" + hostel.name.toLowerCase());

    }

    public static void deleteHostel(String key){

        getDbReference().child("hostels").child(key).removeValue();
        getDbReference().child("hostels_location").child(key).removeValue();

    }

    public static void deleteImage(String url){

        StorageReference photoRef = getStorageReference().child("images").child(url);
        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                Log.d("mytest", "onSuccess: deleted file");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                Log.d("mytest", "onFailure: did not delete file");
            }
        });
    }

    public static void deleteFeedback(String feedback_key){
        getDbReference().child("feedbacks").child(feedback_key).removeValue();
    }

    public static void deleteUser(String key){
        getDbReference().child("users").child(key).removeValue();
    }

}


