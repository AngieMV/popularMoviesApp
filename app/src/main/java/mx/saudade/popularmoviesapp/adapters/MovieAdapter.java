package mx.saudade.popularmoviesapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.models.Movie;

/**
 * Created by angelicamendezvega on 8/5/15.
 */
public class MovieAdapter extends AppAdapter<Movie> {

    public MovieAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_grid, parent, false);

            holder = new ViewHolder(convertView, R.id.layout_item, R.id.imageView_movie, R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie item = results.get(position);
        holder.text.setText(((Movie) item).getTitle());

        Picasso.with(context).load(item.getThumbPosterPath())
                .placeholder(R.drawable.placeholder_movie_icon).into(holder.image);

        return convertView;
    }

    static class ViewHolder {

        ViewHolder(View view, int layoutId, int imageId, int textId) {
            layout = (RelativeLayout) view.findViewById(layoutId);
            image = (ImageView) view.findViewById(imageId);
            text = (TextView) view.findViewById(textId);
        }

        RelativeLayout layout;
        ImageView image;
        TextView text;
    }
}


