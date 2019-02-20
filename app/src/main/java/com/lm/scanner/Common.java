package com.lm.scanner;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;



public class Common {

    final public static int EXIT_APPLICATION = 0;
    final public static int OPTIONS_MENU_ITEM_ID_1 = 1;
    final public static int OPTIONS_MENU_ITEM_ID_2 = 2;
    public final static int MESSAGE_TYPE_ERROR = 0;
    public final static int MESSAGE_TYPE_INFORMATION = 1;
    public final static int MESSAGE_TYPE_SUCCESS = 2;



    public static void showMessage(String title, String message, Context context, int messageType) {

        AlertDialog.Builder alertDialog = new Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        switch(messageType) {

            case MESSAGE_TYPE_ERROR:
                alertDialog.setIcon(R.drawable.error_icon);
                break;
            case MESSAGE_TYPE_INFORMATION:
                alertDialog.setIcon(R.drawable.info_icon);
                break;
            case MESSAGE_TYPE_SUCCESS:
                alertDialog.setIcon(R.drawable.check);
                break;


            default:
                break;
        }

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }





}
