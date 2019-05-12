package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class UnlockActivity extends Activity
{
    Helper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        helper = new Helper(getApplicationContext());

        makeActivityOverlay();

        setContentView(R.layout.activity_unlock);
    }

    public void button_ok_onClicked(View view)
    {
        helper.showToast("UNLOCKING");
        Log.d("UNLOCK", "- > -> -> -> ->  > -> ->  > -> -> UNLOCKING");
        this.finish();
    }
    private void makeActivityOverlay()
    {
        this.getWindow().addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
