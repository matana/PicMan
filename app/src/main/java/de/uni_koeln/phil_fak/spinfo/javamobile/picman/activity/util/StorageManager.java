package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;


import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by bkiss on 11.11.2014.
 */
public class StorageManager {

    private static StorageManager instance;

    private StorageManager() {
        // Utility class.
    }

    public static StorageManager getInstance() {
        if(instance == null)
            instance = new StorageManager();
        return instance;
    }

    public File createPublicStorageDir(){
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "PicMan");
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    public File createPrivateStorageDir(Context context ){
        File dir = new File(context.getExternalFilesDir(null), "PicMan");
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    public boolean isStorageAvailable(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }

}
