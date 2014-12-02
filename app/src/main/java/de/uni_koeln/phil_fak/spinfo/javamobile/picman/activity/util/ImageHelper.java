package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.data.PicItem;


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

    public void saveImageData(Context context, StorageManager storageManager, Bitmap picture, String text, String date) {
        //speicher prüfen
        if (!storageManager.isStorageAvailable()){
            Toaster.toastWrap(context, "Externer Speicher nicht verfügbar!");
            return;
        }

        //daten vorbereiten
        File imgDir = storageManager.createPublicStorageDir();
        File dataDir = storageManager.createPrivateStorageDir();
        PicItem picItem;
        ObjectOutputStream oos;
        String imageID = "IMG_" + TimeStamper.getInstance().generateTimestamp(false);
        String imagePath = null;
        String picItemPath = null;

        //img-pfad festlegen
        try {
            imagePath = imgDir.getCanonicalPath() + "/" + imageID + ".png";
            picItemPath = dataDir.getCanonicalPath() + "/" + imageID + ".pic";
        } catch (IOException e) {
            e.printStackTrace();
        }

        //PicItem-objekt erstellen
        picItem = new PicItem(imageID,
                              imagePath,
                              text,
                              date);

        //bild speichern
        try{
            picture.compress(Bitmap.CompressFormat.PNG, 90, new FileOutputStream(imagePath));
        } catch (Exception e){
            e.printStackTrace();
        }

        //PicItem-objekt speichern
        try {
            oos = new ObjectOutputStream(new FileOutputStream(picItemPath));
            oos.writeObject(picItem);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
