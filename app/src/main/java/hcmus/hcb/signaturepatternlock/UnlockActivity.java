package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import hcmus.hcb.signaturepatternlock.util.KohonenNetwork;

public class UnlockActivity extends Activity
{
    private Helper helper;
    private DrawView drawView;
    private KohonenNetwork network;
    private String[] ids;
    private static boolean isLocking = false;

    private int count;
    private int maxTry;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        makeActivityOverlay();

        setContentView(R.layout.activity_unlock);
        helper = new Helper(getApplicationContext());
        network = new KohonenNetwork();
        count = 0;
        try
        {
            ids = readStringFromFile("myID.txt").split(" ");
            maxTry = Integer.parseInt(readStringFromFile("max_try.txt").trim());
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        drawView = findViewById(R.id.drawview_unlock);

    }

    public void button_ok_onClicked(View view)
    {
        count++;
        if (count > maxTry)
        {
            helper.showToast("INVALID PATTERN");
            UnlockActivity.this.finish();
        }
        else
        {
            //
            // helper.showToast("Try time: " + count);
        }

        helper.showToast("UNLOCKING");
        Log.d("UNLOCK", "- > -> -> -> ->  > -> ->  > -> -> UNLOCKING");
        try
        {
            //Toast.makeText(getApplicationContext(), "Can tao ho cai ", Toast.LENGTH_SHORT).show();
            if (drawView.markedPoints.size() > 0)
            {
                if (recognize() == true)
                {
                    //_isLocking = false;
                    setLocking(false);
                    UnlockActivity.this.finish();
                }
            }
            else
            {
                Log.i("LockActivity", "no catched point");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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



    private boolean recognize() throws IOException
    {
       int id = network.recognize(drawView.nomalizeInput(drawView.markedPoints, 100));
        Toast.makeText(getApplicationContext(), "ID: " + id, Toast.LENGTH_SHORT).show();
        for (String temp : ids)
            if (id == Integer.parseInt(temp.trim()))
            {
                Log.d("RECOGNIZE", "true");
                return true;
            }
        Toast.makeText(getApplicationContext(), "Trying time: " + count + "\nSignature does not match", Toast.LENGTH_SHORT).show();
        drawView.startNew();
        Log.d("RECOGNIZE", "false with " + id);
        return false;
    }

    public void setLocking(boolean isLocking)
    {
        this.isLocking = isLocking;
        Log.i("Locker", "Locking = " + Boolean.toString(this.isLocking));
        Toast.makeText(getApplicationContext(), "Locking = " + Boolean.toString(this.isLocking), Toast.LENGTH_LONG);
    }
    public String readStringFromFile(String filePath)
        throws FileNotFoundException, IOException
    {
        File mFile = new File(filePath);
        StringBuffer stringBuffer = new StringBuffer();

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(
            openFileInput(mFile.getPath())));
        String inputString;
        while ((inputString = inputReader.readLine()) != null)
        {
            stringBuffer.append(inputString + "\n");
        }
        inputReader.close();
        return stringBuffer.toString();
    }
}
