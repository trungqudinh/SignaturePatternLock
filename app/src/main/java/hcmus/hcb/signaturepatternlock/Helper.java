package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Helper extends Activity
{
    private Context clientContext;

    public Context getClientContext()
    {
        return clientContext;
    }

    public Helper(Context context)
    {
        this.clientContext = context;
    }

    public void showToast(String text)
    {
        Toast toast = Toast.makeText(getClientContext(), text, Toast.LENGTH_LONG);
        Log.i(">>>> Helper <<<<", "==================== Starting block =================");
        Log.i(">>>> Helper <<<<", text);
        Log.i(">>>> Helper <<<<", "==================== Ending block ===================");
        toast.show();
    }

    public void writeFileOnInternalStorage(String sFileName, String sBody)
    {
        File file = new File(getClientContext().getFilesDir(), "mydir");
        if (!file.exists())
        {
            file.mkdir();
        }

        try
        {
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void writeData(String fileName, List<String> data)
    {
        Log.i("WRITING", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>==========================================================");
        File path = new File(getClientContext().getFilesDir(), "signature_data");
        if (!path.exists())
        {
            path.mkdir();
        }
        FileOutputStream outputStream;
        //Context ctx = getApplicationContext();
        try
        {
            //outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream = new FileOutputStream(new File(path, fileName));
            for (String item : data)
            {
                outputStream.write((item + "\n").getBytes());
            }

            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.i("WRITING", "====================================================<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    public String saveToInternalStorage(Bitmap bitmapImage)
    {
        ContextWrapper cw = new ContextWrapper(getClientContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "sample.jpg");

        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public String readStringFromFile(String filePath)
    {
        Log.d("readStringFromFile", "file paht = " + filePath);

        File mFile = new File(filePath);
        if(mFile == null)
        {
            Log.d("readStringFromFile: ", "SHITY NULL");
        }
        else
        {
            Log.d("readStringFromFile: ", "PATH: " + mFile.getPath());
        }
        StringBuffer stringBuffer = new StringBuffer();

        try
        {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(getClientContext().openFileInput(mFile.getPath())));
            Log.d("readStringFromFile: ", "PATH: " + mFile.getPath());
            String inputString;
            while ((inputString = inputReader.readLine()) != null)
            {
                stringBuffer.append(inputString + "\n");
            }
            inputReader.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return stringBuffer.toString();
    }
}