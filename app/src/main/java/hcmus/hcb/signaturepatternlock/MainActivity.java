package hcmus.hcb.signaturepatternlock;

import android.app.Activity;
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
    }
    private void setWidgetsListener()
    {
        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),"Register clicked", Toast.LENGTH_LONG).show();
                helper.showToast("Register clicked");

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);

                startActivity(intent);

            }
        });
        btnSignature.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),"Signature clicked", Toast.LENGTH_LONG).show();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
