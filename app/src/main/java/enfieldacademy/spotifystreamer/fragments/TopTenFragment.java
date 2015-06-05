package enfieldacademy.spotifystreamer.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enfieldacademy.spotifystreamer.R;
import enfieldacademy.spotifystreamer.SpotifyListHelper;
import enfieldacademy.spotifystreamer.adapters.SongsSearchResultAdapter;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

public class TopTenFragment extends Fragment {

    private SpotifyService mSpotify;
    private SongsSearchResultAdapter mSongsSearchResultAdapter;

    public TopTenFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpotifyApi api = new SpotifyApi();
        mSpotify = api.getService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_ten, container, false);
        Intent intent = getActivity().getIntent();
        Bundle extras = intent.getExtras();
        String key = getString(R.string.intent_extra_key);
        Integer position = extras.getInt(key);

        Artist artist = SpotifyListHelper.getArtistList().get(position);
        String spotifyId = artist.id;

        SearchTopTenTask searchTasksTask = new SearchTopTenTask(spotifyId);
        searchTasksTask.execute();

        ListView lv = (ListView) rootView.findViewById(R.id.trackListView);
        mSongsSearchResultAdapter = new SongsSearchResultAdapter(getActivity());
        lv.setAdapter(mSongsSearchResultAdapter);
        lv.setOnItemClickListener(mSongsSearchResultAdapter);

        return rootView;
    }

    public class SearchTopTenTask extends AsyncTask<Void, Void, Tracks> {

        private String mSearchStr;
        private Toast mToast;

        public SearchTopTenTask(String searchStr){
            this.mSearchStr = searchStr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Tracks doInBackground(Void... params) {
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("country", "US");
            try {
                return mSpotify.getArtistTopTrack(mSearchStr, queryMap);
            } catch (Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(Tracks tracks) {
            super.onPostExecute(tracks);

            // cancel current toast because a new action is being determined
            // it could succeed or it could fail
            if(mToast != null) mToast.cancel();

            List<Track> trackList;
            if(tracks == null){
                trackList = null;
                mToast = Toast.makeText(getActivity(), "An unexpected error occurred! :(", Toast.LENGTH_SHORT);
                mToast.show();
            } else {
                trackList = tracks.tracks;
                if(trackList.isEmpty()) {
                    mToast = Toast.makeText(getActivity(), "This band doesn't have any top tracks!", Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
            SpotifyListHelper.setTopTenList(trackList);
            mSongsSearchResultAdapter.notifyDataSetChanged();
        }
    }
}
