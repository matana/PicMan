package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.DeleteDialogFragment;


/**
 * Created by matana on 27.11.14.
 */
public class DataProvider {

    private final File imageDir;
    private final File commentDir;

    private Bitmap[] images;
    private String[] descriptions;

    public DataProvider(StorageManager storageManager) {
        imageDir = storageManager.createPublicStorageDir();
        commentDir = storageManager.createPrivateStorageDir();
    }

    public void loadPicData(final Context context, final FragmentManager fragmentManager, ListView listView) {

        File[] imgFiles = imageDir.listFiles();
        File[] dataFiles = commentDir.listFiles();

        if(imgFiles != null && dataFiles != null) {
            loadPicData(context, fragmentManager, listView, imgFiles, dataFiles);
        }
    }

    private void loadPicData(final Context context, final FragmentManager fragmentManager, ListView listView, File[] imgFiles, File[] dataFiles) {

        images = new Bitmap[imgFiles.length];
        descriptions = new String[dataFiles.length];

        try {

            for (int i = 0; i < images.length; i++)
                images[i] = BitmapFactory.decodeFile(imgFiles[i].getCanonicalPath());

            for (int i = 0; i < descriptions.length; i++)
                descriptions[i] = readFile(dataFiles[i]);

        } catch (IOException e) {
            e.printStackTrace();
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
                Toaster.toastWrap(context, position + "");
                DeleteDialogFragment deleteDialog = new DeleteDialogFragment();

                Bundle bundle = new Bundle();
                bundle.putString("textData", descriptions[position]);
                bundle.putParcelable("imageData", images[position]);
                bundle.putInt("position", position);
                deleteDialog.onCreate(bundle);

                deleteDialog.show(fragmentManager, "Delete Image Data Dialog");
                return true;
            }
        });
    }

    private String readFile(final File file){

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }

            fileReader.close();
            return stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
