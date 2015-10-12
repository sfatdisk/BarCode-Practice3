package com.bawanj.viacom.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawanj.viacom.R;
import com.bawanj.viacom.activity.NewsActivity;
import com.bawanj.viacom.adapter.NewsListAdapter;
import com.bawanj.viacom.model.NewsItem;
import com.bawanj.viacom.support.AppConstant;
import com.bawanj.viacom.utils.FetchAndParse;

import java.util.ArrayList;
import java.util.List;

public class NewsPrimaryFragment extends Fragment{

    private static final String TAG= "NewsPrimaryFragment" ;
    // UI
    private RecyclerView mRecyclerView;
    private NewsListAdapter mNewsListAdapter;

    // fetch data
    private List<NewsItem> mNewsItems;
    private FetchAndParse mFetchAndParse;

    // update data every 30 seconds
    private Handler mHandler;
    private final long DEFAULT_INTERVAL= 1; //1 second
    private final long PERIOD_INTERVAL = 30000;// 30 seconds

    @Override
    public void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ( (NewsActivity)getActivity() )
                .getSupportActionBar()
                .setTitle(R.string.fragment_news_primary);
        // save variables
        setRetainInstance(true);
        // ----- keep data because of setRetainInstance(true) -----
        mNewsItems = new ArrayList<>();
        mFetchAndParse= new FetchAndParse(mNewsItems);
        //mNewsListAdapter= new NewsListAdapter( mNewsItems);
        mNewsListAdapter= new NewsListAdapter( mNewsItems);

        // update data regularly : fetch data from server and store in the mNewsItems
        mHandler= new Handler();
        mHandler.postDelayed(updateView, DEFAULT_INTERVAL);
    }

    private Runnable updateView= new Runnable() {
        @Override
        public void run() { //  update layout and sync data

            AppConstant.VIEW_TYPE= (AppConstant.VIEW_TYPE==0)? 1:0 ;
            updateData();
            mHandler.postDelayed(updateView, PERIOD_INTERVAL);
        }
    };

    private void updateData(){
        mFetchAndParse.fetchJsonArray( mNewsListAdapter);  // make the mNewsItems
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mHandler.removeCallbacks(updateView);
        Log.i("fetchJsonArray", " stop ");
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){

        // Define the xml for the fragment
        View rootView= inflater.inflate(R.layout.fragment_news_primary_list, container, false);

        // Define recyclerView
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity());

        mRecyclerView= (RecyclerView)rootView.findViewById(R.id.fragment_news_primary_recycler_view);
        mRecyclerView.setHasFixedSize(true); // improve the performance
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mNewsListAdapter);

        return rootView;
    }

}
