package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity
{
    private Button btnRegister;
    private Button btnSignature;
    private Button btnExit;
    private Button btnEnableLock;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
    }

    private void initWidgets()
    {
        createWidgetInstances();
        setWidgetsListener();
    }

    private void createWidgetInstances()
    {
        helper = new Helper(getApplicationContext());
        btnRegister = findViewById(R.id.button_register);
        btnSignature = findViewById(R.id.button_signature);
        btnExit = findViewById(R.id.button_exit);
        btnEnableLock = findViewById(R.id.button_enable);
    }

    private void setWidgetsListener()
    {
        if (isMyServiceRunning())
            btnEnableLock.setText("Disable");

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Register clicked", Toast.LENGTH_LONG).show();
                helper.showToast("Register clicked");

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);

                startActivity(intent);

            }
        });
//        btnSignature.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Toast.makeText(getApplicationContext(),"Signature clicked", Toast.LENGTH_LONG).show();
//            }
//        });
        btnExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        btnEnableLock.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                btnEnableLock.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        helper.showToast("button clicked");
                        if (btnEnableLock.getText().equals("Enable"))
                        {
                            startLock();
                            btnEnableLock.setText("Disable");
                            MainActivity.this.finish();
                        }
                        else
                        {
                            helper.showToast("Disable lock");
                            Intent i = new Intent(MainActivity.this, WatchDogService.class);
                            MainActivity.this.stopService(i);
                            btnEnableLock.setText("Enable");
                        }

                    }
                });
            }
        });
    }

    public void button_signature_onClicked(View view)
    {
//        helper.showToast("ahihi");
//        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE);
//        KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
//        //lock.reenableKeyguard();
//        lock.disableKeyguard();
/////        lock.reenableKeyguard();
    }

    private boolean isMyServiceRunning()
    {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (WatchDogService.class.getName().equals(service.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }

    private void startLock()
    {
        try
        {
            helper.showToast("Enabled lock");
            startService(new Intent(this, WatchDogService.class));
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
