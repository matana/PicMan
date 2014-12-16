package de.uni_koeln.phil_fak.spinfo.javamobile.picman.data;

import android.location.Location;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class PicItem implements Serializable{

    public static final String ITEM_ID                   = "item_id";
    public static final String ITEM_PATH                 = "item_path";
    public static final String ITEM_THUMBNAIL_PATH       = "item_thumbnail_path";
    public static final String ITEM_COMMENT              = "item_comment";
    public static final String ITEM_LOCATION_LATITUDE    = "item_location_latitude";
    public static final String ITEM_LOCATION_LONGITUDE   = "item_location_longitude";
    public static final String ITEM_DATE                 = "item_date";

    private Map<String, String> data = new HashMap<>();

    public PicItem(String uri, String objectPath, String thumbnailPath, String comment, String date){
        data.put(ITEM_ID, uri);
        data.put(ITEM_PATH, objectPath);
        data.put(ITEM_THUMBNAIL_PATH, thumbnailPath);
        data.put(ITEM_COMMENT, comment);
       // data.put(ITEM_LOCATION_LATITUDE, String.valueOf(location.getLatitude()));
       // data.put(ITEM_LOCATION_LONGITUDE, String.valueOf(location.getLongitude()));
        data.put(ITEM_DATE, date);
    }


    public String getData(String itemDataKey){
        return data.get(itemDataKey);
    }

    public String getDisplayString(){
        return data.get(ITEM_DATE) + data.get(ITEM_COMMENT);
    }

    @Override
    public String toString(){
        return "[PicItem " + data.get(ITEM_ID) + "] " + data.get(ITEM_DATE) + ": " + data.get(ITEM_COMMENT);
    }

}
