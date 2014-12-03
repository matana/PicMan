package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;

/**
 * Created by matana on 01.12.14.
 */
public class ListViewAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] desc;
    private final Bitmap[] img;

    public ListViewAdapter(Context context, String[] desc, Bitmap[] img) {
        super(context, R.layout.pic_list_item, desc);

        this.context = context;
        this.desc = desc;
        this.img = img;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.pic_list_item, null, true);

        ImageView imageView = (ImageView) view.findViewById(R.id.pic_thumb);
        TextView textView = (TextView) view.findViewById(R.id.pic_desc);

        imageView.setImageBitmap(img[position]);
        textView.setText(desc[position]);

        //Log.d(getClass().getSimpleName(), position + ": " + img[position] + " " + desc[position]);
        return view;
    }

}