package mx.saudade.popularmoviesapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.models.Video;

/**
 * Created by angelicamendezvega on 8/24/15.
 */
public class VideoAdapter extends AppAdapter<Video> {

    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_video, parent, false);

            holder = new ViewHolder(convertView, R.id.textView_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Video video = results.get(position);
        holder.name.setText(video.getName());

        return convertView;
    }

    static class ViewHolder {

        ViewHolder(View view, int id) {
            name = (TextView) view.findViewById(id);
        }

        TextView name;
    }
}
