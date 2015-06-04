package enfieldacademy.spotifystreamer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import enfieldacademy.spotifystreamer.R;
import enfieldacademy.spotifystreamer.activities.MainActivity;
import enfieldacademy.spotifystreamer.activities.TopTenActivity;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

public class ArtistsSearchResultAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    private LayoutInflater mInflater;
    private Context context;

    public ArtistsSearchResultAdapter(Context context){
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        if(MainActivity.artistList != null) {
            return MainActivity.artistList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_search_music, parent, false);
        }

        Artist artist = MainActivity.artistList.get(position);
        String bandName = artist.name;
        List<Image> bandImageList = artist.images;

        TextView bandNameTextBox = (TextView) convertView.findViewById(R.id.band_name);
        ImageView bandImageView = (ImageView) convertView.findViewById(R.id.band_image);

        bandNameTextBox.setText(bandName);
        if(!bandImageList.isEmpty()) {
            String bandImageUrl = bandImageList.get(0).url;
            Picasso.with(context)
                    .load(bandImageUrl)
                    .resize(75,75)
                    .centerCrop()
                    .into(bandImageView);
        } else {
            bandImageView.setImageResource(R.drawable.white_square);
        }
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String key = context.getString(R.string.intent_extra_key);
        Intent topTenIntent = new Intent(context, TopTenActivity.class);
        topTenIntent.putExtra(key, position);
        context.startActivity(topTenIntent);
    }
}