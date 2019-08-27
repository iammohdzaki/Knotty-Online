package com.zaphlabs.knotty_online.ui.customView;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.zaphlabs.knotty_online.R;


/**
 * Class that holds ready to use dialogs
 *
 * @author Mohammad Zaki
 */
public class OptionsDialog {

    private static final String TAG = OptionsDialog.class.getSimpleName();

    private Dialog optionsDialog;

    /*
     * The context which would listen to the Events
     * of the AlertDialog (must be an implementation of
     * Listener)
     */
    private Listener listener;

    /*
     * The activity on which the AlertDialog would be displayed
     */
    private Activity activity;

    /*
     * A unique purpose code to identify the action for which
     * the Dialog is Created
     */
    private int purposeCode;

    // The title for the Options Dialog
    private String title;
    // The message to be conveyed to the User
    private String message;
    // The text for Ok button
    private String positiveButton;
    // The text for Cancel button
    private String negativeButton;

    private Bundle backpack;

    /**
     * Method to showOn a multi-event Dialog
     */
    private OptionsDialog init() {

        try {

            optionsDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
            optionsDialog.setContentView(R.layout.options_dialog);

            Window dialogWindow = optionsDialog.getWindow();
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.dimAmount = 0.6f;

            //dialogWindow.getAttributes().windowAnimations = R.style.CustomDialog;

            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            optionsDialog.setCancelable(true);
            optionsDialog.setCanceledOnTouchOutside(true);

            TextView tvTitle = optionsDialog.findViewById(R.id.tvTitle);
            TextView tvMessage = optionsDialog.findViewById(R.id.tvMessage);

            tvTitle.setText(title);
            tvMessage.setText(Html.fromHtml(message));

            Button btnOk = optionsDialog.findViewById(R.id.btnOk);
            Button btnCancel = optionsDialog.findViewById(R.id.btnCancel);

            btnOk.setText(positiveButton);
            btnCancel.setText(negativeButton);


            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    optionsDialog.dismiss();

                    if (listener != null)
                        listener.performPositiveAction(purposeCode, backpack);
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    optionsDialog.dismiss();

                    if (listener != null)
                        listener.performNegativeAction(purposeCode, backpack);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "OPTIONS DIALOG: " + e.getMessage());
        }

        return this;
    }

    /**
     * Method to init the initialized dialog
     */
    public void show() {

        // Check if activity lives
        //May throw BadTokenException if activity is finished
        try {
            if (activity != null)
                // Check if dialog contains data
                if (optionsDialog != null)
                    // Show the Dialog
                    optionsDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Listener to listen to the events when a main_button
     * on the Options Dialog was pressed
     * <p>
     * Developer: Mohammad Zaki
     * Dated: 19/05/19.
     */
    public interface Listener {

        /**
         * Override this method to perform operations
         * after OK button was pressed
         *
         * @param purpose
         */
        void performPositiveAction(int purpose, Bundle backpack);


        /**
         * Override this method to perform operations
         * after CANCEL button was pressed
         *
         * @param purpose
         */
        void performNegativeAction(int purpose, Bundle backpack);
    }

    /**
     * Class to build the OptionsDialog
     */
    public static class Builder {

        private OptionsDialog optionsDialog = new OptionsDialog();

        /**
         * Constructor to initialize the OptionsDialog
         * (for an Activity)
         *
         * @param activity
         */
        public Builder(Activity activity) {

            optionsDialog.activity = activity;

            if (activity instanceof Listener)
                optionsDialog.listener = (Listener) activity;
        }

        /**
         * Constructor to initialize the OptionsDialog
         * (for a Fragment)
         *
         * @param fragment
         */
        public Builder(Fragment fragment) {

            optionsDialog.activity = fragment.getActivity();

            if (fragment instanceof Listener)
                optionsDialog.listener = (Listener) fragment;
        }

        /**
         * Method to set purpose code to identify the Dialog
         *
         * @param purposeCode
         * @return
         */
        public Builder purpose(int purposeCode) {
            optionsDialog.purposeCode = purposeCode;
            return this;
        }

        /**
         * Method to retrieve the String from a resource
         *
         * @param resourceId
         * @return
         */
        private String getString(int resourceId) {
            return optionsDialog.activity.getString(resourceId);
        }

        /**
         * Method to set a custom listener to listen
         * the events from OptionsDialog
         *
         * @param listener
         * @return
         */
        public Builder listener(Listener listener) {
            optionsDialog.listener = listener;
            return this;
        }

        /**
         * Method to set a backpack containing the data for
         * the intended action
         *
         * @param backpack
         * @return
         */
        public Builder backpack(Bundle backpack) {
            optionsDialog.backpack = backpack;
            return this;
        }

        /**
         * Method to set title to the Title
         *
         * @param resourceId
         * @return
         */
        public Builder title(int resourceId) {
            return title(getString(resourceId));
        }

        /**
         * Method to set title to the Title
         *
         * @param title
         * @return
         */
        public Builder title(String title) {
            optionsDialog.title = title;
            return this;
        }

        /**
         * Method to set message to the Dialog
         *
         * @param resourceId
         * @return
         */
        public Builder message(int resourceId) {
            return message(getString(resourceId));
        }

        /**
         * Method to set message to the Dialog
         *
         * @param message
         * @return
         */
        public Builder message(String message) {
            optionsDialog.message = message;
            return this;
        }

        /**
         * Method to set text to the Positive Button
         *
         * @return
         */
        public Builder positiveButton(int resourcedId) {
            return positiveButton(getString(resourcedId));
        }

        /**
         * Method to set text to the Positive Button
         *
         * @param buttonText
         * @return
         */
        public Builder positiveButton(String buttonText) {
            optionsDialog.positiveButton = buttonText;
            return this;
        }

        /**
         * Method to set Text to the Negative Button
         *
         * @param resourceId
         * @return
         */
        public Builder negativeButton(int resourceId) {
            return negativeButton(getString(resourceId));
        }

        /**
         * Method to set Text to the Negative Button
         *
         * @param cancel
         * @return
         */
        public Builder negativeButton(String cancel) {
            optionsDialog.negativeButton = cancel;
            return this;
        }

        /**
         * Method to init the Options Dialog
         *
         * @return
         */
        public OptionsDialog build() {

            // Check for null and assign default values
            optionsDialog.title = "";
            optionsDialog.message = "Are you sure ?";
            optionsDialog.positiveButton = "Yes";
            optionsDialog.negativeButton = "No";

            return optionsDialog.init();
        }
    }
}
