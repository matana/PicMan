package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.exception.LocationNotAvailableException;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.DataProvider;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.ImageHelper;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.ListViewAdapter;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.LocationHelper;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.StorageManager;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.Toaster;

public class PicManActivity extends ActionBarActivity implements DeleteDialogFragment.DeleteDialogFragmentListener {


    private static final int TAKE_PICTURE = 1;
    private DataProvider dataProvider;
    private LocationHelper locationHelper;
    private Location location;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_man);

        dataProvider = new DataProvider(getApplicationContext());
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startCamera(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {

            File file = null;
            try {
                file = createFile();
                Log.i(getClass().getSimpleName(), file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (file != null) {
                uri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, TAKE_PICTURE);
            }

        }
    }

    private File createFile() throws IOException {

        String imageFileName = "JPEG_" + ImageHelper.getInstance().getImageId() + "_";
        File storageDir = StorageManager.getInstance().createPublicStorageDir();

        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    public void showLocation(View view) {

        if (locationHelper.hasProvider()) {
            try {
                location = locationHelper.getLocation();

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

        if (requestCode != TAKE_PICTURE) {
            if (data != null) {
                if (data.hasExtra("data")) {
                    Log.i(getClass().getSimpleName(), "Only thumbnail was taken...");
                }
            }
        } else {
            Intent intent = new Intent(this, PicDetailsActivity.class);
            intent.putExtra("uri", uri.getPath());
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