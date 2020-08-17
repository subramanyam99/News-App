package com.example.news;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;



public class NewsAdapter extends ArrayAdapter<News> {


    private static final String LOG_TAG = NewsAdapter.class.getName();


    private static final String DATE_SEPARATOR = "T";
    private static final String TIME_SEPARATOR = "Z";


    public NewsAdapter( Context context,  List<News> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        News currentnews = getItem(position);


        TextView TitleView =  listItemView.findViewById(R.id.article_title);
                  TitleView.setText(currentnews.getTitle());


        TextView desView =  listItemView.findViewById(R.id.article_description);
        desView.setText(currentnews.getDescription());


        String dateandtime = currentnews.getTime();






              String date = getdate(dateandtime);
        Log.i(LOG_TAG, "date= " + date);
        String time=gettime(dateandtime);
        Log.i(LOG_TAG, "time= " + time);



        TextView dateView =  listItemView.findViewById(R.id.date);
        dateView.setText(date);

        TextView timeView = listItemView.findViewById(R.id.time);
        timeView.setText(time);

        ImageView image = listItemView.findViewById(R.id.article_image);

        if (currentnews != null) {
            Picasso.with(this.getContext())
                    .load(currentnews.getImage_url())
                    .into(image);
        }


        return listItemView;

    }

    String getdate(String dateandtime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(dateandtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(date);
        return  formattedDate;
    }

    String gettime(String dateandtime){

        String[] parts = dateandtime.split(DATE_SEPARATOR);
        String timea=parts[1];
        String[] times = timea.split(TIME_SEPARATOR);
        String  time=times[0];
        return  time;


    }

}
