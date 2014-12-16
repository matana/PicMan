package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.data.PicItem;

/**
 * Created by matana on 01.12.14.
 */
public class ListViewAdapter extends ArrayAdapter<PicItem> {

    private final Context context;
    private List<PicItem> items;

    public ListViewAdapter(Context context, List<PicItem> items) {

        super(context, R.layout.pic_list_item, items);

        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (items.size() == 0) return view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.pic_list_item, null, true);

        ImageView imageView = (ImageView) view.findViewById(R.id.pic_thumb);
        TextView textView = (TextView) view.findViewById(R.id.pic_desc);

        Bitmap thumbnail =  BitmapFactory.decodeFile(items.get(position).getData(PicItem.ITEM_THUMBNAIL_PATH));
        String desc = items.get(position).getDisplayString();
        if (thumbnail == null) {
            thumbnail = BitmapFactory.decodeResource(context.getResources(), R.drawable.error);
            desc += "\n(image deleted!)";
        }

        imageView.setImageBitmap(thumbnail);
        textView.setText(desc);

        return view;
    }

}