package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.WindowManager;

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
        if (instance == null)
            instance = new ImageHelper();
        return instance;
    }

    public Bitmap getWindowSizedBitmapFromPath(String path, WindowManager windowManager) {

        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
        factoryOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, factoryOptions);

        int imageWidth = factoryOptions.outWidth;
        int imageHeight = factoryOptions.outHeight;
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        int width = point.x;
        int height = point.y;
        int scaleFactor = Math.min(imageWidth / width, imageHeight / height);

        factoryOptions.inJustDecodeBounds = false;
        factoryOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(path, factoryOptions);
    }

    public Bitmap getThumbnailBitmapFromPath(String path) {

        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
        factoryOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, factoryOptions);

        factoryOptions.inJustDecodeBounds = false;
        factoryOptions.inSampleSize = 5;

        return BitmapFactory.decodeFile(path, factoryOptions);
    }

    public String getImageId() {
        return "IMG_" + TimeStamper.getInstance().generateTimestamp(false);
    }

    public void saveImageData(Context context, Bitmap thumbnail, String text, String date, String uri) {

        StorageManager storageManager = StorageManager.getInstance();

        if (!storageManager.isStorageAvailable()) {
            Toaster.toastWrap(context, "External storage is not available.");
            return;
        }

        try {

            String thumbnailPath;
            String picItemPath;

            File dataDir = storageManager.createPrivateStorageDir(context);
            dataDir.mkdirs();

            String uriWithNoExtension = uri.substring(0, uri.lastIndexOf("."));
            thumbnailPath = uriWithNoExtension + "-thumbnail.jpg";
            String[] split = uriWithNoExtension.split("/");
            picItemPath = split[split.length - 1] + ".pic";
            Log.i(getClass().getSimpleName(), split[split.length - 1]);


            Log.d(getClass().getSimpleName(), "URI :: " + uri);
            Log.d(getClass().getSimpleName(), "OBJECT_PATH :: " + picItemPath);
            Log.d(getClass().getSimpleName(), "THUMBNAIL :: " + thumbnailPath);

            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, new FileOutputStream(thumbnailPath));

            PicItem picItem = new PicItem(uri, picItemPath, thumbnailPath, text, date);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
                    new File(dataDir, picItemPath)));
            oos.writeObject(picItem);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
