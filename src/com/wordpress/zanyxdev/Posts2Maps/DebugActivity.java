package com.wordpress.zanyxdev.Posts2Maps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import com.wordpress.zanyxdev.Posts2Maps.tools.ConnectionDetectorTask;


public class DebugActivity extends Activity implements IAsyncResponse {
    // Connection detector class
    ConnectionDetectorTask connectionDetectorTask;
    private Boolean mConnectToInternet;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_activity);
        // creating connection detector class instance
        connectionDetectorTask = new ConnectionDetectorTask(getApplicationContext());
        connectionDetectorTask.iAsyncResponse = this;
        connectionDetectorTask.execute();

        findViewById(R.id.btn_check).setOnClickListener(mGlobal_OnClickListener);
    }

    /**
     * Function to display simple Alert Dialog
     *
     * @param context    - application context
     * @param title_id   - alert dialog title
     * @param message_id - alert message
     * @param status     - success/failure (used to set icon)
     */
    public void showAlertDialog(Context context, int title_id, int message_id, Boolean status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title_id)
                .setMessage(message_id)
                .setIcon((status) ? R.drawable.success : R.drawable.fail);// Setting alert dialog icon
        builder.setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void processFinish(Boolean result) {
        mConnectToInternet = result;
    }

    //Global On click listener for all views
    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btn_check:
                    if (mConnectToInternet) {
                        // Internet Connection is Present
                        showAlertDialog(DebugActivity.this, R.string.title_iconnect,
                                R.string.msg_iconnect, true);
                    } else showAlertDialog(DebugActivity.this, R.string.title_idisonnect,
                            R.string.msg_idisconnect, false);
                    break;
                default:
            }
        }
    };
}
