package vn.com.vsc.ptpm.VNPT_DMS.control;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import vn.com.vsc.ptpm.VNPT_DMS.common.onGetSignalStatus;

public class GetSignalStatus {
	private final String TAG = "GetSignalStatus";
	private Context mContext;

	private WifiManager wifiManager;
	private int signalStrength = 0;
	private String networkType ="Unknow";
	private onGetSignalStatus listener;
	private MyPhoneStateListener myListener;
//	private int singalStrengthWifi;
	private int singalStrength3G;
	private TelephonyManager telephonyManager;

	public GetSignalStatus(Context context, onGetSignalStatus listener) {
		this.mContext = context;
		this.listener = listener;
//		new Handler(Looper.getMainLooper()).post(new Runnable() {
//
//			@Override
//			public void run() {
//				initSignalListener();
//
//			}
//		});
		getSignalStrengthAndNetworkType();
		listener.onGetBatteryLevel(getLevelBattery());
		listener.onGetNetworkType(networkType);
		listener.onGetSignalStrengValue(signalStrength);
	}

	public int getLevelBattery() {
		int level = 0;
		try {
			IntentFilter ifilter = new IntentFilter(
					Intent.ACTION_BATTERY_CHANGED);
			Intent batteryStatus = mContext.registerReceiver(null, ifilter);
			level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			Log.i("PIN", "Pin: " + level + "%");
		} catch (Exception e) {
			e.printStackTrace();
			level = -1;
		}
		return level;
	}

	private void getSignalStrengthAndNetworkType() {
		try {
			ConnectivityManager cm = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo Info = cm.getActiveNetworkInfo();
			if (Info == null || !Info.isConnectedOrConnecting()) {
				networkType = "Offline";
			} else {
				int netType = Info.getType();

				if (netType == ConnectivityManager.TYPE_WIFI) {
					Log.i(TAG, "Wifi connection");
					networkType = "Wifi";
					wifiManager = (WifiManager) mContext
							.getSystemService(Context.WIFI_SERVICE);
					signalStrength = wifiManager.getConnectionInfo().getRssi();
					// Need to get wifi strength
				} else if (netType == ConnectivityManager.TYPE_MOBILE) {
					Log.i(TAG, "GPRS/3G connection");
					networkType = "3G";
					// Need to get differentiate between 3G/GPRS
					myListener = new MyPhoneStateListener();
					telephonyManager = (TelephonyManager) mContext
							.getSystemService(Context.TELEPHONY_SERVICE);
					telephonyManager.listen(myListener,
							PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
					stopPhoneStateListener();
				} else {
					networkType = "Unknow";
//					networkType = "-1";
					signalStrength = 0;
				}
			}
		} catch (Exception e) {
			networkType = "Unknow";
			signalStrength = 0;
			e.printStackTrace();
		}
	}

	public void stopPhoneStateListener() {
		if (telephonyManager != null) {
			telephonyManager.listen(myListener, PhoneStateListener.LISTEN_NONE);
			telephonyManager = null;
		}
	}

	private class MyPhoneStateListener extends PhoneStateListener {

		@Override
		public void onSignalStrengthsChanged(SignalStrength signalStrength) {
			super.onSignalStrengthsChanged(signalStrength);

			if (signalStrength.isGsm()) {
				if (signalStrength.getGsmSignalStrength() != 99) {
					singalStrength3G = (2 * signalStrength
							.getGsmSignalStrength()) - 113;
				} else { // signal strength = 0 or 99 not known or not
							// detectable :-> no signal -> UNKNOWN_RSSI
					// singalStrength3G = signalStrength.getGsmSignalStrength();
					singalStrength3G = -120; // add tpq no signal
				}
			} else {
				singalStrength3G = signalStrength.getCdmaDbm();
			}
			Log.i(TAG, "Signal Strength 3G: " + singalStrength3G); // -> dBm
		}
	}
}
