package com.example.projetc2application.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;


public class AlertDialogFragment extends DialogFragment {

    public AlertDialogFragment() {
    }

    String title = "";
    String message = "";
    boolean cancelable = false;

    String positiveBtnTxt = "";
    DialogInterface.OnClickListener posClickListener;

    String negativeBtnTxt = "";
    DialogInterface.OnClickListener negClickListener;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }


    public void setPositiveButton(String btnTxt, DialogInterface.OnClickListener clickListener) {
        positiveBtnTxt = btnTxt;
        posClickListener = clickListener;
    }


    public void setNegativeButton(String btnTxt, DialogInterface.OnClickListener clickListener) {
        negativeBtnTxt = btnTxt;
        negClickListener = clickListener;
    }

    ArrayAdapter<String> arrayAdapter;
    DialogInterface.OnClickListener lstClickListener;

    public void setAdapter(ArrayAdapter<String> arrayAdapter, DialogInterface.OnClickListener clickListener) {
        this.arrayAdapter = arrayAdapter;
        lstClickListener = clickListener;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        if (!message.equals(""))
            dialog.setMessage(message);
        if (!title.equals("")) {
            dialog.setTitle(title);
        }
        dialog.setCancelable(cancelable);
        if (!negativeBtnTxt.equals("")) {
            dialog.setNegativeButton(negativeBtnTxt, negClickListener);
        }
        if (!positiveBtnTxt.equals("")) {
            dialog.setPositiveButton(positiveBtnTxt, posClickListener);
        }
        if (arrayAdapter != null) {
            dialog.setAdapter(arrayAdapter, lstClickListener);
        }

        AlertDialog alertDialog = dialog.create();
        alertDialog.setCancelable(cancelable);
        alertDialog.setCanceledOnTouchOutside(cancelable);
        setCancelable(cancelable);
        return alertDialog;
    }

}

