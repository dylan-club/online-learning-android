package com.nicklaus.niloedu.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import androidx.navigation.Navigation;

import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;

import es.dmoral.toasty.Toasty;

public class ViewUtils {

    public static void showHeaderAndFooter(MainActivity activity) {
        activity.bottomNavigationView.setVisibility(View.VISIBLE);
        activity.homeToolbar.setVisibility(View.VISIBLE);
    }

    public static void hideHeaderAndFooter(MainActivity activity) {
        activity.bottomNavigationView.setVisibility(View.GONE);
        activity.homeToolbar.setVisibility(View.GONE);
    }

    public static void showBackToOriginalFragmentAlertDialog(
            Context context,
            int toastMsgId,
            View view,
            int navigationActionId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.quit_dialog_title);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.quit_dialog_positive_message, (dialog, which) -> Navigation
                .findNavController(view)
                .navigate(navigationActionId));
        builder.setNegativeButton(R.string.quit_dialog_negative_message, (dialog, which) -> Toasty
                .warning(context,toastMsgId).show());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressWarnings("ConstantConditions")
    public static void setHomeToolbarTitle(MainActivity mainActivity, View view){
        mainActivity.homeToolbarTitle
                .setText(Navigation.findNavController(view)
                                .getCurrentDestination().getLabel());
    }
}
