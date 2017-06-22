package com.example.hi.inventapp.Info;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.example.hi.inventapp.Info.CatalogActivity.ItemEntry;
/**
 * Created by hi on 27-05-2017.
 */

public class InventProvider extends ContentProvider {
    private static final int item = 100;
    private static final int item_id = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CatalogActivity.Content_Authority, CatalogActivity.PATH_ITEM, item);
        sUriMatcher.addURI(CatalogActivity.Content_Authority, CatalogActivity.PATH_ITEM + "/#", item_id);
    }

    private PbHelper mProvider;

    @Override
    public boolean onCreate() {
        mProvider = new PbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mProvider.getReadableDatabase();
        Cursor c;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case item:
                c = db.query(ItemEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case item_id:
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[]{
                        String.valueOf(ContentUris.parseId(uri))
                };
                c = db.query(ItemEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case item:
                return insertItem(uri, contentValues);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case item:
                return updateItem(uri, contentValues, selection, selectionArgs);
            case item_id:
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[]{
                        String.valueOf(ContentUris.parseId(uri))
                };
                return updateItem(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mProvider.getWritableDatabase();
        int rowsDelete;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case item:
                rowsDelete = db.delete(ItemEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case item_id:
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDelete = db.delete(ItemEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (rowsDelete != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDelete;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case item:
                return ItemEntry.CONTENT_LIST;
            case item_id:
                return ItemEntry.CONTENT_ITEM;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    private Uri insertItem(Uri uri, ContentValues contentValues) {
        String name = contentValues.getAsString(ItemEntry.Name);
        if (name == null) {
            throw new IllegalArgumentException("Invalid Item Name");
        }
        Integer quantity = contentValues.getAsInteger(ItemEntry.Quantity);
        if (quantity < 0 && quantity != null) {
            throw new IllegalArgumentException("Invalid number of quantity");
        }
        Integer price = contentValues.getAsInteger(ItemEntry.Price);
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Invalid value of price");
        }
        String image = contentValues.getAsString(ItemEntry.Image);
        if (image == null) {
            throw new IllegalArgumentException("Invalid Image");
        }
        SQLiteDatabase db = mProvider.getWritableDatabase();
        long id = db.insert(ItemEntry.TABLE_NAME, null, contentValues);
        if (id == -1) {
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    private int updateItem(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        if (contentValues.containsKey(ItemEntry.Name)) {
            String ItemName = contentValues.getAsString(ItemEntry.Name);
            if (ItemName == null) {
                throw new IllegalArgumentException("Enter Item Name ");
            }
        }
        if (contentValues.containsKey(ItemEntry.Quantity)) {
            Integer ItemQuantity = contentValues.getAsInteger(ItemEntry.Quantity);
            if (ItemQuantity != null && ItemQuantity < 0) {
                throw new IllegalArgumentException("Enter valid quantity");
            }
        }
        if (contentValues.containsKey(ItemEntry.Image)) {
            String ItemImage = contentValues.getAsString(ItemEntry.Image);
            if (ItemImage == null)
                throw new IllegalArgumentException("Insert Valid Image");
        }
        if (contentValues.containsKey(ItemEntry.Price)) {
            Integer ItemPrice = contentValues.getAsInteger(ItemEntry.Price);
            if (ItemPrice == null || ItemPrice < 0) {
                throw new IllegalArgumentException("Enter positive price");
            }
        }
        if (contentValues.size() == 0) {
            return 0;
        }
        SQLiteDatabase db = mProvider.getWritableDatabase();
        int rowsUpdated;
        rowsUpdated = db.update(ItemEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}