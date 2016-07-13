package com.example.sample.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.sample.myapplication.adapters.NewsAdapter;
import com.example.sample.myapplication.helpers.NewsRepository;
import com.example.sample.myapplication.models.NewsPackage;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.InputStream;
import java.net.URL;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    private static final String LogTag = "ItemListActivity";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private static final String URL_BASE = "http://rss2json.com/api.json?rss_url=";
    private static final String RSS_URL = URL_BASE + "https://www.nasa.gov/rss/dyn/breaking_news.rss";

    private RecyclerView _recyclerView;
    private SwipeRefreshLayout _refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();
        final ImageLoaderConfiguration initConfig = new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(options).build();

        ImageLoader.getInstance().init(initConfig);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("News Viewer");

        _refresh = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);

        _refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Loadnews();
            }
        });

        _recyclerView = (RecyclerView)findViewById(R.id.item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        Log.i(LogTag, "onCreate");
        if(NewsRepository.I().Get() != null && NewsRepository.I().Get().size() > 0){
            updateUI();
            return;
        }
        Loadnews();
    }

    private void Loadnews(){
        new ReloadNews().execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LogTag, "onResume");
    }

    private void updateUI(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(linearLayoutManager);
        _recyclerView.setAdapter(new NewsAdapter( ItemListActivity.this, mTwoPane));
    }

    private class ReloadNews extends AsyncTask<Void, Void, Void> {
        private ProgressDialog _progressDialog;

        @Override
        protected void onPreExecute() {
            _progressDialog = ProgressDialog.show(ItemListActivity.this, "Loading", "Loading");
        }

        @Override
        protected Void doInBackground(Void... params) {
            NewsPackage newsPackage = getNewsFromUrl();
            if(newsPackage != null){
                NewsRepository.I().Add(newsPackage.getItems());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            updateUI();
            if(_progressDialog != null){
                _progressDialog.dismiss();
            }

            _refresh.setRefreshing(false);
        }


        private NewsPackage getNewsFromUrl(){
            try {
                // Create a URL for the desired page
                URL url = new URL(RSS_URL);

                // Read all the text returned by the server
                InputStream inputStream = url.openStream();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                NewsPackage newsPackage = objectMapper.readValue(inputStream, NewsPackage.class);
                inputStream.close();

                return newsPackage;
            } catch (Exception e) {
                Log.e(LogTag, e.getMessage());
            }
            return null;
        }
    }
}
