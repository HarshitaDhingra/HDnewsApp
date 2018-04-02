package com.example.android.harshitadhingranewsapp;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import java.util.List;
public class HDNewsLoader extends AsyncTaskLoader<List<HDNews>> {
    private static final String LOG_TAG = HDNewsLoader.class.getName();
    private String mUrl;
    public HDNewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"onStart");
        forceLoad();
    }
    @Override
    public List<HDNews> loadInBackground() {
        Log.i(LOG_TAG,"loadInBackground");
        if (mUrl == null) {
            return null;
        }
        List<HDNews> news = Utilities.fetchNewsData(mUrl);
        return news;
    }
}
