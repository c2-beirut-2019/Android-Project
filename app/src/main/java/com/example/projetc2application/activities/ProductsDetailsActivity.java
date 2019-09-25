package com.example.projetc2application.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetc2application.R;
import com.example.projetc2application.beans.NewsBean;
import com.example.projetc2application.beans.ProductsBean;
import com.example.projetc2application.utils.GlobalVars;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

public class ProductsDetailsActivity extends AppCompatActivity {

    Activity activity;
    TextView tvTitle,tvDate,tvDescription;
    ImageView ivNews;

    ProductsBean newsBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        setupViews();
    }

    public void setupViews(){
        activity = this;
        newsBean =(ProductsBean) getIntent().getSerializableExtra(GlobalVars.PRODUCTS_BEAN_BUNDLE);
        ivNews = findViewById(R.id.ivNews);
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvDescription = findViewById(R.id.tvDescription);

        tvTitle.setText(newsBean.getTitle());
        tvDescription.setText("Description:"+newsBean.getDescription()+"\nQuantity Availbale: "+newsBean.getQuantityAvailable()+"\nPrice: "+newsBean.getPrice()+"$");
        tvDate.setText(newsBean.getCreateDate());

        com.squareup.picasso.Transformation transformation = new RoundedTransformationBuilder()
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .oval(false)
                .build();

        try {
            Picasso.get()
                    .load(newsBean.getImages().get(0))
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
