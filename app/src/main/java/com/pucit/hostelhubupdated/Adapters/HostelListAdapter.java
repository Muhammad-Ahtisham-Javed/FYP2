package com.pucit.hostelhubupdated.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.pucit.hostelhubupdated.Models.HostelModel;
import com.pucit.hostelhubupdated.R;

import java.util.ArrayList;

public class HostelListAdapter extends RecyclerView.Adapter<HostelListAdapter.MyViewHolder> {

    private ArrayList<HostelModel> hostelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView name;
        public TextView location;
        public TextView type;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imgV_hostel_list_item);
            name = (TextView) view.findViewById(R.id.tv_name_hostel_list_item);
            location = (TextView) view.findViewById(R.id.tv_location_hostel_list_item);
            type = (TextView) view.findViewById(R.id.tv_type_hostel_list_item);
        }
    }

    public HostelListAdapter(ArrayList<HostelModel> hostelList) {
        this.hostelList = hostelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hostel_list_item
                        , parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        HostelModel hostel = hostelList.get(position);

        holder.imageView.setImageResource(R.drawable.list_icon);
        holder.name.setText(hostel.name.toUpperCase());
        holder.location.setText("House No " +  hostel.house_no + ", Street No " +
                hostel.street_no + " " + hostel.locality.toLowerCase() + ", " + hostel.city.toUpperCase());
        holder.type.setText(hostel.type.toUpperCase());
    }

    @Override
    public int getItemCount() {
        return hostelList.size();
    }
}

