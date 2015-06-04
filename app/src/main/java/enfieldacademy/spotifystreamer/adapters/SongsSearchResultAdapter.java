package enfieldacademy.spotifystreamer.adapters;

import android.content.Context;
import android.util.Log;
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
import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

public class SongsSearchResultAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    private final String TAG = "SongsSearchResultAdptr";

    private LayoutInflater mInflater;
    private Context context;

    public SongsSearchResultAdapter(Context context){
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        if(MainActivity.topTenList != null) {
            return MainActivity.topTenList.size();
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
            convertView = mInflater.inflate(R.layout.item_bands_top_ten, parent, false);
        }

        Track track = MainActivity.topTenList.get(position);

        AlbumSimple album = track.album;
        List<Image> imageList = album.images;
        Image image = imageList.get(0);

        List<ArtistSimple> artistSimpleList = track.artists;
        ArtistSimple artist = artistSimpleList.get(0);
        //artist.

        // TODO: come back to this!

        String songName = track.name;
        String imageUrl = image.url;
        String bandAlbum = album.name;

        /*TextView bandNameTextBox = (TextView) convertView.findViewById(R.id.band_name);
        ImageView bandImageView = (ImageView) convertView.findViewById(R.id.band_image);

        bandNameTextBox.setText(bandName);
        Picasso.with(context)
                .load(bandImageUrl)
                .resize(75,75)
                .centerCrop()
                .into(bandImageView);*/
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "position: " + position);
    }
}