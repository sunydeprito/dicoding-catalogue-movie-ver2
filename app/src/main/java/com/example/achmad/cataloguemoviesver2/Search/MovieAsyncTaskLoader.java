package com.example.achmad.cataloguemoviesver2.Search;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.example.achmad.cataloguemoviesver2.Adapter.MovieItems;
import com.example.achmad.cataloguemoviesver2.BuildConfig;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItems>> {

    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;

    private String mTitle;

    public MovieAsyncTaskLoader(final Context context, String MovieTitle) {
        super(context);

        onContentChanged();
        this.mTitle = MovieTitle;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    //show result data
    public void deliverResult(ArrayList<MovieItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }


    private void onReleaseResources(ArrayList<MovieItems> data) {
    }

    @Override
    //get data synchronously
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItems> movieItemses = new ArrayList<>();

        //url format to get JSON value based on title
        String url = BuildConfig.MOVIE_URL_SEARCH + "?api_key=" +
                BuildConfig.MOVIE_API_KEY + "&language=en-US&query=" + mTitle;

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                //load in background
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movie);
                        movieItemses.add(movieItems);
                    }
                } catch (Exception e) {
                    //to avoid error parsing value
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //no method if fail
            }
        });
        return movieItemses;
    }
}
