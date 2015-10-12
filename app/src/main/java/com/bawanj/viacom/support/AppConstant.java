package com.bawanj.viacom.support;


public class AppConstant {

    //RecyclerView Type
    public static int VIEW_TYPE = 0 ; // 0: textLeft, 1: textRight

    // Dribbble API
    private static final String BASE_URI         = "https://api.dribbble.com/v1/shots?";
    private static final String ACCESS_TOKEN     = "access_token=d40cff3dab6677286b8fb77fba2232eab7e60e76c591eb78043e1c65be564c8e";
    public static final String  RECENT_DATA_URL   =BASE_URI+"sort=recent&"+ACCESS_TOKEN;

}
