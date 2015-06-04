package enfieldacademy.spotifystreamer.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enfieldacademy.spotifystreamer.R;
import enfieldacademy.spotifystreamer.activities.MainActivity;
import enfieldacademy.spotifystreamer.adapters.ArtistsSearchResultAdapter;
import enfieldacademy.spotifystreamer.adapters.SongsSearchResultAdapter;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.http.QueryMap;

public class TopTenFragment extends Fragment {

    private SongsSearchResultAdapter songsSearchResultAdapter;

    public TopTenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_ten, container, false);
        Intent intent = getActivity().getIntent();
        Bundle extras = intent.getExtras();
        String key = getString(R.string.intent_extra_key);
        Integer position = extras.getInt(key);

        Artist artist = MainActivity.artistList.get(position);
        String spotifyId = artist.id;

        SearchTopTenTask searchTasksTask = new SearchTopTenTask(spotifyId);
        searchTasksTask.execute();

        ListView lv = (ListView) rootView.findViewById(R.id.trackListView);
        songsSearchResultAdapter = new SongsSearchResultAdapter(getActivity());
        lv.setAdapter(songsSearchResultAdapter);
        lv.setOnItemClickListener(songsSearchResultAdapter);

        return rootView;
    }

    public class SearchTopTenTask extends AsyncTask<Void, Void, Void> {

        private String searchStr;

        public SearchTopTenTask(String searchStr){
            this.searchStr = searchStr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("country", "US");
            Tracks tracks = MainActivity.spotify.getArtistTopTrack(searchStr, queryMap);
            MainActivity.topTenList = tracks.tracks;
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            songsSearchResultAdapter.notifyDataSetChanged();
        }
    }
}
