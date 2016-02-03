package ly.generalassemb.drewmahrt.shoppinglistver2.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import ly.generalassemb.drewmahrt.shoppinglistver2.R;
import ly.generalassemb.drewmahrt.shoppinglistver2.activities.MainActivity;
import ly.generalassemb.drewmahrt.shoppinglistver2.db.GrocerySQLHelper;
import ly.generalassemb.drewmahrt.shoppinglistver2.models.GroceryItem;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class GroceryDialogFragment extends DialogFragment {
  public static final String TAG =
      GroceryDialogFragment.class.getCanonicalName();
  public static final String UPDATE = "UPd413";
  public static final String CREATE = "cr3413";
  private EditText mItemNameEdit;
  private EditText mItemDescriptionEdit;
  private EditText mItemPriceEdit;
  private Spinner mSpinner;
  private GroceryItem.Updater mUpdater;

  public GroceryDialogFragment() {}

  public static GroceryDialogFragment newInstance() {
    GroceryDialogFragment fragment = new GroceryDialogFragment();
    return fragment;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    View view = getActivity().getLayoutInflater().inflate(
        R.layout.create_item_dialog, null);

    Bundle bundle = getArguments();
    mItemNameEdit = (EditText)view.findViewById(R.id.item_name_edit);
    mItemDescriptionEdit =
        (EditText)view.findViewById(R.id.item_description_edit);
    mItemPriceEdit = (EditText)view.findViewById(R.id.item_price_edit);
    mSpinner = (Spinner)view.findViewById(R.id.type_spin);
    if (bundle != null) {
      mUpdater = (GroceryItem.Updater)bundle.getSerializable(
          GroceryDialogFragment.UPDATE);
      GroceryItem item = (GroceryItem)bundle.getSerializable(CREATE);
      if (item != null) {
        mItemNameEdit.setText(item.getName());
        mItemDescriptionEdit.setText(item.getDescription());
        mItemPriceEdit.setText(item.getPrice() + "");
        mSpinner.setSelection(getItemTypeIndex(item.getItemType()));
      }
    }

    return new AlertDialog.Builder(getActivity())
        .setView(view)
        .setTitle("Create Item")
        .setCancelable(false)
        .setPositiveButton(
            "ADD",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

                final String itemName = mItemNameEdit.getText().toString();
                if ((itemName.isEmpty()) || (itemName.length() < 2)) {
                  mItemNameEdit.setError(
                      "Item name must be longer than two characters");
                  return;
                }

                final String description =
                    mItemDescriptionEdit.getText().toString();

                final double price =
                    Double.parseDouble(mItemPriceEdit.getText().toString());
                if (price < 0) {
                  mItemPriceEdit.setError("Price cannot be negative");
                  return;
                }

                final String itemTypeName =
                    mSpinner.getSelectedItem().toString();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                if (mUpdater != null) {
                  mUpdater.name(itemName)
                      .price(price)
                      .description(description)
                      .itemType(GroceryItem.ItemType.valueOf(itemTypeName));
                  intent.putExtra(GroceryDialogFragment.UPDATE, mUpdater);
                  startActivity(intent);
                } else {
                  final GroceryItem.Builder builder = new GroceryItem.Builder();
                  builder.name(itemName)
                      .price(price)
                      .description(description)
                      .itemType(GroceryItem.ItemType.valueOf(itemTypeName));
                  intent.putExtra(GrocerySQLHelper.TABLE_NAME, builder.build());
                  startActivity(intent);
                }
              }
            })
        .create();
  }

  private int getItemTypeIndex(GroceryItem.ItemType typeName) {
    String name = typeName.name();
    int index = -1;
    for (int i = 0; i < mSpinner.getCount(); ++i) {
      if (mSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(name)) {
        return i;
      }
    }
    return index;
  }
}
