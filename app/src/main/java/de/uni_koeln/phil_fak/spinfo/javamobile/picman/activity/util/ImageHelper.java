package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by matana on 27.11.14.
 */
public class ImageHelper {

    private static ImageHelper instance;

    private ImageHelper() {
        // Utility class
    }

    public static ImageHelper getInstance() {
        if(instance == null)
            instance = new ImageHelper();
        return instance;
    }

    public void saveImageData(Context context, StorageManager storageManager, Bitmap picture, String text) {

        File imgDir = storageManager.createPublicStorageDir();
        File dataDir = storageManager.createPrivateStorageDir();

        //Bedingungen prüfen
        if (!storageManager.isStorageAvailable()){
            Toaster.toastWrap(context, "Externer Speicher nicht verfügbar!");
            return;
        }

        //Speichern
        FileOutputStream out = null;
        String imageID = "IMG_" + TimeStamper.getInstance().generateTimestamp(false);


        try{

            //Bild
            out = new FileOutputStream(imgDir.getCanonicalPath() + "/" + imageID + ".png");
            picture.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();

            //Meta-Daten
            out = new FileOutputStream(dataDir.getCanonicalPath() + "/" + imageID + ".txt");
            out.write(text.getBytes());

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if (out != null){
                    out.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
