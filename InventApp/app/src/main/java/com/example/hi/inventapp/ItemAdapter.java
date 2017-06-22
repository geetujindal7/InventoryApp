package com.example.hi.inventapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hi.inventapp.Info.CatalogActivity.ItemEntry;
/**
 * Created by hi on 27-05-2017.
 */
public class ItemAdapter extends CursorAdapter {
    public ItemAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor c, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_list_view, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor c) {
        TextView mname = (TextView) view.findViewById(R.id.name);
        final TextView mquantity = (TextView) view.findViewById(R.id.quantity);
        TextView mprice = (TextView) view.findViewById(R.id.price);
        final int Item_ID = c.getInt(c.getColumnIndexOrThrow(ItemEntry._ID));

        int nameIndex = c.getColumnIndex(ItemEntry.Name);
        int quantityIndex = c.getColumnIndex(ItemEntry.Quantity);
        int priceIndex = c.getColumnIndex(ItemEntry.Price);

        String name = c.getString(nameIndex);
        final int quantityAdd = c.getInt(quantityIndex);
        final int price = c.getInt(priceIndex);

        Button sell1 = (Button) view.findViewById(R.id.sell1);
        sell1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = quantityAdd;
                if (quantity <= 0) {
                    Toast.makeText(context, "EMPTY", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put(ItemEntry.Quantity, quantity);

                Uri uri = ContentUris.withAppendedId(ItemEntry.CONTENT_URI, Item_ID);
                context.getContentResolver().update(uri, contentValues, null, null);
                mquantity.setText(Integer.toString(quantityAdd));
            }
        });
        Button sell2 = (Button) view.findViewById(R.id.sell2);
        sell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = quantityAdd;
                if(quantity >=2)
                {
                    quantity = quantity - 2;

                ContentValues contentValues = new ContentValues();
                contentValues.put(ItemEntry.Quantity, quantity);

                Uri uri = ContentUris.withAppendedId(ItemEntry.CONTENT_URI, Item_ID);
                context.getContentResolver().update(uri, contentValues, null, null);
                mquantity.setText(Integer.toString(quantityAdd));
                }
                else
                {
                    Toast.makeText(context,"qauntity is less than 2",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mname.setText(name);
        mprice.setText(Integer.toString(price));
        mquantity.setText(Integer.toString(quantityAdd));
    }
}
