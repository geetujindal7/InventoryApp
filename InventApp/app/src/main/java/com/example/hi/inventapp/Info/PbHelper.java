package com.example.hi.inventapp.Info;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.hi.inventapp.Info.CatalogActivity.ItemEntry;
/**
 * Created by hi on 27-05-2017.
 */

public class PbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Item.db";
    private static final int DATABASE_VERSION = 4;

    public PbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_table = "CREATE TABLE " + ItemEntry.TABLE_NAME + "(" + ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ItemEntry.Name + " TEXT NOT NULL, " +
                ItemEntry.Quantity + " INTEGER NOT NULL DEFAULT 0, " + ItemEntry.Image + " TEXT, " + ItemEntry.Price + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(Create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS" + ItemEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}
