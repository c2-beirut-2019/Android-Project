package com.example.projetc2application.handlers;

import com.example.projetc2application.beans.ProductsBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductsHandler {

    public static ArrayList<ProductsBean> parseNews(String response){
        ArrayList<ProductsBean> newsBeans = new ArrayList<>();
        if (response != null) {
            try {
                JSONObject jsonObj = new JSONObject(response);
                if (jsonObj.has("data")) {
                    JSONArray jsonArray = jsonObj.getJSONArray("data");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                         String _id = "";
                         String title = "";
                         String description = "";
                         String price = "";
                         int quantityAvailable = 0;
                         String createDate = "";
                         ArrayList<String> colorsAvailable = new ArrayList<>();
                         ArrayList<String> images = new ArrayList<>();
                        ProductsBean newsBean = new ProductsBean();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if(jsonObject.has("_id")){
                            _id = jsonObject.getString("_id");
                        }
                        if(jsonObject.has("title")){
                            title = jsonObject.getString("title");
                        }
                        if(jsonObject.has("description")){
                            description = jsonObject.getString("description");
                        }
                        if(jsonObject.has("price")){
                            price = jsonObject.getString("price");
                        }
                        if(jsonObject.has("quantityAvailable")){
                            quantityAvailable = jsonObject.getInt("quantityAvailable");
                        }
                        if(jsonObject.has("createDate")){
                            createDate = jsonObject.getString("createDate");
                        }
                        if(jsonObject.has("colorsAvailable")){
                            JSONArray colorsAvailableArray = jsonObject.getJSONArray("colorsAvailable");
                            for (int j = 0; j <colorsAvailableArray.length() ; j++) {
                                colorsAvailable.add(colorsAvailableArray.getString(i));
                            }
                        }
                        if(jsonObject.has("images")){
                            JSONArray colorsAvailableArray = jsonObject.getJSONArray("images");
                            for (int j = 0; j <colorsAvailableArray.length() ; j++) {
                                images.add(colorsAvailableArray.getString(i));
                            }
                        }

                        newsBean.set_id(_id);
                        newsBean.setColorsAvailable(colorsAvailable);
                        newsBean.setCreateDate(createDate);
                        newsBean.setDescription(description);
                        newsBean.setPrice(price);
                        newsBean.setQuantityAvailable(quantityAvailable);
                        newsBean.setImages(images);
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
