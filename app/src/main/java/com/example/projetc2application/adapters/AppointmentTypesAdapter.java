package com.example.projetc2application.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.projetc2application.R;
import com.example.projetc2application.activities.NewsDetailsActivity;
import com.example.projetc2application.beans.AppointmentBean;
import com.example.projetc2application.beans.NewsBean;
import com.example.projetc2application.utils.GlobalVars;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AppointmentTypesAdapter extends RecyclerView.Adapter {

    Activity activity;
    ArrayList<AppointmentBean> newsBeans;
    LayoutInflater inflater;

    public static final int VIEW_TYPE_ITEM = 0;
    public static final int VIEW_TYPE_LOADING = 1;

    public AppointmentTypesAdapter(Activity activity, ArrayList<AppointmentBean> newsBeans) {
        this.activity = activity;
        this.newsBeans = newsBeans;
        inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = inflater.inflate(R.layout.item_appointment_type, viewGroup, false);
            return new NewsViewHolder(itemView);
        } else {
            View itemView = inflater.inflate(R.layout.item_appointment_type, viewGroup, false);
            return new NewsViewHolder(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {

        return newsBeans.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final RecyclerView.ViewHolder viewHolder1 = viewHolder;
        if (viewHolder instanceof NewsViewHolder) {

            ((NewsViewHolder) viewHolder1).tvTitle.setText(newsBeans.get(position).getName());
            if(newsBeans.get(position).isSelected())
            ((NewsViewHolder) viewHolder1).ivIsSelected.setVisibility(View.VISIBLE);
            else
                ((NewsViewHolder) viewHolder1).ivIsSelected.setVisibility(View.GONE);

            ((NewsViewHolder) viewHolder).tvTitle.setTag(position);
            ((NewsViewHolder) viewHolder).tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();
                    AppointmentBean appointmentBean = newsBeans.get(pos);
                    Intent intent = new Intent();
                    intent.putExtra(GlobalVars.CODE_BUNDLE,appointmentBean);
                    activity.setResult(Activity.RESULT_OK,intent);
                    activity.finish();

                }
            });



        }

    }

    @Override
    public int getItemCount() {
        return newsBeans.size();
    }

    private static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivIsSelected;
        RelativeLayout rlParent;

        NewsViewHolder(View convertView) {
            super(convertView);
            ivIsSelected =  convertView.findViewById(R.id.ivIsSelected);
            tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            rlParent = convertView.findViewById(R.id.rlParent);
        }
    }

    public void add(AppointmentBean reviewBean) {
        newsBeans.add(reviewBean);
    }


    public void addAll(ArrayList<AppointmentBean> reviewBeans) {
        this.newsBeans.addAll(reviewBeans);
    }


    public void remove(int index) {
        newsBeans.remove(index);
    }

}
