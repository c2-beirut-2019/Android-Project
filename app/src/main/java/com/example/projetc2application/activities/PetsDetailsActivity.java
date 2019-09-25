package com.example.projetc2application.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetc2application.R;
import com.example.projetc2application.beans.PetsBean;
import com.example.projetc2application.beans.ProductsBean;
import com.example.projetc2application.utils.GlobalVars;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

public class PetsDetailsActivity extends AppCompatActivity {

    Activity activity;
    TextView tvTitle,tvDate,tvDescription;
    ImageView ivNews;

    PetsBean newsBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        setupViews();
    }

    public void setupViews(){
        activity = this;
        newsBean =(PetsBean) getIntent().getSerializableExtra(GlobalVars.PETS_BEAN_BUNDLE);
        ivNews = findViewById(R.id.ivNews);
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvDescription = findViewById(R.id.tvDescription);

        tvTitle.setText(newsBean.getName());
        tvDescription.setText("Color:"+newsBean.getColor()+"\nCategory: "+newsBean.getCategory_name()+"\nDate of Birth: "+newsBean.getDateOfBirth()+"$");
        tvDate.setText("Registration Date: "+newsBean.getRegistrationDate());

        com.squareup.picasso.Transformation transformation = new RoundedTransformationBuilder()
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .oval(false)
                .build();

        try {
            Picasso.get()
                    .load(newsBean.getImage())
                    .fit()
                    .placeholder(R.drawable.default_pic)
                    .centerCrop()
                    .transform(transformation)
                    .into(ivNews, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            ivNews.setImageDrawable(activity.getResources().getDrawable(R.drawable.default_pic));

                        }
                    });
        } catch (Exception e) {
           ivNews.setImageDrawable(activity.getResources().getDrawable(R.drawable.default_pic));
        }
    }
}
