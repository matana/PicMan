package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.custom.PicManInfoWindowAdapter;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.data.PicItem;

public class MapActivity extends Activity implements TaskCompleted, OnMapReadyCallback {

    private GoogleMap map;
    private LatLng location = new LatLng(19.675740, -155.769160);
    private ArrayList<PicItem> items;

    private Marker currentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        items = (ArrayList<PicItem>) getIntent().getSerializableExtra("items");

        //Location l = getIntent().getParcelableExtra("location");

        //if (l != null)
        //   location = new LatLng(l.getLatitude(), l.getLongitude());

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
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

    @Override
    public void onTaskCompleted(final Bitmap bitmap) {
        Log.i(getClass().getName(), "Bitmap loaded!");
        LayoutInflater factory = LayoutInflater.from(MapActivity.this);
        final View view = factory.inflate(R.layout.img_placholder, null);
        ImageView imgView = (ImageView)view.findViewById(R.id.placeholder);
        imgView.setImageBitmap(bitmap);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Detials");
        dialog.setView(view);
        dialog.show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        //map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        //map.setMyLocationEnabled(true);
        map.setInfoWindowAdapter(new PicManInfoWindowAdapter(this));
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                new AsyncTask<String, Void, Bitmap>() {


                    TaskCompleted taskCompleted = (TaskCompleted) MapActivity.this;

                    @Override
                    protected Bitmap doInBackground(String... params) {
                        try {
                            Log.i(getClass().getName(), params[0]);
                            URL url = new URL(params[0]);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            Bitmap myBitmap = BitmapFactory.decodeStream(input);
                            return myBitmap;
                        } catch (IOException e) {
                            Log.i(getClass().getName(), e.getStackTrace().toString());
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(Bitmap result) {
                        taskCompleted.onTaskCompleted(result);
                    }


                }.execute("http://theoldreader.com/kittens/600/400/");

            }
        });
        currentMarker = map.addMarker(new MarkerOptions()
                .position(location)
                .title("You're here!")
                .snippet("LAT: "
                        + location.latitude
                        + System.getProperty("line.separator")
                        + "LON: "
                        + location.longitude)
                .icon(BitmapDescriptorFactory.fromPath(items.get(0).getData(PicItem.ITEM_THUMBNAIL_PATH))));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);


       /* map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {

                myLocation = location;

                if(location.getAccuracy() > myLocation.getAccuracy()) {
                    if(currentMarker != null) {
                       currentMarker.remove();
                       addCurrentMarker(location);
                    }
                }
                if(currentMarker == null) {
                    addCurrentMarker(location);
                }

            }

            private void addCurrentMarker(Location location) {
                currentMarker = map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()))
                        .title("You're here!")
                        .snippet("LAT: "
                                + location.getLongitude()
                                + System.getProperty("line.separator")
                                + "LON: "
                                + location.getLongitude())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            }
        });*/

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                map.addMarker(new MarkerOptions().position(latLng)
                        .title("Just an other marker")
                        .snippet("LAT: "
                                + latLng.latitude
                                + System.getProperty("line.separator")
                                + "LON: "
                                + latLng.longitude)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
            }
        });
    }
}
