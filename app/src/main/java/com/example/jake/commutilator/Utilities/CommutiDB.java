package com.example.jake.commutilator.Utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jake.commutilator.R;
import com.example.jake.commutilator.Trip;
import com.example.jake.commutilator.Vehicles.Vehicle;

/**
 * Created by Seth on 4/27/2015.
 */
public class CommutiDB extends SQLiteOpenHelper {
        public CommutiDB(Context context) {
        super(context, context.getResources().getString(R.string.db_name), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//Need code here to create table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveTrip(Trip trip) {

    }

    public void saveVehicle(Vehicle vehicle) {

    }

}
