package vn.com.vsc.ptpm.VNPT_DMS.config;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import vn.com.vsc.ptpm.VNPT_DMS.common.StringDef;

/**
 * Created by MinhDN on 27/6/2017.
 */

public class NetworkUtil {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = StringDef.WIFI_ENABLED;
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = StringDef.MOBILE_DATA_ENABLED;
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = StringDef.NO_INTERNET;
        }
        return status;
    }

}
