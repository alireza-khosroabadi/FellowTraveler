package com.khosroabadi.fellowtraveler.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.khosroabadi.fellowtraveler.R;
import com.khosroabadi.fellowtraveler.client.Client;
import com.khosroabadi.fellowtraveler.tools.ClientRequestType;

public class ClientActivity extends AppCompatActivity {


    TextView response;
    EditText editTextAddress;
    Button buttonConnect, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        editTextAddress = (EditText) findViewById(R.id.addressEditText);
       // editTextPort = (EditText) findViewById(R.id.portEditText);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        buttonClear = (Button) findViewById(R.id.clearButton);
        response = (TextView) findViewById(R.id.responseTextView);

        buttonConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Client myClient = new Client(editTextAddress.getText()
                        .toString(), Integer.parseInt(getString(R.string.PORT)), ClientRequestType.PAIR , getApplicationContext(), getIntent().getStringExtra(getString(R.string.Device_Name_Key)) , null);
                myClient.execute();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                response.setText("");
            }
        });
    }


}
