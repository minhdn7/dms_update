package vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by MinhDN on 27/6/2017.
 */

public class RestartServiceReceiver extends BroadcastReceiver {
    private static final String TAG = "RestartServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive");
        context.startService(new Intent(context.getApplicationContext(), LocationService.class));
        context.startService(new Intent(context.getApplicationContext(), SendDataLocationService.class));

    }

}
