package com.example.android.harshitadhingranewsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<HDNews>> {
    private TextView mEmptyStateTextView;
    private static final int NEWS_ID = 1;
    private int ProgressBarStatus=0;
    public static final String LOG_TAG = MainActivity.class.getName();
    private HDNewsAdapter mAdapter;
    private static final String API =
            "https://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView newslist=(ListView)findViewById(R.id.list);
        mEmptyStateTextView =(TextView) findViewById(R.id.empty_view);
        newslist.setEmptyView(mEmptyStateTextView);
        mAdapter = new HDNewsAdapter(this, new ArrayList<HDNews>());
        newslist.setAdapter(mAdapter);
        newslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                HDNews currentNews = mAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getUrl());
               Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!=null&&activeNetwork.isConnected())
        {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_ID, null,this);}
        else {
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
        Log.i(LOG_TAG,"initLoader");
    }
    @Override
    public Loader<List<HDNews>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG,"onCreateLoader");
        HDNewsLoader e=new HDNewsLoader(this,API);
        return e;
    }
    @Override
    public void onLoadFinished(Loader<List<HDNews>> loader, List<HDNews> news) {
        ProgressBar p=(ProgressBar)findViewById(R.id.loading_spinner);
        p.setVisibility(View.GONE);
        Log.i(LOG_TAG,"onLoadFinished");
        mEmptyStateTextView.setText(R.string.no_news);
        mAdapter.clear();
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<HDNews>> loader) {
        Log.i(LOG_TAG,"onLoaderReset");
        mAdapter.clear();
    }
}
