package mx.saudade.popularmoviesapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.models.Movie;
import mx.saudade.popularmoviesapp.models.Results;

/**
 * Created by angelicamendezvega on 8/5/15.
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private List<Movie> movies;

    public GridAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_grid, parent, false);

            holder = new ViewHolder();
            holder.layout = (RelativeLayout) convertView.findViewById(R.id.layout_item);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView_movie);
            holder.text = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie item = movies.get(position);

        holder.text.setText(item.getOriginalTitle());

        Picasso.with(context).load(item.getThumbPosterPath())
                .placeholder(R.drawable.placeholder_movie_icon).into(holder.image);

        return convertView;
    }

    public Results getMovies() {
        return new Results(movies);
    }

    static class ViewHolder {
        RelativeLayout layout;
        ImageView image;
        TextView text;

    }

}


