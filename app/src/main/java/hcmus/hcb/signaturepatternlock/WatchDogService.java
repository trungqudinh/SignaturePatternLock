package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class WatchDogService extends Service
{
    public WatchDogService()
    {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    public static WatchDogReceiver mBroadcast = new WatchDogReceiver();
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        Log.d("LOCKER", "Service started");

        disableKeyguard();
        startWatchDog();
        Log.d("LOCKER", "Hope this f*cking good");

        super.onCreate();

    }
    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        unregisterReceiver(mBroadcast);
        super.onDestroy();
    }

    private void disableKeyguard()
    {
        Log.i("WATCH_DOG", "Disable keyGuard");

        KeyguardManager.KeyguardLock k1;
        KeyguardManager km = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
        k1 = km.newKeyguardLock("IN");
        k1.disableKeyguard();

        Log.i("WATCH_DOG", "Disable keyGuard");
    }
    private void startWatchDog()
    {
        Log.i("WATCH_DOG", "Starting");

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mBroadcast, filter);

        Log.i("WATCH_DOG", "Started");
    }
}