package vn.com.vsc.ptpm.VNPT_DMS.Utils;

/**
 * Created by ThaoPit on 10/26/2016.
 */

public class NetworkStateChanged {
    private boolean mIsInternetConnected;

    public NetworkStateChanged(boolean isInternetConnected) {
        mIsInternetConnected = isInternetConnected;
    }

    public boolean isInternetConnected() {
        return mIsInternetConnected;
    }
}
