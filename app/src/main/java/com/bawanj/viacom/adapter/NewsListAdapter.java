package com.bawanj.viacom.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bawanj.viacom.R;
import com.bawanj.viacom.model.NewsItem;
import com.bawanj.viacom.support.AppConstant;
import com.bawanj.viacom.utils.VolleyHelper;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsHolder> {

    private final String TAG = "NewsListAdapter";

    private final int VIEW_TYPE_TEXT_VIEW_LEFT = 0;
    private final int VIEW_TYPE_TEXT_VIEW_RIGHT= 1;

    private ImageLoader mImageLoader;
    private List<NewsItem> mNewsItems;

    public NewsListAdapter( List<NewsItem> newsItems){
    // constructor
        mNewsItems= newsItems;
        mImageLoader= VolleyHelper.getInstance().getImageLoader();
    }


    public final class NewsHolder extends RecyclerView.ViewHolder{
        // 1. reference 2. set input information

        private TextView mTextView;
        private NetworkImageView mImageView;

        public NewsHolder(View itemView, int viewType) {
            super(itemView);

            if(viewType== VIEW_TYPE_TEXT_VIEW_LEFT ){
                mTextView= (TextView) itemView.findViewById(R.id.item_title_left);
                mImageView= (NetworkImageView)itemView.findViewById(R.id.item_img_right);

            }else{ // VIEW_TYPE_TEXT_VIEW_RIGHT
                mTextView= (TextView) itemView.findViewById(R.id.item_title_right);
                mImageView= (NetworkImageView)itemView.findViewById(R.id.item_img_left);
            }
        }

        public void bindNewsItem(NewsItem newsItem ){
            mTextView.setText( newsItem.getItemTitle() );
            mImageView.setImageUrl( newsItem.getImageUrl(), mImageLoader);
        }

    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

    @Override
    public int getItemViewType(int position){

        if( AppConstant.VIEW_TYPE == 0 ){
            return VIEW_TYPE_TEXT_VIEW_LEFT;
        }
        return VIEW_TYPE_TEXT_VIEW_RIGHT;
    }


    @Override
    public NewsHolder onCreateViewHolder( ViewGroup viewGroup, int viewType ) {

        if(viewType == VIEW_TYPE_TEXT_VIEW_LEFT){
            View itemView = LayoutInflater.from( viewGroup.getContext() )
                    .inflate(R.layout.news_item_text_left, viewGroup, false);
            return new NewsHolder(itemView , viewType);

        }else{ //  VIEW_TYPE_TEXT_VIEW_RIGHT
            View itemView = LayoutInflater.from( viewGroup.getContext() )
                    .inflate(R.layout.news_item_text_right, viewGroup, false);
            return new NewsHolder(itemView , viewType);
        }
    }

    @Override
    public void onBindViewHolder( NewsHolder newsHolder, int position ) {

        NewsItem item= mNewsItems.get(position);
        newsHolder.bindNewsItem(item);

    }
}
