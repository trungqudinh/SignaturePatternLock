package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity
{
    private  Button btnRegister;
    private Button btnSignature;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
    }

    private void initWidgets()
    {
        btnRegister = findViewById(R.id.button_register);
        btnSignature = findViewById(R.id.button_signature);

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),"hello :P", Toast.LENGTH_LONG).show();
            }
        });
        btnSignature.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),"ahihi", Toast.LENGTH_LONG).show();
            }
        });
    }

}
