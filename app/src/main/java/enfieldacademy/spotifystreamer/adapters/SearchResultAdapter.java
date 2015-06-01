package enfieldacademy.spotifystreamer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import enfieldacademy.spotifystreamer.R;

/**
 * Created by para on 5/30/2015.
 * Used as the adapter that will define how each search result is displayed in a ListView
 */
public class SearchResultAdapter extends BaseAdapter{

    private final String TAG = "SearchResultAdapter";
    private LayoutInflater mInflater;

    public SearchResultAdapter(Context context){
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return 3;
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
        if(convertView != null) {
            // do nothing
        } else{
            try {
                convertView = mInflater.inflate(R.layout.item_search_music, parent, false);
            } catch (Exception e){
                Log.e(TAG, "convertView not inflated!", e);
            }
        }
        return convertView;
    }

}
