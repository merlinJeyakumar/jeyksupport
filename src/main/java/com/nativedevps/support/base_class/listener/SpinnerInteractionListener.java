package com.nativedevps.support.base_class.listener;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

/*
 * spinner.setOnTouchListener(spinnerInteractionListener)
 * spinner.onItemSelectedListener = spinnerInteractionListener
 * */
public class SpinnerInteractionListener<T> implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

    boolean userSelect = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        userSelect = true;
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (userSelect) {
            // Your selection handling code here
            userSelect = false;

            onItemsSelected(parent, view, pos, ((T) parent.getSelectedItem()), id);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void onItemsSelected(AdapterView<?> parent, View view, int pos, T selectedItem, long id) {

    }
}