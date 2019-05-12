package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class UnlockActivity extends Activity
{
    Helper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        helper = new Helper(getApplicationContext());
        setContentView(R.layout.activity_unlock);
    }

    public void button_ok_onClicked(View view)
    {
        helper.showToast("UNLOCKING");
        Log.d("UNLOCK", "- > -> -> -> ->  > -> ->  > -> -> UNLOCKING");
        this.finish();
    }
}
