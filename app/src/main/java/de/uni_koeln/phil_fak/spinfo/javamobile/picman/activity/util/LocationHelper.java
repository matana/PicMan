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
import android.widget.Toast;

/**
 * Created by matana on 10.12.14.
 */
public class LocationHelper implements LocationListener {

    private Location location;

    protected LocationManager locationManager;

    private Context context;

    private String provider;



    public LocationHelper(final Context context) {
        this.context = context;
        getLocation(context);
    }

    public Location getLocation() {
        return location;
    }

    public boolean hasProvider()  {
        return !provider.equals(LocationManager.PASSIVE_PROVIDER) && provider != null;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("GPS NOT ENABLED!");
        alertDialog.setMessage("Do you want to go to location settings?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void getLocation(final Context context) {

        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {

            this.provider = findByCriteria(locationManager);
            locationManager.requestLocationUpdates(provider, 0, 0, this);
            this.location = locationManager.getLastKnownLocation(provider);

            if (location != null)
                onLocationChanged(location);
        }
    }

    private String findByCriteria(LocationManager locationManager) {

        Criteria criteria = new Criteria();

        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setCostAllowed(false);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(false);

        return locationManager.getBestProvider(criteria, true);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(context, "Provider enabled: " + provider, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(context, "Provider enabled: " + provider, Toast.LENGTH_SHORT).show();
    }

}
