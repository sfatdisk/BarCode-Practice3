package com.bawanj.viacom.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class NewsListFragment extends Fragment {

    // constant variables
    private static final String TAG= "NewsListFragment";
    private static final String ARG_MODE= "current_mode";
    // switch Fragment
    private boolean mSwipeMode;
    // UI
    private RecyclerView mRecyclerView;
    private NewsListAdapter mNewsListAdapter;
    // fetch data
    private List<NewsItem> mNewsItems;
    private FetchAndParse mFetchAndParse;
    // update manually
    private SwipeRefreshLayout mSwipeRefreshLayout;

    // update periodically every 30 seconds
    private Handler mHandler;
    private final long DEFAULT_INTERVAL= 1; //1 second
    private final long PERIOD_INTERVAL = 30000;// 30 seconds

    public static NewsListFragment newInstance(boolean swipeMode ){
        Bundle args = new Bundle();
        args.putBoolean(ARG_MODE, swipeMode);

        NewsListFragment fragment= new NewsListFragment();
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
        mNewsListAdapter= new NewsListAdapter(mNewsItems);

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

            AppConstant.VIEW_TYPE= (AppConstant.VIEW_TYPE==0)? 1:0 ;
            updateData();
            mHandler.postDelayed(updateView, PERIOD_INTERVAL);
        }
    };

    private void updateData(){
        mFetchAndParse.fetchJsonArray( mNewsListAdapter );  // make the mNewsItems
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        if(!mSwipeMode){
            mHandler.removeCallbacks(updateView);
            Log.i("fetchJsonArray", " stop ");
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){

        // Define the xml for the fragment
        View rootView= inflater.inflate(R.layout.fragment_news_list, container, false);

        // Define recyclerView
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity());

        mRecyclerView= (RecyclerView)rootView.findViewById(R.id.fragment_news_recycler_view);
        mRecyclerView.setHasFixedSize(true); // improve the performance
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mNewsListAdapter);
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
                    AppConstant.VIEW_TYPE = (AppConstant.VIEW_TYPE == 0) ? 1 : 0;
                    updateData();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });

        }
        return rootView;
    }

}
