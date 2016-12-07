package com.mtaylord.todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by taylor on 12/6/16.
 */

public class ItemDialog extends DialogFragment {

    interface DialogListener {
        void onDialogPositiveClick(ItemDialog dialog, String itemName);

        void onDialogNegativeClick(ItemDialog dialog);
    }

    DialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ItemDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        return builder.setMessage(R.string.dialog_add_item)
                .setView(inflater.inflate(R.layout.item_dialog, null))
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView text = (TextView) getDialog().findViewById(R.id.item_dialog_name);
                        mListener.onDialogPositiveClick(ItemDialog.this, text.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogNegativeClick(ItemDialog.this);
                    }
                }).create();
    }
}
