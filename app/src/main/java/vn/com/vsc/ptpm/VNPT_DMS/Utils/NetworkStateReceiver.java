package vn.com.vsc.ptpm.VNPT_DMS.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by ThaoPit on 10/26/2016.
 */

public class NetworkStateReceiver extends BroadcastReceiver {
    //cach khac
    //    @Override
//    public void onReceive(Context context, Intent intent) {
//        // send network state changed
//        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
//            // there is Internet connection
//            EventBus.getDefault().post(new NetworkStateChanged(true));
//        } else {
//            // no Internet connection
//            EventBus.getDefault().post(new NetworkStateChanged(false));
//        }
//    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("app", "Network connectivity change");
        if (intent.getExtras() != null) {
            NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                Log.i("app", "Network " + ni.getTypeName() + " connected");
                EventBus.getDefault().post(new NetworkStateChanged(true));
            } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
                Log.d("app", "There's no network connectivity");
                EventBus.getDefault().post(new NetworkStateChanged(false));
            }
        }
    }
}
