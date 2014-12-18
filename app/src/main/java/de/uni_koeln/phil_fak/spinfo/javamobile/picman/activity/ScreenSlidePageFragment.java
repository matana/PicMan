package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;

public class ScreenSlidePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        Bundle b = getArguments();
        ImageView imageView = (ImageView) rootView.findViewById(R.id.full_screen_image_view);
        imageView.setImageBitmap((Bitmap)b.getParcelable("img"));

        return rootView;
    }

}
