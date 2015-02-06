package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.custom;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.Toaster;

/**
 * Created by matana on 22.01.15.
 */
public class PicManInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {


    private final View customInfoView;

    public PicManInfoWindowAdapter(final Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        customInfoView = inflater.inflate(R.layout.custom_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Log.i(getClass().getName(), marker.toString());
        ((TextView)customInfoView.findViewById(R.id.title)).setText(marker.getTitle());
        ((TextView)customInfoView.findViewById(R.id.snippet)).setText(marker.getSnippet());
        return customInfoView;
    }
}
