package com.bawanj.viacom.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawanj.viacom.R;
import com.bawanj.viacom.activity.NewsActivity;
import com.bawanj.viacom.adapter.NewsRandomAdapter;
import com.bawanj.viacom.model.NewsItem;
import com.bawanj.viacom.utils.FetchAndParse;

import java.util.ArrayList;
import java.util.List;

public class NewsRandomFragment extends Fragment {

    // constant variables
    private static final String TAG= "NewsRandomFragment";
    private static final String ARG_MODE= "current_mode";
    // switch Fragment
    private boolean mSwipeMode;
    // UI
    private RecyclerView mRecyclerView;
    private NewsRandomAdapter mNewsRandomAdapter;

    // fetch data
    private List<NewsItem> mNewsItems;
    private FetchAndParse mFetchAndParse;
    // update manually
    private SwipeRefreshLayout mSwipeRefreshLayout;

    // update periodically every 30 seconds
    private Handler mHandler;
    private final long DEFAULT_INTERVAL= 1; //1 second
    private final long PERIOD_INTERVAL = 30000;// 30 seconds

    public static NewsRandomFragment newInstance(boolean swipeMode ){
        Bundle args = new Bundle();
        args.putBoolean(ARG_MODE, swipeMode);

        NewsRandomFragment fragment= new NewsRandomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mSwipeMode= getArguments().getBoolean(ARG_MODE);
        setActionBarTitle(mSwipeMode);
        // --- setup variables
        mNewsItems= new ArrayList<>();
        mFetchAndParse= new FetchAndParse( mNewsItems );
        mNewsRandomAdapter= new NewsRandomAdapter(mNewsItems);

        if(!mSwipeMode){ // primary mode

            // update data regularly : fetch data from server and store in the mNewsItems
            mHandler= new Handler();
            mHandler.postDelayed(updateView, DEFAULT_INTERVAL);

        }else{ // swipe mode
            updateData();
        }
    }

    private void setActionBarTitle(boolean swipeMode ){

        if(swipeMode){
            ( (NewsActivity)getActivity() )
                    .getSupportActionBar()
                    .setTitle(R.string.fragment_news_swipe);
        }else{
            ( (NewsActivity)getActivity() )
                    .getSupportActionBar()
                    .setTitle(R.string.fragment_news_primary);
        }
    }

    private Runnable updateView= new Runnable() {
        @Override
        public void run() { //  update layout and sync data

            updateData();
            mHandler.postDelayed(updateView, PERIOD_INTERVAL);
        }
    };

    private void updateData(){
        Log.d(TAG, "update every 30 seconds.");
        mFetchAndParse.fetchJsonArray( mNewsRandomAdapter );  // make the mNewsItems
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        if(!mSwipeMode){
            mHandler.removeCallbacks(updateView);
            //Log.i("fetchJsonArray", " stop ");
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){

        // Define the xml for the fragment
        View rootView= inflater.inflate(R.layout.fragment_news_list, container, false);

        // Define recyclerView
        StaggeredGridLayoutManager layoutManager=
                new StaggeredGridLayoutManager( 3, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView= (RecyclerView)rootView.findViewById(R.id.fragment_news_recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.addItemDecoration( new );
        mRecyclerView.setAdapter(mNewsRandomAdapter);

        mSwipeRefreshLayout= (SwipeRefreshLayout)rootView.findViewById(R.id.fragment_news_swipeRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);

        if( !mSwipeMode ){

            mSwipeRefreshLayout.setEnabled( false );

        }else{

            mSwipeRefreshLayout.setEnabled(true);
            // swipeToRefresh
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateData();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });

        }
        return rootView;
    }

}
