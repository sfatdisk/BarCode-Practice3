package com.bawanj.viacom.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bawanj.viacom.R;
import com.bawanj.viacom.model.NewsItem;
import com.bawanj.viacom.utils.VolleyHelper;

import java.util.ArrayList;
import java.util.List;

public class NewsRandomAdapter extends RecyclerView.Adapter<NewsRandomAdapter.NewsRandomHolder> {


    private final String TAG = "NewsRansomAdapter";

    private ImageLoader mImageLoader;
    private List<NewsItem> mNewsItems;
    private List<Integer> mHeights;


    public NewsRandomAdapter(List<NewsItem> newsItems){
    // constructor
        mNewsItems= newsItems;
        mImageLoader= VolleyHelper.getInstance().getImageLoader();
    }

    public void getRandomHeight(){
        mHeights = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            mHeights.add((int)(250 + Math.random()*500));
        }
    }

    public final class NewsRandomHolder extends RecyclerView.ViewHolder{

        private CardView mCardView;
        private TextView mTextView;
        private NetworkImageView mImageView;

        public NewsRandomHolder(View itemView) {
            super(itemView);

            mCardView= (CardView) itemView.findViewById(R.id.cardView);
            mTextView= (TextView) itemView.findViewById(R.id.item_title);
            mImageView= (NetworkImageView)itemView.findViewById(R.id.item_image);

        }

        public void bindNewsItem(NewsItem newsItem ){
            mTextView.setText( newsItem.getItemTitle() );
            mImageView.setImageUrl( newsItem.getImageUrl(), mImageLoader);
        }
    }

    @Override
    public NewsRandomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");
        View itemView= LayoutInflater.from( parent.getContext() )
                .inflate(R.layout.news_item_random, parent, false);

        getRandomHeight();
        return new NewsRandomHolder(itemView);
    }

    @Override
    public void onBindViewHolder( NewsRandomHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder");
        // setup layout
        ViewGroup.LayoutParams params= holder.itemView.getLayoutParams();
        params.height=  mHeights.get(position);

        holder.mCardView.setLayoutParams(params);
        // get  data
        NewsItem item= mNewsItems.get(position);
        holder.bindNewsItem(item);
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size() ;
    }

}
