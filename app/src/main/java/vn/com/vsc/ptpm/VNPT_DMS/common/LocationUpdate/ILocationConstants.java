package vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate;

public interface ILocationConstants {

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    long UPDATE_INTERVAL_IN_MILLISECONDS = 20 * 1000; //20s

    /**
     * If accuracy is lesser than 100m , discard it
     */
    int ACCURACY_THRESHOLD = 10;     //10m
    int ACCURACY_THRESHOLD_2 = 15;   //15m
    int ACCURACY_THRESHOLD_3 = 20;   //20m
    int ACCURACY_THRESHOLD_4 = 30;   //30m
    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    /***
     * Request code while asking for permissions
     */
    int PERMISSION_ACCESS_LOCATION_CODE = 99;


}
