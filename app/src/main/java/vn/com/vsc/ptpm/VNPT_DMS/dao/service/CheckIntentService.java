package vn.com.vsc.ptpm.VNPT_DMS.dao.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate.LocationService;
import vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate.SendDataLocationService;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

/**
 * Created by MinhDN on 12/6/2017.
 */

public class CheckIntentService extends Service {
    public static final int SPLASH_SCREEN_DELAY = 5000;//5s
    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            long endTime = System.currentTimeMillis() + 5*1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
//        Toast.makeText(getApplication(), "service create", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Count Down 3 minute = 30 * 6000
        new CountDownTimer(500, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
//                if(!isMyServiceRunning(SendDataLocationService.class)){
//                    getApplication().startService(new Intent(getApplication().getApplicationContext(), SendDataLocationService.class));
//                }
//                if(!isMyServiceRunning(LocationService.class)){
//                    getApplication().startService(new Intent(getApplication().getApplicationContext(), LocationService.class));
//                }
            }
        }.start();


        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
//        Toast.makeText(this, "on binding", Toast.LENGTH_SHORT).show();
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
//        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }



    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
