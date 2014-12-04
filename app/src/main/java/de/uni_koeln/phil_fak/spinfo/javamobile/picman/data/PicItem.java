package de.uni_koeln.phil_fak.spinfo.javamobile.picman.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class PicItem implements Serializable{

    public static final String ITEM_ID          = "item_id";
    public static final String ITEM_PATH        = "item_path";
    public static final String ITEM_IMG_PATH    = "item_img_path";
    public static final String ITEM_COMMENT     = "item_comment";
    public static final String ITEM_DATE        = "item_date";

    private Map<String, String> data;

    public PicItem() {
        data = new HashMap<String, String>();
    }

    public PicItem(String id, String path, String imgPath, String comment, String date){
        this();
        data.put(ITEM_ID, id);
        data.put(ITEM_PATH, path);
        data.put(ITEM_IMG_PATH, imgPath);
        data.put(ITEM_COMMENT, comment);
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
