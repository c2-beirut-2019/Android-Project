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
import com.example.projetc2application.activities.ProductsDetailsActivity;
import com.example.projetc2application.beans.ProductsBean;
import com.example.projetc2application.utils.GlobalFunctions;
import com.example.projetc2application.utils.GlobalVars;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

public class ProductsAdapter extends RecyclerView.Adapter {

    Activity activity;
    ArrayList<ProductsBean> newsBeans;
    LayoutInflater inflater;

    public static final int VIEW_TYPE_ITEM = 0;
    public static final int VIEW_TYPE_LOADING = 1;

    public ProductsAdapter(Activity activity, ArrayList<ProductsBean> newsBeans) {
        this.activity = activity;
        this.newsBeans = newsBeans;
        inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = inflater.inflate(R.layout.item_products, viewGroup, false);
            return new ProductsViewHolder(itemView);
        } else {
            View itemView = inflater.inflate(R.layout.item_products, viewGroup, false);
            return new ProductsViewHolder(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {

        return newsBeans.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final RecyclerView.ViewHolder viewHolder1 = viewHolder;
        if (viewHolder instanceof ProductsViewHolder) {

            com.squareup.picasso.Transformation transformation = new RoundedTransformationBuilder()
                    .scaleType(ImageView.ScaleType.CENTER_CROP)
                    .oval(false)
                    .build();

            try {
                Picasso.get()
                        .load(newsBeans.get(position).getImages().get(0))
                        .fit()
                        .placeholder(R.drawable.default_pic)
                        .centerCrop()
                        .transform(transformation)
                        .into(((ProductsViewHolder) viewHolder).storyimage, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                ((ProductsViewHolder) viewHolder1).storyimage.setImageDrawable(activity.getResources().getDrawable(R.drawable.default_pic));

                            }
                        });
            } catch (Exception e) {
                ((ProductsViewHolder) viewHolder1).storyimage.setImageDrawable(activity.getResources().getDrawable(R.drawable.default_pic));
            }

            ((ProductsViewHolder) viewHolder1).titlestory.setText(newsBeans.get(position).getTitle());
            ((ProductsViewHolder) viewHolder1).tvTime.setText(GlobalFunctions.convertDateToTimeZone(newsBeans.get(position).getCreateDate(), GlobalVars.inputDateFormat,
                    GlobalVars.outputDateFormatNotifications
                    , Locale.ENGLISH, TimeZone.getTimeZone("UTC"), TimeZone.getDefault()));

            ((ProductsViewHolder) viewHolder).rlParent.setTag(position);
            ((ProductsViewHolder) viewHolder).rlParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();
                    ProductsBean newsBean = newsBeans.get(pos);

                    Intent intent = new Intent(activity, ProductsDetailsActivity.class);
                    intent.putExtra(GlobalVars.PRODUCTS_BEAN_BUNDLE,newsBean);
                    activity.startActivity(intent);
                }
            });

            ((ProductsViewHolder) viewHolder).rlShare.setTag(position);
            ((ProductsViewHolder) viewHolder).rlShare.setOnClickListener(new View.OnClickListener() {
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

    private static class ProductsViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlParent;
        RoundedImageView storyimage;
        TextView titlestory;
        TextView tvTime;
        RelativeLayout rlShare;

        ProductsViewHolder(View convertView) {
            super(convertView);
            storyimage = (RoundedImageView) convertView.findViewById(R.id.ivnews);
            titlestory = (TextView) convertView.findViewById(R.id.tvtitle);
            rlParent = (RelativeLayout) convertView.findViewById(R.id.rlparent);
            tvTime = (TextView) convertView.findViewById(R.id.tvDate);
            rlShare =  convertView.findViewById(R.id.rlShare);
        }
    }

    public void add(ProductsBean reviewBean) {
        newsBeans.add(reviewBean);
    }


    public void addAll(ArrayList<ProductsBean> reviewBeans) {
        this.newsBeans.addAll(reviewBeans);
    }


    public void remove(int index) {
        newsBeans.remove(index);
    }

}
