package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.DeleteDialogFragment;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.FullScreenActivity;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.data.PicItem;


public class DataProvider {

    private ListViewAdapter adapter;
    private final File itemDataDir;
    private List<PicItem> items = new ArrayList<>();

    public DataProvider(final Context context) {
        itemDataDir = StorageManager.getInstance().createPrivateStorageDir(context);
    }

    public void loadPicData(final Context context, final FragmentManager fragmentManager, ListView listView) {
        File[] dataFiles = getItemDataFiles();

        if(dataFiles != null && dataFiles.length > 0) {
            loadPicData(context, fragmentManager, listView, dataFiles);
        }
    }

    private void loadPicData(final Context context, final FragmentManager fragmentManager, ListView listView, File[] dataFiles) {

        Arrays.sort(dataFiles, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                Date d1 = new Date(lhs.lastModified());
                Date d2 = new Date(rhs.lastModified());
                return d2.compareTo(d1);
            }
        });

        for (File f : dataFiles){
            try {
                PicItem item = deserializeItem(f);
                if (item != null) items.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        provideListView(context, fragmentManager,  listView);
    }

    public PicItem deleteItem(int pos){

        PicItem item = items.get(pos);
        File itemFile = new File(item.getData(PicItem.ITEM_PATH));
        File imgFile = new File(item.getData(PicItem.ITEM_ID));
        File thumbFile = new File(item.getData(PicItem.ITEM_THUMBNAIL_PATH));
        if (imgFile.exists()) imgFile.delete();
        if (itemFile.exists()) itemFile.delete();
        if (thumbFile.exists()) thumbFile.delete();
        return item;
    }

    private PicItem deserializeItem(File itemFile){
        PicItem item = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(itemFile));
            item = (PicItem) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    private void provideListView(final Context context, final FragmentManager fragmentManager, ListView listView) {
        adapter = new ListViewAdapter(context, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, FullScreenActivity.class);
                intent.putExtra("uri", items.get(position).getData(PicItem.ITEM_ID));
                intent.putExtra("desc",items.get(position).getDisplayString());
                context.startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                DeleteDialogFragment deleteDialog = new DeleteDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("textData", items.get(position).getDisplayString());
                bundle.putParcelable("imageData", BitmapFactory.decodeFile(items.get(position).getData(PicItem.ITEM_THUMBNAIL_PATH)));
                bundle.putInt("position", position);
                deleteDialog.onCreate(bundle);
                deleteDialog.show(fragmentManager, "Delete Image Data Dialog");
                return true;
            }
        });
    }

    private File[] getItemDataFiles(){
        return itemDataDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return dir.exists() && filename.endsWith(".pic");
            }
        });
    }

    public List<PicItem> getItems() {
        return items;
    }

}
