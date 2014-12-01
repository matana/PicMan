package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;

import java.util.Date;

/**
 * Created by matana on 27.11.14.
 */
public class TimeStamper {

    private static TimeStamper instance;


    private TimeStamper() {
        // Utility class
    }

    public static TimeStamper getInstance() {
        if(instance == null)
            instance = new TimeStamper();
        return instance;
    }

    public String generateTimestamp(boolean formatted) {
        Date today = new Date();
        if (formatted)
            return String.format("%tB %te, %tY %n", today,today,today,today);
        else
            return System.currentTimeMillis() + "";
    }
}
