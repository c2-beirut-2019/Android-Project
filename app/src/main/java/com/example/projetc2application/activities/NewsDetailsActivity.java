package com.example.projetc2application.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetc2application.R;
import com.example.projetc2application.beans.NewsBean;
import com.example.projetc2application.utils.GlobalVars;

public class NewsDetailsActivity extends AppCompatActivity {

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
        newsBean =(NewsBean) getIntent().getSerializableExtra(GlobalVars.NEWS_BEAN_BUNDLE);
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvDescription = findViewById(R.id.tvDescription);

        tvTitle.setText(newsBean.getTitle());
        tvDescription.setText(newsBean.getContent());
        tvDate.setText(newsBean.getCreationDate());
    }
}
