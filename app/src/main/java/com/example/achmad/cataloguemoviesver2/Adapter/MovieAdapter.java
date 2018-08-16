package com.example.achmad.cataloguemoviesver2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.achmad.cataloguemoviesver2.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends BaseAdapter {

    private ArrayList<MovieItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;


    public MovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItems> items) {
        mData = items;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        //Check null if no value, return 0 no view
        if (mData == null) return 0;
        return mData.size();
    }

    @Override
    public MovieItems getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //holder is used to hold value
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.movie_row_list, null);
            holder = new ViewHolder(convertView);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(mData.get(position).getMovTitle());
        holder.tvVote.setText("Popularity: " + mData.get(position).getMovVote());

        String collectDate = mData.get(position).getMovDateRelease();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(collectDate);

            SimpleDateFormat new_dateFormat = new SimpleDateFormat("yyyy");
            String date_of_release = new_dateFormat.format(date);
            holder.tvReleaseDate.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvRating.setText("Rating: " + mData.get(position).getMovRating());
        Picasso.get().load("http://image.tmdb.org/t/p/w185/" + mData.get(position).getMovImage()).
                placeholder(context.getResources().getDrawable(R.drawable.ic_image_black)).
                error(context.getResources().getDrawable(R.drawable.ic_image_black)).into(holder.imgMovie);
        return convertView;

    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_dateRelease)
        TextView tvReleaseDate;
        @BindView(R.id.image)
        ImageView imgMovie;
        @BindView(R.id.tv_rating)
        TextView tvRating;
        @BindView(R.id.tv_vote)
        TextView tvVote;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}


