package com.example.news;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>>
         {
             private static final String LOG_TAG = MainActivity.class.getName();

             /** URL for earthquake data from the USGS dataset */
             private static final String NEWS_URL =
                     "https://newsapi.org/v2/top-headlines?country=in&apiKey=182638fff18b4147956c08e5a44c2444";

             /**
              * Constant value for the earthquake loader ID. We can choose any integer.
              * This really only comes into play if you're using multiple loaders.
              */
             private static final int NEWS_LOADER_ID = 1;

             /** Adapter for the list of earthquakes */
             private NewsAdapter mAdapter;

             /** TextView that is displayed when the list is empty */
             private TextView EmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView =  findViewById(R.id.list);

        EmptyStateTextView =  findViewById(R.id.empty_view);
        newsListView.setEmptyView(EmptyStateTextView);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

       newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                News currentnews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentnews.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            EmptyStateTextView.setText("NO INTERNET CONNECTION");
        }


    }



             @Override
             public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {



                 Uri baseUri = Uri.parse(NEWS_URL);
                 Uri.Builder uriBuilder = baseUri.buildUpon();



                 return new NewsLoader(this, uriBuilder.toString());
             }

             @Override
             public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
                 // Hide loading indicator because the data has been loaded
                 View loadingIndicator = findViewById(R.id.loading_indicator);
                 loadingIndicator.setVisibility(View.GONE);

                 // Set empty state text to display "No earthquakes found."
                 EmptyStateTextView.setText("NO NEWS FOUND");

                 // Clear the adapter of previous earthquake data
                 //mAdapter.clear();

                 // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
                 // data set. This will trigger the ListView to update.
                 if (news != null && !news.isEmpty()) {
                     mAdapter.addAll(news);
                 }
             }

             @Override
             public void onLoaderReset(Loader<List<News>> loader) {
                 // Loader reset, so we can clear out our existing data.
                 mAdapter.clear();
             }






         }


