package com.example.projetc2application.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetc2application.R;
import com.example.projetc2application.adapters.NewsAdapter;
import com.example.projetc2application.beans.NewsBean;
import com.example.projetc2application.utils.GlobalVars;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    Activity activity;
    TextView tvTitle,tvDate,tvDescription;
    ImageView ivNews;

    NewsBean newsBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        setupViews();
    }

    public void setupViews(){
        activity = this;
        newsBean =(NewsBean) getIntent().getSerializableExtra(GlobalVars.NEWS_BEAN_BUNDLE);
        ivNews = findViewById(R.id.ivNews);
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvDescription = findViewById(R.id.tvDescription);

        tvTitle.setText(newsBean.getTitle());
        tvDescription.setText(newsBean.getContent());
        tvDate.setText(newsBean.getCreationDate());

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
