package com.mtaylord.todo;

/**
 * Created by taylor on 12/6/16.
 */

public interface ItemDialogListener {
    void onDialogPositiveClick(ItemDialog dialog, String itemName);
    void onDialogNegativeClick(ItemDialog dialog);
}
