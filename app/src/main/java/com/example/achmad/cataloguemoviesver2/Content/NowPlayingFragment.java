package com.example.achmad.cataloguemoviesver2.Content;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.achmad.cataloguemoviesver2.Adapter.ContentAdapter;
import com.example.achmad.cataloguemoviesver2.Adapter.MovieItems;
import com.example.achmad.cataloguemoviesver2.BuildConfig;
import com.example.achmad.cataloguemoviesver2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment {


    private RecyclerView rvCategory;
    private RecyclerView.Adapter adapter;
    private View view;
    private List<MovieItems> movieLists;

    private static final String API_URL = BuildConfig.MOVIE_URL + "/now_playing?api_key=" + BuildConfig.MOVIE_API_KEY + "&language=en-US";

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        rvCategory = (RecyclerView) view.findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieLists = new ArrayList<MovieItems>();
        loadUrlData();
        return view;
    }

    private void loadUrlData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {

                        MovieItems movies = new MovieItems();

                        JSONObject data = array.getJSONObject(i);
                        movies.setMovTitle(data.getString("title"));
                        movies.setMovOverview(data.getString("overview"));
                        movies.setMovDateRelease(data.getString("release_date"));
                        movies.setMovImage(data.getString("poster_path"));
                        movies.setMovVote(data.getString("vote_count"));
                        movies.setMovRating(data.getString("vote_average"));
                        movieLists.add(movies);

                    }

                    adapter = new ContentAdapter(movieLists, getActivity());
                    rvCategory.setAdapter(adapter);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                loadUrlData();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
