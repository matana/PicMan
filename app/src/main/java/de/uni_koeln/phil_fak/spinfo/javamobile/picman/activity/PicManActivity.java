package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Iterator;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.exception.LocationNotAvailableException;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.DataProvider;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.ListViewAdapter;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.LocationHelper;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.StorageManager;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.Toaster;

public class PicManActivity extends ActionBarActivity implements DeleteDialogFragment.DeleteDialogFragmentListener {


    private DataProvider dataProvider;
    private LocationHelper locationHelper;
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_man);

        dataProvider = new DataProvider(new StorageManager(getApplicationContext()));
        ListView listView = (ListView) findViewById(R.id.pic_list);
        dataProvider.loadPicData(this, getFragmentManager(), listView);

        locationHelper = new LocationHelper(this);
        locationHelper.registerLocationListener();
    }

    @Override
    protected void onPause() {
        locationHelper.unregisterLocationListener();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationHelper.registerLocationListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }

    public void showLocation(View view) {
        if (locationHelper.hasProvider()) {
            try {
                location = locationHelper.getLocation();

                Toast.makeText(this, "Location :: " + location, Toast.LENGTH_LONG).show();

//                Bundle extras = location.getExtras();
//                Iterator<String> iterator = extras.keySet().iterator();
//                while (iterator.hasNext()) {
//                    String key = iterator.next();
//                    Object obj = extras.get(key);
//                    Log.i(getClass().getSimpleName(), "key::" + key + " // value::" + obj);
//                }

                String LAT = location.getLatitude() + "";
                String LON = location.getLongitude() + "";

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?z=12&t=m&q=loc:" + LAT + "+" + LON));
                startActivity(intent);

            } catch (LocationNotAvailableException e) {
                e.printStackTrace();
            }
        } else {
            locationHelper.showSettingsAlert();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap picture = (Bitmap) extras.get("data");

            Intent intent = new Intent(this, PicDetailsActivity.class);
            intent.putExtra("pic", picture);
            startActivity(intent);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int position) {
        ListView listView = (ListView) findViewById(R.id.pic_list);
        ((ListViewAdapter) listView.getAdapter()).remove(dataProvider.deleteItem(position));

        Toaster.toastWrap(getApplicationContext(), "Item deleted.");
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }


}