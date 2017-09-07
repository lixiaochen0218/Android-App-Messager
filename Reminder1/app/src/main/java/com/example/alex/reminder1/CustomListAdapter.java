package com.example.alex.reminder1;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] titles;
    private final Integer[] imgid;
    private final Calendar[] calendars;

    public CustomListAdapter(Activity context, String[] titles, Integer[] imgid, Calendar[] calendars) {
        super(context, R.layout.mylist, titles);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.titles=titles;
        this.imgid=imgid;
        this.calendars=calendars;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(titles[position]);
        imageView.setImageResource(imgid[position]);
        extratxt.setText(String.format("%1$tA %1$tb %1$td %1$tY at %1$tI:%1$tM %1$Tp",calendars[position]));
        return rowView;

    };
}