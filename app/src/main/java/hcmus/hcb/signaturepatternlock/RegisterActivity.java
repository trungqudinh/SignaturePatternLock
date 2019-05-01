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
        logImage(false);
    }

    public void writeData(List<String> data)
    {
        Log.i("WRITING", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>==========================================================");
        String filename = "signatureData1.txt";
        //String fileContents = "ahihi23985743489";
        FileOutputStream outputStream;
        //Context ctx = getApplicationContext();
        try
        {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            for (String item : data)
            {
                outputStream.write((item + "\n").getBytes());
            }

            outputStream.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.i("WRITING", "====================================================<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    public void logImage(List<List<Integer>> image, char backgroundCharacter, char foregroundCharacter)
    {
        Log.d("LOG_IMAGE", "===================================================================");
        Log.d("LOG_IMAGE", "Height: " + image.size());
        Log.d("LOG_IMAGE", "Width " + image.get(0).size());


        for (List<Integer> line : image)
        {
            String lineAsString = "";
            for(int value : line)
            {
                if (value == 0)
                {
                    lineAsString += backgroundCharacter;
                }
                else
                {
                    lineAsString += foregroundCharacter;
                }
            }
            Log.d("LOG_IMAGE", lineAsString);
        }

        Log.d("LOG_IMAGE", "===================================================================");
    }
    public void logImage(List<List<Integer>> image)
    {
        logImage(image, ' ', '1');
    }
    public void logImage(boolean portraitView)
    {

        List<List<Integer>> image = drawView.getBinnaryImage(0.1);
        if (portraitView)
        {
            logImage(image);
        } else
        {
            List<List<Integer>> landscapeImage = getRotateMatrix(image);
            logImage(landscapeImage);
        }
    }

    public List<List<Integer>> getRotateMatrix(List<List<Integer>> srcMatrix)
    {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < srcMatrix.get(0).size(); i++)
        {
            res.add(new ArrayList<Integer>());
        }

        for(List<Integer> lineSrc : srcMatrix)
        {
            int j = srcMatrix.get(0).size() - 1;
            for (List<Integer> lineDst : res)
            {
                lineDst.add(lineSrc.get(j));
                j--;
            }

        }
        return res;
    }

    public void logImage()
    {
        logImage(true);
    }
}