package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
}
