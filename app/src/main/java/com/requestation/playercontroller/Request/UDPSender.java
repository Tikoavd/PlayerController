package com.requestation.playercontroller.Request;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.*;

import static com.requestation.playercontroller.Request.HexHelper.bytesToHex;
import static com.requestation.playercontroller.Request.HexHelper.hexStringToBytes;

import com.requestation.playercontroller.R;

public class UDPSender {
    final Handler toastHandler = new Handler();
    public void SendTo(final Context context, final Uri uri) {

        if (uri == null) return;
        String msg = Uri.decode(uri.getLastPathSegment());
        if(msg == null) return;
        byte[] msgBytes = msg.getBytes();
        if (msg.startsWith("\\0x")) {
            msg = msg.replace("\\0x", "0x");
            msgBytes = msg.getBytes();
        } else if (msg.startsWith("0x")) {
            msg = msg.replace("0x", "");
            if(!msg.matches("[a-fA-F0-9]+")) {
                Toast.makeText(context, "ERROR: Invalid hex values", Toast.LENGTH_LONG).show();
                return;
            }
            msgBytes = hexStringToBytes(msg);
        }

        final byte[] buf = msgBytes;

        String appName = context.getString(R.string.app_name);
        Log.d(appName, new String(msgBytes));
        Log.d(appName, "0x" + bytesToHex(msgBytes));

        new Thread(() -> {
            try {
                InetAddress serverAddress = InetAddress.getByName(uri.getHost());
                //Log.v(getString(R.string.app_name), serverAddress.getHostAddress());
                DatagramSocket socket = new DatagramSocket();
                if (!socket.getBroadcast()) socket.setBroadcast(true);
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddress, uri.getPort());
                socket.send(packet);
                socket.close();
            } catch (final UnknownHostException e) {
                toastHandler.post(() -> Toast.makeText(context, e.toString(),
                        Toast.LENGTH_SHORT).show());
                e.printStackTrace();
            } catch (final SocketException e) {
                toastHandler.post(() -> Toast.makeText(context, e.toString(),
                        Toast.LENGTH_SHORT).show());
                e.printStackTrace();
            } catch (final IOException e) {
                toastHandler.post(() -> Toast.makeText(context, e.toString(),
                        Toast.LENGTH_SHORT).show());
                e.printStackTrace();
            }
        }).start();
    }
}
