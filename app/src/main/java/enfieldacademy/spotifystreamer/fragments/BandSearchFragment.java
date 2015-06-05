package enfieldacademy.spotifystreamer.fragments;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import enfieldacademy.spotifystreamer.R;
import enfieldacademy.spotifystreamer.SpotifyListHelper;
import enfieldacademy.spotifystreamer.adapters.ArtistsSearchResultAdapter;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Pager;

public class BandSearchFragment extends Fragment {

    // to satisfy looking for bands with very short names like U2, but also trying to respect API limits
    private final int NUM_OF_CHARS_BEFORE_SEARCH = 1;

    private ArtistsSearchResultAdapter mArtistsSearchResultAdapter;
    private SpotifyService mSpotify;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpotifyApi api = new SpotifyApi();
        mSpotify = api.getService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_band_search, container, false);
        EditText musicSearchBox = (EditText) rootView.findViewById(R.id.music_search_box);
        musicSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > NUM_OF_CHARS_BEFORE_SEARCH) {
                    SearchArtistsTask search = new SearchArtistsTask(s.toString());
                    search.execute();
                }
            }
        });

        ListView lv = (ListView) rootView.findViewById(R.id.searchListView);
        mArtistsSearchResultAdapter = new ArtistsSearchResultAdapter(getActivity());
        lv.setAdapter(mArtistsSearchResultAdapter);
        lv.setOnItemClickListener(mArtistsSearchResultAdapter);
        return rootView;
    }

    public class SearchArtistsTask extends AsyncTask<Void, Void, List<Artist>> {

        private String mSearchStr;
        private Toast mToast;

        public SearchArtistsTask(String searchStr){
            this.mSearchStr = searchStr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Artist> doInBackground(Void... params) {
            // added the asterisk so that partial names still find bands/singers
            // for ex: 'Subli' will find Sublime whereas without an asterisk it would not
            try {
                ArtistsPager artistsPager = mSpotify.searchArtists(mSearchStr + "*");
                Pager<Artist> pagerOfArtists = artistsPager.artists;
                return pagerOfArtists.items;
            } catch (Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Artist> artists) {
            super.onPostExecute(artists);

            // cancel current toast because a new action is being determined
            // it could succeed or it could fail
            if(mToast != null) mToast.cancel();

            if(artists == null) {
                mToast = Toast.makeText(getActivity(), "An unexpected error occurred! :(", Toast.LENGTH_SHORT);
                mToast.show();
            } else if(artists.isEmpty()){
                mToast = Toast.makeText(getActivity(), "No artists found! Please try again.", Toast.LENGTH_SHORT);
                mToast.show();
            }

            SpotifyListHelper.setArtistList(artists);
            mArtistsSearchResultAdapter.notifyDataSetChanged();
        }
    }
}
