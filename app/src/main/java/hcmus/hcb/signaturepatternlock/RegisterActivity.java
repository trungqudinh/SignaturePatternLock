package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends Activity
{

    private DrawView drawView;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        helper = new Helper(getApplicationContext());

        helper.showToast("Start register activity");

        setContentView(R.layout.activity_register);

        drawView = (DrawView) findViewById(R.id.drawingViewReg);

        helper.showToast("Start register activity");
    }

    public void button_clear_onClicked(View view)
    {
        drawView.startNew();
    }

    public void button_next_onClicked(View view)
    {
        helper.showToast("Accept button clicked");
    }

    public void button_debug_onClicked(View view)
    {
        List<String> image = drawView.getBinnaryImage();
        Log.d("DRAWVIEW", "===================================================================");
        Log.d("DRAWVIEW", "Height: " + image.size());
        Log.d("DRAWVIEW", "Width " + image.get(1).length());

        for (String line : image)
        {
            Log.d("DRAWVIEW", line);
        }
        /*for (int i = 1; i < image.size(); i++) {
            Log.i("DRAWVIEW", String.format("%5d %s", i, image.get(i)));
        }*/


//        Log.d("DRAWVIEW", "==========================DEBUG SIZE=========================================");

        /*ArrayList<String> str = new ArrayList<>();
        String tmp = "";
        for(int i=0; i < 1080; i++)
            tmp += Integer.toString(i % 10);
        for(int i=0; i < 1743; i++)
            str.add(tmp);

        //for (String t : str)
        Log.d("DRAWVIEW", "size of str = " + str.size());
        Log.d("DRAWVIEW", "size of item = " + str.get(1).length());
        /*for(int i = 0; i < str.size(); i++)
        {
            Log.d("DRAWVIEW", String.format("%5d %s", i, str.get(i)));
        }*/
//        writeData(image);
        Log.d("DRAWVIEW", "===================================================================");
    }
    public void writeData(List<String> data)
    {
        Log.i("WRITING", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>==========================================================");
        String filename = "signatureData1.txt";
        //String fileContents = "ahihi23985743489";
        FileOutputStream outputStream;
        //Context ctx = getApplicationContext();
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            for(String item : data)
            {
                outputStream.write((item + "\n").getBytes());
            }

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("WRITING", "====================================================<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
