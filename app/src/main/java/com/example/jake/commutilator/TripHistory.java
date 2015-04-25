package com.example.jake.commutilator;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class TripHistory extends ListActivity {
    TripManager tripManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);
        tripManager = TripManager.getInstance();
        setListAdapter(new TripAdapter(this, R.layout.trip_item, tripManager.getTripHistory()));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Trip trip = (Trip)getListAdapter().getItem(position);
        Intent intent = new Intent(this, TripDetail.class);
        intent.putExtra(TripDetail.CURRENT_TRIP_ID, trip.getId().toString());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class TripAdapter extends ArrayAdapter<Trip> {

        public TripAdapter(Context context, int resource, Trip[] objects) {
            super(context, resource, objects);
        }

        TripAdapter(Context context, int resource, List<Trip> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.trip_item, parent, false);
            }
            else {
                view = convertView;
            }

            Trip trip = getItem(position);

            TextView startTimeView = (TextView)view.findViewById(R.id.trip_start_time);
            TextView distanceView = (TextView)view.findViewById(R.id.trip_distance);
            TextView amountSavedView = (TextView)view.findViewById(R.id.trip_amount_saved);

            startTimeView.setText(new SimpleDateFormat("EEE MMM dd hh:mm a").format(trip.getStartTime()));
            distanceView.setText(new DecimalFormat("0.00 miles").format(trip.getDistance()));
            amountSavedView.setText(new DecimalFormat("$0.00").format(trip.getAmountSaved()));

            return view;
        }
    }
}
