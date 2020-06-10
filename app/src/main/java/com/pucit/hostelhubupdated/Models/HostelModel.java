package com.pucit.hostelhubupdated.Models;

import java.io.Serializable;

public class HostelModel implements Serializable {

    public String manager_cnic;

    public String name;
    public int total_rooms;
    public int available_rooms;
    public int single_bed_rent;
    public int double_bed_rent;
    public int dormitory_rent;
    public boolean wifi;
    public boolean gas;
    public boolean electricity;
    public boolean mess;
    public boolean attached_baths;
    public boolean ac;
    public int house_no;
    public int street_no;
    public int postal_code;
    public String locality;
    public String city;
    public String type;
    public String city_locality_name;
    public String mess_rating;
    public String cleanliness_rating;
    public String discipline_rating;
    public String safety_rating;
    public String time_strictness_rating;
    public String overall_rating;
}
