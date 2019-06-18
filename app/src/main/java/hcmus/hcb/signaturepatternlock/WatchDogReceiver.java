package hcmus.hcb.signaturepatternlock;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public class WatchDogReceiver extends BroadcastReceiver
{
    //public static boolean screenOn = true;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
        {
            Log.i("ACTION_SCREEN_OFF", "================================ACTION_SCREEN_OFF================================");
            Log.i("ACTION_SCREEN_OFF", "");
            Log.i("ACTION_SCREEN_OFF", "================================ACTION_SCREEN_OFF================================");
            //screenOn = false;
            Intent mIntent = new Intent(context, UnlockActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(mIntent);
        }
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
        {
            Log.i("ACTION_SCREEN_ON", "================================ACTION_SCREEN_ON================================");
            Log.i("ACTION_SCREEN_ON", "");
            Log.i("ACTION_SCREEN_ON", "================================ACTION_SCREEN_ON================================");
            Intent intent11 = new Intent(context, UnlockActivity.class);
            intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent11);
            //screenOn = true;
        }
        else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            Log.d("WatchDogReceiver", "\"================================ACTION_BOOT_COMPLETED================================\"");
//            Intent mIntent = new Intent(context, UnlockActivity.class);
//            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mIntent);
        }

    }
}
