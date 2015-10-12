package com.bawanj.viacom.utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bawanj.viacom.model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FetchAndParse {

    private static final String TAG= "FetchAndParse";

    private List<NewsItem> mNewsItems;

    public FetchAndParse(List<NewsItem> newsItems){
        mNewsItems= newsItems;
    }

    public void fetchJsonArray( final RecyclerView.Adapter adapter ){ // a thread

        //Log.i("fetchJsonArray", "ViewType is: " + AppConstant.VIEW_TYPE);
        //Log.i("fetchJsonArray", "time is 30 seconds? Yes ");

        final String url= "https://api.dribbble.com/v1/shots?sort=recent&access_token=";

        JsonArrayRequest request= new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try{
                            //Log.d( TAG, "JSONArray "+response );
                            parseJSONArray(response, adapter );

                        }catch(Exception e){
                            Log.d(TAG, "Unable to fetch JsonObject: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d (TAG, error.getMessage(),error);
                        error.printStackTrace();
                    }
                }
        );
        VolleyHelper.getInstance().getRequestQueue().add(request);

    }

    // TODO - get JSON response and parseJSONArray it
    private void parseJSONArray(JSONArray jsonArray  , RecyclerView.Adapter adapter)throws JSONException {

        mNewsItems.clear(); //  clear the list first

        for(int i=0;i<jsonArray.length();++i){

            JSONObject news_item= jsonArray.getJSONObject(i);

            String title= news_item.getString("title");

            JSONObject images = news_item.getJSONObject("images");

            String image= images.getString("teaser");

            // store each server data in module class
            NewsItem item= new NewsItem( title, image );
            // add the nodule class to the ArrayList which constructs the adapter
            mNewsItems.add(item);
        }
        // notify the data updated
        adapter.notifyDataSetChanged();
    }
}
