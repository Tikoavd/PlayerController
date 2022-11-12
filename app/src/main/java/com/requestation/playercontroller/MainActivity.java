package com.requestation.playercontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.requestation.playercontroller.Request.UDPSender;

public class MainActivity extends AppCompatActivity {
    boolean playing = false;
    boolean started = false;

    SharedPreferences sharedPreferences;
    String host;
    String port;
    String label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(Consts.APP_PREFERENCES, Context.MODE_PRIVATE);
        host = sharedPreferences.getString(Consts.IP_ADDRESS, "192.168.0.102");
        port = sharedPreferences.getString(Consts.PORT, "5000");
        label = sharedPreferences.getString(Consts.LABEL_TEXT, "Видео");

        ((TextView)findViewById(R.id.textLabel)).setText(label);
    }

    public void onPLay(View view) {
        findViewById(R.id.play).setVisibility(View.INVISIBLE);
        findViewById(R.id.pause).setVisibility(View.VISIBLE);

        if(started && !playing) {
            onResume(view);
            playing = true;
            return;
        }
        started = true;
        if (playing) {
            onPause(view);
            return;
        }
        playing = true;


        String uriString = "udp://" + host + ":" + port + "/" + Uri.encode("PLAY");
        Uri uri = Uri.parse(uriString);
        new UDPSender().SendTo(this, uri);
    }

    public void onPause(View view) {
        playing = false;

        findViewById(R.id.pause).setVisibility(View.INVISIBLE);
        findViewById(R.id.play).setVisibility(View.VISIBLE);

        String uriString = "udp://" + host + ":" + port + "/" + Uri.encode("PAUSE");
        Uri uri = Uri.parse(uriString);
        new UDPSender().SendTo(this, uri);
    }

    public void onResume(View view) {
        String uriString = "udp://" + host + ":" + port + "/" + Uri.encode("RESUME");
        Uri uri = Uri.parse(uriString);
        new UDPSender().SendTo(this, uri);
    }

    public void onRepeat(View view) {
        if (!started || !playing) return;

        findViewById(R.id.play).setVisibility(View.INVISIBLE);
        findViewById(R.id.pause).setVisibility(View.VISIBLE);

        String uriString = "udp://" + host + ":" + port + "/" + Uri.encode("REP");
        Uri uri = Uri.parse(uriString);
        new UDPSender().SendTo(this, uri);
    }

    public void onPrev(View view) {
        String uriString = "udp://" + host + ":" + port + "/" + Uri.encode("PREV");
        Uri uri = Uri.parse(uriString);
        new UDPSender().SendTo(this, uri);
    }

    public void onNext(View view) {
        String uriString = "udp://" + host + ":" + port + "/" + Uri.encode("NEXT");
        Uri uri = Uri.parse(uriString);
        new UDPSender().SendTo(this, uri);
    }

    public void onMinus(View view) {
        String uriString = "udp://" + host + ":" + port + "/" + Uri.encode("VOL-");
        Uri uri = Uri.parse(uriString);
        new UDPSender().SendTo(this, uri);
    }

    public void onPlus(View view) {
        String uriString = "udp://" + host + ":" + port + "/" + Uri.encode("VOL+");
        Uri uri = Uri.parse(uriString);
        new UDPSender().SendTo(this, uri);
    }

    public void onSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}