package com.muruga.retrofitgetserver;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

public class Utilkit {

    private static final String TAG = "Utilkit";
    private static String EMPTY_STR = "";
    private static Context context;
    public static String message;
    public static Dialog dialog;
    public static Handler mProgressHandler;
    private static boolean cancelable = true;
    public static AlertDialog alertDialog;

    public static boolean validateObjectValues(Object _validateObj) {
        if (_validateObj == null)
            return false;
        else {
            return !(_validateObj instanceof String
                    && ((String) _validateObj).trim().equalsIgnoreCase(
                    EMPTY_STR));
        }
    }

    /**
     * @param context
     * @param cancelable
     */
    public static void showSpinnerDialog(Context context, boolean cancelable) {

        try {
            Utilkit.context = context;
            message = null;
            mProgressHandler = new Handler();
            mProgressHandler.post(mShowCustomSpinnerDialog);
            Utilkit.cancelable = cancelable;
        } catch (Exception e) {
            dialog = null;
        }
    }


    public static Runnable mShowCustomSpinnerDialog = new Runnable() {

        public void run() {
            try {
                if (message != null)
                    showSpinner(context, message);
                else
                    showSpinner(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * @param ctx
         */
        private void showSpinner(Context ctx) {
            try {
                if (isDialogShown()) {
                    dismisssSpinnerDialog();
                }
                dialog = new Dialog(ctx, android.R.style.Theme_Translucent);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(Utilkit.cancelable);
                dialog.setContentView(R.layout.process_spinner);
                dialog.show();
            } catch (Exception e) {
                dialog = null;
            }
        }

        private void showSpinner(Context ctx, String msg) {
            try {
                if (isDialogShown()) {
                    dismisssSpinnerDialog();
                }
                dialog = new Dialog(ctx, android.R.style.Theme_Translucent);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(Utilkit.cancelable);
                dialog.setContentView(R.layout.process_spinner);
                TextView text = dialog.findViewById(R.id.textView1);
                text.setText(msg);
                dialog.show();
            } catch (Exception e) {
                dialog = null;
            }
        }
    };

    public static boolean isDialogShown() {
        return dialog != null && dialog.isShowing();
    }

    public static void dismisssSpinnerDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dialog = null;
        }
    }

    public static void alertRetrofitExceptionDialog(Context context, Throwable t) {
        try {
            if (t instanceof SocketTimeoutException) {
                alertRetrofitExceptionalert("Network Time out. Please try again.", context);
            } else if (t instanceof ConnectException) {
                alertRetrofitExceptionalert("Server Time out. Please try again.", context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void alertRetrofitExceptionalert(String message, final Context context) {
        LayoutInflater inflater;
        View dialogView;
        inflater = LayoutInflater.from(Utilkit.context);
        dialogView = inflater.inflate(R.layout.alert_message_layout, null);
        alertDialog = new AlertDialog.Builder(Utilkit.context).create();
        alertDialog.setView(dialogView);
        TextView erroreMessage = dialogView.findViewById(R.id.textViewDilog);

        Log.i(TAG, "intitializeAlertDialog " + message);
        erroreMessage.setText(message);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.finishAffinity((Activity) context);
                } else
                    ((Activity) context).finish();

            }
        });

        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }

    }

}
