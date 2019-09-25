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
import com.example.projetc2application.activities.PetsDetailsActivity;
import com.example.projetc2application.beans.NewsBean;
import com.example.projetc2application.beans.PetsBean;
import com.example.projetc2application.utils.GlobalVars;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PetsAdapter extends RecyclerView.Adapter {

    Activity activity;
    ArrayList<PetsBean> newsBeans;
    LayoutInflater inflater;

    public static final int VIEW_TYPE_ITEM = 0;
    public static final int VIEW_TYPE_LOADING = 1;

    public PetsAdapter(Activity activity, ArrayList<PetsBean> newsBeans) {
        this.activity = activity;
        this.newsBeans = newsBeans;
        inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = inflater.inflate(R.layout.item_pets, viewGroup, false);
            return new NewsViewHolder(itemView);
        } else {
            View itemView = inflater.inflate(R.layout.item_pets, viewGroup, false);
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

            com.squareup.picasso.Transformation transformation = new RoundedTransformationBuilder()
                    .scaleType(ImageView.ScaleType.CENTER_CROP)
                    .oval(false)
                    .build();

            try {
                Picasso.get()
                        .load(newsBeans.get(position).getImage())
                        .fit()
                        .placeholder(R.drawable.default_pic)
                        .centerCrop()
                        .transform(transformation)
                        .into(((NewsViewHolder) viewHolder).storyimage, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                ((NewsViewHolder) viewHolder1).storyimage.setImageDrawable(activity.getResources().getDrawable(R.drawable.default_pic));

                            }
                        });
            } catch (Exception e) {
                ((NewsViewHolder) viewHolder1).storyimage.setImageDrawable(activity.getResources().getDrawable(R.drawable.default_pic));
            }

            ((NewsViewHolder) viewHolder1).titlestory.setText("Category : "+newsBeans.get(position).getCategory_name()+"\nName : "+newsBeans.get(position).getName()+"\nColor : "+newsBeans.get(position).getColor());
            ((NewsViewHolder) viewHolder1).tvTime.setText(newsBeans.get(position).getDateOfBirth());

            ((NewsViewHolder) viewHolder).rlParent.setTag(position);
            ((NewsViewHolder) viewHolder).rlParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();
                    PetsBean newsBean = newsBeans.get(pos);

                    Intent intent = new Intent(activity, PetsDetailsActivity.class);
                    intent.putExtra(GlobalVars.PETS_BEAN_BUNDLE,newsBean);
                    activity.startActivity(intent);
                }
            });

            ((NewsViewHolder) viewHolder).rlShare.setTag(position);
            ((NewsViewHolder) viewHolder).rlShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return newsBeans.size();
    }

    private static class NewsViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlParent;
        RoundedImageView storyimage;
        TextView titlestory;
        TextView tvTime;
        RelativeLayout rlShare;

        NewsViewHolder(View convertView) {
            super(convertView);
            storyimage = (RoundedImageView) convertView.findViewById(R.id.ivnews);
            titlestory = (TextView) convertView.findViewById(R.id.tvtitle);
            rlParent = (RelativeLayout) convertView.findViewById(R.id.rlparent);
            tvTime = (TextView) convertView.findViewById(R.id.tvDate);
            rlShare =  convertView.findViewById(R.id.rlShare);
        }
    }

    public void add(PetsBean reviewBean) {
        newsBeans.add(reviewBean);
    }


    public void addAll(ArrayList<PetsBean> reviewBeans) {
        this.newsBeans.addAll(reviewBeans);
    }


    public void remove(int index) {
        newsBeans.remove(index);
    }

}
