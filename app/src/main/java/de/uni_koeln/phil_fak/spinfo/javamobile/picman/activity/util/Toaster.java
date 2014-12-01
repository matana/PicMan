package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by matana on 27.11.14.
 */
public class Toaster {

    public static void toastWrap(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
