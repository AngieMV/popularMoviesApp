package mx.saudade.popularmoviesapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.models.Review;

/**
 * Created by angelicamendezvega on 8/24/15.
 */
public class ReviewAdapter extends AppAdapter<Review> {

    public ReviewAdapter(Context context, int identifier) {
        super(context, identifier);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_review, parent, false);

            holder = new ViewHolder(convertView, R.id.textView_review, R.id.textView_author);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Review review = results.get(position);
        holder.content.setText(review.getContent());
        holder.author.setText(review.getAuthor());

        return convertView;
    }

    static class ViewHolder {

        ViewHolder(View view, int contentId, int authorId) {
            content = (TextView) view.findViewById(contentId);
            author = (TextView) view.findViewById(authorId);
        }

        TextView content;
        TextView author;
    }
}
