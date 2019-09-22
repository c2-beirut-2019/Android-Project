package com.example.projetc2application.handlers;

import com.example.projetc2application.beans.NewsBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsHandler {

    public static ArrayList<NewsBean> parseNews(String response){
        ArrayList<NewsBean> newsBeans = new ArrayList<>();
        if (response != null) {
            try {
                JSONObject jsonObj = new JSONObject(response);
                if (jsonObj.has("data")) {
                    JSONArray jsonArray = jsonObj.getJSONArray("data");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                         String _id="";
                         String title="";
                         String content="";
                         String image="";
                         String creationDate="";
                         NewsBean newsBean = new NewsBean();
                         JSONObject jsonObject = jsonArray.getJSONObject(i);

                         if(jsonObject.has("_id")){
                             _id = jsonObject.getString("_id");
                         }
                        if(jsonObject.has("title")){
                            title = jsonObject.getString("title");
                        }
                        if(jsonObject.has("content")){
                            content = jsonObject.getString("content");
                        }
                        if(jsonObject.has("image")){
                            image = jsonObject.getString("image");
                        }
                        if(jsonObject.has("creationDate")){
                            creationDate = jsonObject.getString("creationDate");
                        }

                        newsBean.set_id(_id);
                        newsBean.setContent(content);
                        newsBean.setCreationDate(creationDate);
                        newsBean.setImage(image);
                        newsBean.setTitle(title);
                        newsBeans.add(newsBean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newsBeans;
    }
}
