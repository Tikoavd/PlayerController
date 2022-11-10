package com.requestation.playercontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.requestation.playercontroller.Request.UDPSender;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPLay(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextIP);
        String host = editText.getText().toString();
        if (!host.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b")) {
            CharSequence text = "Error: Invalid IP Address";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        editText = (EditText) findViewById(R.id.editTextPort);
        String port = editText.getText().toString();
        if (!port.matches("^(6553[0-5]|655[0-2]\\d|65[0-4]\\d\\d|6[0-4]\\d{3}|[1-5]\\d{4}|[1-9]\\d{0,3}|0)$")) {
            CharSequence text = "Error: Invalid Port Number";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        String uriString = "udp://" + host + ":" + port + "/" + Uri.encode("PLAY");
        Uri uri = Uri.parse(uriString);
        new UDPSender().SendTo(this, uri);
    }

    public void onPause(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextIP);
        String host = editText.getText().toString();
        if (!host.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b")) {
            CharSequence text = "Error: Invalid IP Address";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        editText = (EditText) findViewById(R.id.editTextPort);
        String port = editText.getText().toString();
        if (!port.matches("^(6553[0-5]|655[0-2]\\d|65[0-4]\\d\\d|6[0-4]\\d{3}|[1-5]\\d{4}|[1-9]\\d{0,3}|0)$")) {
            CharSequence text = "Error: Invalid Port Number";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }


        String uriString = "udp://" + host + ":" + port + "/" + Uri.encode("PAUSE");
        Uri uri = Uri.parse(uriString);
        new UDPSender().SendTo(this, uri);
    }

    public void onResume(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextIP);
        String host = editText.getText().toString();
        if (!host.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b")) {
            CharSequence text = "Error: Invalid IP Address";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        editText = (EditText) findViewById(R.id.editTextPort);
        String port = editText.getText().toString();
        if (!port.matches("^(6553[0-5]|655[0-2]\\d|65[0-4]\\d\\d|6[0-4]\\d{3}|[1-5]\\d{4}|[1-9]\\d{0,3}|0)$")) {
            CharSequence text = "Error: Invalid Port Number";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }


        String uriString = "udp://" + host + ":" + port + "/" + Uri.encode("RESUME");
        Uri uri = Uri.parse(uriString);
        new UDPSender().SendTo(this, uri);
    }
}