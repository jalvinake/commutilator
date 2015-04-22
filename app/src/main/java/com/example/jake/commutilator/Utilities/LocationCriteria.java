package com.example.jake.commutilator.Utilities;

import android.location.Criteria;

/**
 * Created by Seth on 4/20/2015.
 */
public class LocationCriteria extends Criteria {

    public LocationCriteria() {
        super();
        this.setAccuracy(Criteria.ACCURACY_FINE);
        //Do we have any other criteria?
    }
}
