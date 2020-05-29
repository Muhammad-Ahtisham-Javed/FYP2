package com.pucit.hostelhubupdated.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pucit.hostelhubupdated.Models.FeedbackModel;
import com.pucit.hostelhubupdated.R;

import java.util.ArrayList;

public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.ViewHolder> {

    Context application;
    ArrayList<FeedbackModel> data;

    public FeedbackListAdapter(Context application, ArrayList<FeedbackModel> data) {
        this.application = application;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedbacklist_item
                        , parent, false);

        return new FeedbackListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FeedbackModel i = data.get(position);

        holder.name.setText(i.feedbacker_name.toUpperCase());
        holder.date.setText(i.created_on);
        holder.clean_rating.setText("Cleanliness:  " + i.cleanliness_rating);
        holder.safe_rating.setText("Safety:  " + i.safety_rating);
        holder.discipline_rating.setText("Discipline:  " + i.discipline_rating);
        holder.mess_rating.setText("Mess Quality:  " + i.mess_rating);
        holder.time_rating.setText("Time Strictness:  " + i.time_strictness_rating);
        holder.overall_rating.setText("Overall Rating:  " + i.overall_rating);
        holder.comment.setText(i.comment);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView date;
        public TextView clean_rating,safe_rating,discipline_rating,time_rating,mess_rating,overall_rating;
        public TextView comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name_feedbacklist_item);
            date = itemView.findViewById(R.id.tv_date_feedbacklist_item);
            clean_rating = itemView.findViewById(R.id.tv_rating_cleanliness_feedbacklist_item);
            safe_rating = itemView.findViewById(R.id.tv_rating_safety_feedbacklist_item);
            discipline_rating = itemView.findViewById(R.id.tv_rating_discipline_feedbacklist_item);
            time_rating = itemView.findViewById(R.id.tv_rating_time_strictness_feedbacklist_item);
            mess_rating = itemView.findViewById(R.id.tv_rating_mess_feedbacklist_item);
            overall_rating = itemView.findViewById(R.id.tv_overall_rating_feedbacklist_item);
            comment = itemView.findViewById(R.id.tv_comment_feedbacklist_item);

        }
    }
}
