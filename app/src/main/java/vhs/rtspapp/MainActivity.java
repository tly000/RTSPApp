package vhs.rtspapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.pedro.rtsp.utils.ConnectCheckerRtsp;
import com.pedro.rtspserver.RtspServerDisplay;

public class MainActivity extends AppCompatActivity {

    RtspServerDisplay server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        server = new RtspServerDisplay(getApplicationContext(), false, new ConnectCheckerRtsp() {
            @Override
            public void onConnectionStartedRtsp(String s) {

            }

            @Override
            public void onConnectionSuccessRtsp() {

            }

            @Override
            public void onConnectionFailedRtsp(String s) {

            }

            @Override
            public void onNewBitrateRtsp(long l) {

            }

            @Override
            public void onDisconnectRtsp() {

            }

            @Override
            public void onAuthErrorRtsp() {

            }

            @Override
            public void onAuthSuccessRtsp() {

            }
        }, 1234);
        startActivityForResult(server.sendIntent(), 123456);
    }

    @SuppressLint("MissingSuperCall")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123456) {
            server.setIntentResult(resultCode, data);
            server.prepareAudio();
            server.prepareVideo();
            server.startStream();
        }
    }

}