package vn.com.vsc.ptpm.VNPT_DMS.vn;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate.SendDataLocationService;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate.LocationService;

public class BootReceiver extends BroadcastReceiver {
	public static final int INTERVAL = 1000 * 60;
	public static final int INTERVAL_SEND_DATA = 1000 * 60;

	public void onReceive(Context context, Intent intent) {
		Log.d("LocationService", "starting service...");
		if ((intent.getAction() != null) && (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))) {
			// Tạo file cấu hình, log nếu chưa có.
			mAF.ghilog = true;
			mAF.init4Log(context, mAF.filelog);
			mAF.writelog("Started on boot", context, mAF.filelog);
			try {
				Calendar cal = Calendar.getInstance();
				AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
				Intent serviceIntent = new Intent(context, LocationService.class);
				PendingIntent servicePendingIntent = PendingIntent.getService(context, 0, // integer constant used to identify the service
						serviceIntent, 0); // FLAG to avoid creating a second service if there's already one running
				am.setRepeating(AlarmManager.RTC_WAKEUP,// type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
						cal.getTimeInMillis(), INTERVAL, servicePendingIntent);
				mAF.writelog("Set alarm repeating OK", context, mAF.filelog);
				Log.d("LocationService", "Set repeat ok ");
			} catch (Exception ex) {
				mAF.writelog("Error start on boot: " + ex.toString(), context, mAF.filelog);
			}

			try {
				Calendar cal = Calendar.getInstance();
				AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
				Intent serviceIntent = new Intent(context, SendDataLocationService.class);
				PendingIntent servicePendingIntent = PendingIntent.getService(context, 0, // integer constant used to identify the service
						serviceIntent, 0); // FLAG to avoid creating a second service if there's already one running
				am.setRepeating(AlarmManager.RTC_WAKEUP,// type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
						cal.getTimeInMillis(), INTERVAL_SEND_DATA, servicePendingIntent);
				mAF.writelog("Set alarm repeating OK", context, mAF.filelog);
				Log.d("SendDataLocationService", "Set repeat ok ");
			} catch (Exception ex) {
				mAF.writelog("Error start on boot: " + ex.toString(), context, mAF.filelog);
			}

		}
	}
}
