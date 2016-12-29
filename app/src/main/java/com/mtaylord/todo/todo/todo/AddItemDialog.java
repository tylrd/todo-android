package com.mtaylord.todo.todo.todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.mtaylord.todo.R;

/**
 * Created by taylor on 12/6/16.
 */

public class AddItemDialog extends DialogFragment {

    public interface DialogListener {
        void onDialogPositiveClick(AddItemDialog dialog, String itemName);

        void onDialogNegativeClick(AddItemDialog dialog);
    }

    DialogListener mListener;

    public void setDialogListener(DialogListener listener) {
        mListener = listener;
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
                        if (mListener != null) {
                            TextView text = (TextView) getDialog().findViewById(R.id.item_dialog_name);
                            mListener.onDialogPositiveClick(AddItemDialog.this, text.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mListener != null) {
                            mListener.onDialogNegativeClick(AddItemDialog.this);
                        }
                    }
                }).create();
    }
}
