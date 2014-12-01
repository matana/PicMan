package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by bkiss on 11.11.2014.
 */
public class StorageManager {

    Context context;

    public StorageManager(Context context){
        this.context = context.getApplicationContext();
    }

    public File createPublicStorageDir(){
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "PicMan");
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    public File createPrivateStorageDir(){
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
