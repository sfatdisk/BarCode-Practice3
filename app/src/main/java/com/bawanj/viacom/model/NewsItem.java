package com.bawanj.viacom.model;


public class NewsItem {

    private String mImageUrl;
    private String mItemTitle;

    public NewsItem(  String title , String imageUrl ){
        mImageUrl =imageUrl;
        mItemTitle=title;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String image) {
        mImageUrl = image;
    }

    public String getItemTitle() {
        return mItemTitle;
    }

    public void setItemTitle(String itemTitle) {
        mItemTitle = itemTitle;
    }
}
