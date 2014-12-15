package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.exception.LocationNotAvailableException;


public class LocationHelper {

    private final Criteria criteria;
    private Location location;
    private LocationManager loccationManager;

    private int minDistance = 0;
    private int minTime = 0;

    private final String GPS = LocationManager.GPS_PROVIDER;
    private final String NETWORK = LocationManager.NETWORK_PROVIDER;

    private String bestProvider;
    private String averageProvider;

    private LocationListener averageLocationListener = new PicManLocationListener();
    private LocationListener bestLocationListener = new PicManLocationListener();

    private Context context;

    private final String CLASS_NAME = LocationHelper.class.getName();


    public LocationHelper(final Context context) {

        this.context = context;

        this.criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(true);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setAltitudeRequired(false);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_FINE);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_FINE);

        this.loccationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    }

    public Location getLocation() throws LocationNotAvailableException {
        if(location == null)
            throw new LocationNotAvailableException("Location is null.");
        else return location;
    }

    public boolean hasProvider() {
        return (averageProvider != null || bestProvider != null) && !averageProvider.equals(LocationManager.PASSIVE_PROVIDER);
    }

    public void registerLocationListener() {

        unregisterLocationListener();

        averageProvider = loccationManager.getBestProvider(criteria, false);
        bestProvider = loccationManager.getBestProvider(criteria, true);

        if(averageProvider == null) {
            Log.i(CLASS_NAME, "No location provider available on device.");
        } else if(averageProvider.equals(bestProvider)) {
            loccationManager.requestLocationUpdates(bestProvider, minTime, minDistance, bestLocationListener);
        } else {
            loccationManager.requestLocationUpdates(averageProvider, minTime, minDistance, averageLocationListener);
            if (bestProvider != null) {
                loccationManager.requestLocationUpdates(bestProvider, minTime, minDistance, bestLocationListener);
            }
        }

        compareAndSetLocation(loccationManager.getLastKnownLocation(averageProvider));

    }

    public void unregisterLocationListener() {
        loccationManager.removeUpdates(averageLocationListener);
        loccationManager.removeUpdates(bestLocationListener);
        averageProvider = null;
        bestProvider = null;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle("GPS DISABLED!");
        alert.setMessage("Go to location settings?");

        alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    }
                }
        );
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();

    }

    private class PicManLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            compareAndSetLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            registerLocationListener();
            Toast.makeText(context, "Provider enabled :: " + provider, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            unregisterLocationListener();
            Toast.makeText(context, "Provider disabled :: " + provider, Toast.LENGTH_SHORT).show();
        }
    }


    private void compareAndSetLocation(Location location) {
        if(location == null) {
            return;
        } else if(this.location != null) {
            boolean accuracy = location.getAccuracy() > this.location.getAccuracy();
            // boolean time = location.getElapsedRealtimeNanos() > this.location.getElapsedRealtimeNanos();
            if (accuracy) {
                this.location = location;
            }
        } else {
            this.location = location;
        }
    }

}
