package com.requestation.playercontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText hostEdit;
    EditText portEdit;
    EditText labelEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        hostEdit = findViewById(R.id.ipAddress);
        portEdit = findViewById(R.id.port);
        labelEdit = findViewById(R.id.labelText);

        sharedPreferences = getSharedPreferences(Consts.APP_PREFERENCES, Context.MODE_PRIVATE);
        hostEdit.setText(sharedPreferences.getString(Consts.IP_ADDRESS, "192.168.0.102"));
        portEdit.setText(sharedPreferences.getString(Consts.PORT, "5000"));
        labelEdit.setText(sharedPreferences.getString(Consts.LABEL_TEXT, "Видео"));
    }

    public void onBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onSave(View view) {
        String host = hostEdit.getText().toString();
        if (!host.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b")) {
            CharSequence text = "Error: Invalid IP Address";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        String port = portEdit.getText().toString();
        if (!port.matches("^(6553[0-5]|655[0-2]\\d|65[0-4]\\d\\d|6[0-4]\\d{3}|[1-5]\\d{4}|[1-9]\\d{0,3}|0)$")) {
            CharSequence text = "Error: Invalid Port Number";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Consts.IP_ADDRESS, host);
        editor.putString(Consts.PORT, port);
        editor.putString(Consts.LABEL_TEXT, labelEdit.getText().toString());
        editor.apply();
    }
}