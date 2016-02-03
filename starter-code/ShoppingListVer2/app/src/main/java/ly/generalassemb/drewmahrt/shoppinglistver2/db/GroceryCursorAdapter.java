package ly.generalassemb.drewmahrt.shoppinglistver2.db;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ly.generalassemb.drewmahrt.shoppinglistver2.R;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class GroceryCursorAdapter extends CursorAdapter {
  private static String TAG = GroceryCursorAdapter.class.getCanonicalName();
  public GroceryCursorAdapter(Context context, Cursor c) {
    super(context, c, 0);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    Log.d(TAG, "Inflating view");
    return LayoutInflater.from(context)
        .inflate(R.layout.grocery_item, parent, false);
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    // UI Elements
    final TextView itemNameText =
        (TextView)view.findViewById(R.id.item_name_txt);
    final TextView descriptionText =
        (TextView)view.findViewById(R.id.item_description_txt);
    final TextView priceText = (TextView)view.findViewById(R.id.item_price_txt);
    final TextView typeText = (TextView)view.findViewById(R.id.item_type);
    final TextView itemIDText  =(TextView) view.findViewById(R.id.item_id);
    Log.d(TAG, "Binding UI");

    // Cursor Indices
    int idIndex = cursor.getColumnIndex(GrocerySQLHelper.COL_ID);
    int nameIndex =
        cursor.getColumnIndex(GrocerySQLHelper.COL_ITEM_NAME);
    int descriptionIndex =
        cursor.getColumnIndex(GrocerySQLHelper.COL_DESCRIPTION);
    int priceIndex = cursor.getColumnIndex(GrocerySQLHelper.COL_PRICE);
    int typeIndex = cursor.getColumnIndex(GrocerySQLHelper.COL_TYPE);
    Log.d(TAG, "Got cursor indices");

    // Row info
    final String itemId = cursor.getString(idIndex);
    final String itemName = cursor.getString(nameIndex);
    final String description = cursor.getString(descriptionIndex);
    final String price = cursor.getString(priceIndex);
    final String type = cursor.getString(typeIndex);
    Log.d(TAG, "Got items from database");

    itemNameText.setText(itemName);
    descriptionText.setText(description);
    priceText.setText(price);
    typeText.setText(type);
    itemIDText.setText(itemId);

    Log.d(TAG, "UI Set up");
  }
}
