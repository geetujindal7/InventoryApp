package com.example.hi.inventapp.Info;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hi on 27-05-2017.
 */

public class CatalogActivity {
    public static final String Content_Authority = "com.example.hi.inventapp";
    public static final Uri Base_Content_uri = Uri.parse("content://" + Content_Authority);
    public static final String PATH_ITEM = "items";

    private CatalogActivity() {
    }

    public static final class ItemEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Base_Content_uri, PATH_ITEM);
        public static final String CONTENT_LIST = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Content_Authority + "/" + PATH_ITEM;
        public static final String CONTENT_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + Content_Authority + "/" + PATH_ITEM;
        public static final String TABLE_NAME = "items";
        public static final String _ID = BaseColumns._ID;
        public static final String Name = "Item_Name";
        public static final String Quantity = "Item_Quantity";
        public static final String Image = "Item_Image";
        public static final String Price = "Item_Price";
    }
}
