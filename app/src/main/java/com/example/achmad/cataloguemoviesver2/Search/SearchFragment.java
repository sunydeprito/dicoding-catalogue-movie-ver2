package com.example.achmad.cataloguemoviesver2.Search;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.achmad.cataloguemoviesver2.Adapter.MovieAdapter;
import com.example.achmad.cataloguemoviesver2.Adapter.MovieItems;
import com.example.achmad.cataloguemoviesver2.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    @BindView(R.id.lv_movie)
    ListView lvMovie;
    @BindView(R.id.edt_movie)
    EditText edtTitle;
    @Nullable
    @BindView(R.id.image_movie)
    ImageView imgMovie;
    @BindView(R.id.btn_find)
    FancyButton btnFindMovie;
    MovieAdapter adapter;
    private View view;

    static final String EXTRAS_MOVIE = "extras_movie";


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        adapter = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();

        lvMovie.setAdapter(adapter);
        lvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                MovieItems item = (MovieItems) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), ActivityDetailMovie.class);


                intent.putExtra(ActivityDetailMovie.EXTRA_TITLE, item.getMovTitle());
                intent.putExtra(ActivityDetailMovie.EXTRA_OVERVIEW, item.getMovOverview());
                intent.putExtra(ActivityDetailMovie.EXTRA_DATE_RELEASE, item.getMovDateRelease());
                intent.putExtra(ActivityDetailMovie.EXTRA_IMAGE_MOVIE, item.getMovImage());
                intent.putExtra(ActivityDetailMovie.EXTRA_RATING, item.getMovRating());
                intent.putExtra(ActivityDetailMovie.EXTRA_VOTE, item.getMovVote());

                startActivity(intent);
            }
        });


        btnFindMovie.setOnClickListener(movieListener);

        String judul_movie = edtTitle.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, judul_movie);

        getLoaderManager().initLoader(0, bundle, SearchFragment.this);

        return view;
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int i, Bundle bundle) {
        String movieTitle = "";
        if (bundle != null) {
            movieTitle = bundle.getString(EXTRAS_MOVIE);
        }
        return new MovieAsyncTaskLoader(getActivity(), movieTitle);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> movieItems) {
        adapter.setData(movieItems);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener movieListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String movie_title = edtTitle.getText().toString();
            if (TextUtils.isEmpty(movie_title)) {
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, movie_title);
            getLoaderManager().restartLoader(0, bundle, SearchFragment.this);
        }
    };

}


