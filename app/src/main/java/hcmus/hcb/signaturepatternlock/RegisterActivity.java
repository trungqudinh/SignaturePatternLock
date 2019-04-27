package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends Activity
{

    private DrawView drawingView;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        helper = new Helper(getApplicationContext());

        helper.showToast("Start register activity");

        setContentView(R.layout.activity_register);

        drawingView = (DrawView) findViewById(R.id.drawingViewReg);

        helper.showToast("Start register activity");
    }

    public void button_clear_onClicked(View view)
    {
        drawingView.startNew();
    }

    public void button_next_onClicked(View view)
    {
        helper.showToast("Accept button clicked");
    }
}
