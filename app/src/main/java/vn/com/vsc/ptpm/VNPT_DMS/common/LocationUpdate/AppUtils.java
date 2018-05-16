package vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate;

import android.os.Build;

public class AppUtils {

    /**
     * @return true If device has Android Marshmallow or above version
     */
    public static boolean hasM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
