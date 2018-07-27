package ueolot.com.ueolot.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

import ueolot.com.ueolot.R;


public class Fragment_EnViu extends Fragment {

    ListView listView;
    RecyclerView recyclerView;

    public Fragment_EnViu() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View v = null;
        if(comprovarInternet(getContext())) {
            v = inflater.inflate(R.layout.fragment_en_viu, container, false);
            if (v != null) {


                TwitterConfig config = new TwitterConfig.Builder(getContext())
                        .logger(new DefaultLogger(Log.DEBUG))
                        .twitterAuthConfig(new TwitterAuthConfig("JlOaq9KEOErEROb9BU5nkwQbE", "kVdbcyGm6sru2gR4MumATi50AInsq31ag6UE26APBpZzUlDGAO"))
                        .debug(true)
                        .build();
                Twitter.initialize(config);

                 recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                        .query("#LIVEUEO")
                        .maxItemsPerRequest(50)
                        .build();

                final TweetTimelineRecyclerViewAdapter adapter =
                        new TweetTimelineRecyclerViewAdapter.Builder(getContext())
                                .setTimeline(searchTimeline)
                                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                                .build();

                recyclerView.setAdapter(adapter);
            }
        }else{
            Snackbar.make(v,"No hi ha connexió a Internet", Snackbar.LENGTH_LONG).show();
        }
        return v;
    }

    public static boolean comprovarInternet(Context context){

        boolean connexio = false;

        ConnectivityManager connect = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] xarxes = connect.getAllNetworkInfo();

        for(int i=0; i<xarxes.length; i++){
            if(xarxes[i].getState() == NetworkInfo.State.CONNECTED)
                connexio = true;
        }
        return connexio;
    }



}
