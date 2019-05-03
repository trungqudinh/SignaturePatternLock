package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;

public class Helper extends Activity
{
    private Context context;
    public Helper(Context context)
    {
        this.context = context;
    }
    public void showToast(String text)
    {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        Log.i(">>>> Helper <<<<", "==================== Starting block =================");
        Log.i(">>>> Helper <<<<", text);
        Log.i(">>>> Helper <<<<", "==================== Ending block ===================");
        toast.show();
    }
    public void writeFileOnInternalStorage(String sFileName, String sBody){
        File file = new File(context.getFilesDir(),"mydir");
        if(!file.exists()){
            file.mkdir();
        }

        try{
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
