package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import hcmus.hcb.signaturepatternlock.util.KohonenNetwork;

public class RegisterActivity extends Activity
{

    private DrawView drawView;
    private Helper helper;
    private KohonenNetwork mNetwork;
    private int count;
    private String temp = "";
    long timeOffset = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        helper = new Helper(getApplicationContext());

        helper.showToast("Start register activity");

        setContentView(R.layout.activity_register);

        drawView = (DrawView) findViewById(R.id.drawingViewReg);

        helper.showToast("Start register activity");

        mNetwork = new KohonenNetwork();
        count = 0;
    }

    public void button_clear_onClicked(View view)
    {
        drawView.startNew();
    }

    public void button_next_onClicked(View view)
    {
        helper.showToast("Accept button clicked");
        try
        {
            int k = mNetwork.recognize(drawView.nomalizeInput(drawView.markedPoints, 100));
            temp += k + " ";
            count++;
            if (5 - count > 0)
            {
//            Toast.makeText(
//                getApplicationContext(),
//                "ID = " + k + "\nSign more " + (5 - count) + " time!",
//                Toast.LENGTH_LONG).show();
                //txtReg.setText("ID = " + k + "\nSign more " + (5 - count) + " time!");
                helper.showToast("ID = " + k + "\nSign more " + (5 - count) + " time!");
                drawView.startNew();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Done!",
                    Toast.LENGTH_SHORT).show();
                ContextWrapper ctw = new ContextWrapper(this);
                OutputStream os = ctw.openFileOutput("myID.txt",
                    Context.MODE_PRIVATE);
                os.write(temp.trim().getBytes());
                os.flush();
                os.close();
                temp = "";
                RegisterActivity.this.finish();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private List<List<Integer>> getCurrentBinaryImage(double scalingPercent)
    {
        return drawView.getBinnaryImage(scalingPercent);
    }

    private List<List<Integer>> getCurrentBinaryImage()
    {
        return getCurrentBinaryImage(1);
    }

    public void logImage(List<List<Integer>> image, char backgroundCharacter, char foregroundCharacter)
    {
        Log.d("LOG_IMAGE", "===================================================================");
        Log.d("LOG_IMAGE", "Height: " + image.size());
        Log.d("LOG_IMAGE", "Width " + image.get(0).size());


        for (List<Integer> line : image)
        {
            String lineAsString = "";
            for (int value : line)
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
        }
        else
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

        for (List<Integer> lineSrc : srcMatrix)
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