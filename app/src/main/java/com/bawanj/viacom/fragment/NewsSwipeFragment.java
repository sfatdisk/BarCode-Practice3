package com.bawanj.viacom.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class NewsSwipeFragment extends Fragment {

    private static final String TAG= "NewsPrimaryFragment" ;
    // UI
    private RecyclerView mRecyclerView;
    private NewsListAdapter mNewsListAdapter;
    // fetch data
    private List<NewsItem> mNewsItems;
    private FetchAndParse mFetchAndParse;

    // update manually
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ( (NewsActivity)getActivity() )
                .getSupportActionBar()
                .setTitle(R.string.fragment_news_swipe);
        // save variables
        setRetainInstance(true);
        // ----- keep data because of setRetainInstance(true) -----
        mNewsItems = new ArrayList<>();
        mFetchAndParse= new FetchAndParse(mNewsItems);
        mNewsListAdapter= new NewsListAdapter( mNewsItems);

        // fetch data from server and store in the mNewsItems
        updateData();
    }

    private void updateData(){
        mFetchAndParse.fetchJsonArray( mNewsListAdapter );  // make the mNewsItems
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){

        // Define the xml for the fragment
        View rootView= inflater.inflate(R.layout.fragment_news_swipe_list, container, false);

        // Define swipeRefreshLayout and recyclerView
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity());
        mRecyclerView= (RecyclerView)rootView.findViewById(R.id.fragment_news_swipe_recycler_view);
        mRecyclerView.setHasFixedSize(true); // improve the performance
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mNewsListAdapter);

        // swipeToRefresh
        mSwipeRefreshLayout= (SwipeRefreshLayout)rootView.findViewById(R.id.fragment_news_swipe_swipeRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AppConstant.VIEW_TYPE= (AppConstant.VIEW_TYPE==0)? 1:0 ;
                updateData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return rootView;
    }
}
