package com.example.achmad.cataloguemoviesver2.Search;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.achmad.cataloguemoviesver2.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityDetailMovie extends AppCompatActivity {

    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_OVERVIEW = "extra_overview";
    public static String EXTRA_DATE_RELEASE = "extra_date_release";
    public static String EXTRA_IMAGE_MOVIE = "extra_image_movie";
    public static String EXTRA_RATING = "extra_rating";
    public static String EXTRA_VOTE = "extra_vote";


    @BindView(R.id.tv_detail_title)
    TextView tvTitle;
    @BindView(R.id.tv_detail_overview)
    TextView tvOverview;
    @BindView(R.id.tv_detail_date)
    TextView tvDateRelease;
    @BindView(R.id.tv_detail_rating)
    TextView tvRating;
    @BindView(R.id.image_movie)
    ImageView imgMovie;
    @BindView(R.id.tv_detail_vote)
    TextView tvVote;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        //get intent
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String rating = getIntent().getStringExtra(EXTRA_RATING);
        String vote = getIntent().getStringExtra(EXTRA_VOTE);
        String image = getIntent().getStringExtra(EXTRA_IMAGE_MOVIE);
        String dateRelease = getIntent().getStringExtra(EXTRA_DATE_RELEASE);


        //Format Date Release
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(dateRelease);

            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            tvDateRelease.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //pass value using set function
        tvTitle.setText(title);
        tvOverview.setText(overview);
        tvRating.setText(rating + " Ratings (" + rating + "/10)");
        tvVote.setText(vote);
        Picasso.get().load("http://image.tmdb.org/t/p/original/" + image).into(imgMovie);
    }
}
