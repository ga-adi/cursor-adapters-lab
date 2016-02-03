package ly.generalassemb.drewmahrt.shoppinglistver2.activities;

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
import ly.generalassemb.drewmahrt.shoppinglistver2.db.GrocerySQLHelper;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class CreateDialogFragment extends DialogFragment {
  private GrocerySQLHelper mHelper;
  private EditText mItemNameEdit;
  private EditText mItemDescriptionEdit;
  private EditText mItemPriceEdit;
  private Spinner mSpinner;

  public CreateDialogFragment() {}

  /*package*/ static CreateDialogFragment newInstance() {
    CreateDialogFragment fragment = new CreateDialogFragment();
    return fragment;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    View view = getActivity().getLayoutInflater().inflate(
        R.layout.create_item_dialog, null);
    mItemNameEdit = (EditText)view.findViewById(R.id.item_name_edit);
    mItemDescriptionEdit =
        (EditText)view.findViewById(R.id.item_description_edit);
    mItemPriceEdit = (EditText)view.findViewById(R.id.item_price_edit);
    mSpinner = (Spinner)view.findViewById(R.id.type_spin);
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
                Bundle bundle = new Bundle();
                bundle.putString(GrocerySQLHelper.COL_ITEM_NAME, itemName);
                bundle.putString(GrocerySQLHelper.COL_DESCRIPTION, description);
                bundle.putDouble(GrocerySQLHelper.COL_PRICE, price);
                bundle.putString(GrocerySQLHelper.COL_TYPE, itemTypeName);

                intent.putExtras(bundle);
                startActivity(intent);
              }
            })
        .create();
  }
}
