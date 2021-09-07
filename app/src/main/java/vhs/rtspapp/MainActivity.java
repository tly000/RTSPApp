package vhs.rtspapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.pedro.rtsp.utils.ConnectCheckerRtsp;
import com.pedro.rtspserver.RtspServerDisplay;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ConnectCheckerRtsp {
    String[] permissions = {
            "android.permission.INTERNET",
            "android.permission.RECORD_AUDIO",
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };
    RtspServerDisplay server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] needed_permissions = Arrays.stream(permissions).filter(p -> checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED).toArray(String[]::new);
        if (needed_permissions.length == 0) {
            onRequestPermissionsResult(234567, needed_permissions, new int[]{});
        } else {
            requestPermissions(needed_permissions, 234567);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Arrays.stream(grantResults).allMatch(i -> i == PackageManager.PERMISSION_GRANTED)) {
            server = new RtspServerDisplay(getApplicationContext(), false, this, 1234);
            startActivityForResult(server.sendIntent(), 123456);
        }
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

    @Override
    public void onAuthErrorRtsp() {

    }

    @Override
    public void onAuthSuccessRtsp() {

    }

    @Override
    public void onConnectionFailedRtsp(String s) {

    }

    @Override
    public void onConnectionStartedRtsp(String s) {

    }

    @Override
    public void onConnectionSuccessRtsp() {

    }

    @Override
    public void onDisconnectRtsp() {

    }

    @Override
    public void onNewBitrateRtsp(long l) {

    }
}