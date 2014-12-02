package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.util.Arrays;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.DeleteDialogFragment;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.data.PicItem;


public class DataProvider {

    private final File commentDir;

    private Bitmap[] images;
    private String[] descriptions;

    public DataProvider(StorageManager storageManager) {
        commentDir = storageManager.createPrivateStorageDir();
    }

    public void loadPicData(final Context context, final FragmentManager fragmentManager, ListView listView) {
        File[] dataFiles = commentDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".pic");
            }
        });

        if(dataFiles != null && dataFiles.length > 0) {
            loadPicData(context, fragmentManager, listView, dataFiles);
        }
    }

    private void loadPicData(final Context context, final FragmentManager fragmentManager, ListView listView, File[] dataFiles) {
        ObjectInputStream ois;
        PicItem picItem;
        Arrays.sort(dataFiles);
        images = new Bitmap[dataFiles.length];
        descriptions = new String[dataFiles.length];

        for (int i = 0; i < dataFiles.length; i++){
            try {
                ois = new ObjectInputStream(new FileInputStream(dataFiles[i]));
                picItem = (PicItem) ois.readObject();
                images[i] = BitmapFactory.decodeFile(picItem.getData(PicItem.ITEM_IMG_PATH));
                descriptions[i] = picItem.getDisplayString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        provideListView(context, fragmentManager,  listView);
    }

    private void provideListView(final Context context, final FragmentManager fragmentManager, ListView listView) {

        ListViewAdapter adapter = new ListViewAdapter(context, descriptions, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent call ....
                Toaster.toastWrap(context, parent.getAdapter().getItem(position).toString());
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toaster.toastWrap(context, parent.getAdapter().getItem(position).toString());
                Toaster.toastWrap(context, descriptions[position]);
                DeleteDialogFragment deleteDialog = new DeleteDialogFragment();
                deleteDialog.setTextData(descriptions[position]);
                deleteDialog.setImageData(images[position]);
                deleteDialog.setPosition(position);
                deleteDialog.show(fragmentManager, "Delete Image Data Dialog");
                return true;
            }
        });
    }

}
