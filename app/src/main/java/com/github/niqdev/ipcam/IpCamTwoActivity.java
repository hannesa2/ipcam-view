package com.github.niqdev.ipcam;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.niqdev.ipcam.databinding.ActivityIpcamTwoCameraBinding;
import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;

import androidx.appcompat.app.AppCompatActivity;

public class IpCamTwoActivity extends AppCompatActivity {

    private static final int TIMEOUT = 5;
    private ActivityIpcamTwoCameraBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIpcamTwoCameraBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void loadIpCam1() {
        Mjpeg.newInstance()
                .open("http://50.244.186.65:8081/mjpg/video.mjpg", TIMEOUT)
                .subscribe(
                        inputStream -> {
                            binding.mjpegViewDefault1.setSource(inputStream);
                            binding.mjpegViewDefault1.setDisplayMode(DisplayMode.BEST_FIT);
                            binding.mjpegViewDefault1.showFps(true);
                        },
                        throwable -> {
                            Log.e(getClass().getSimpleName(), "mjpeg error", throwable);
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                        });
    }

    private void loadIpCam2() {
        Mjpeg.newInstance()
                .open("http://iris.not.iac.es/axis-cgi/mjpg/video.cgi?resolution=320x240", TIMEOUT)
                .subscribe(
                        inputStream -> {
                            binding.mjpegViewDefault2.setSource(inputStream);
                            binding.mjpegViewDefault2.setDisplayMode(DisplayMode.BEST_FIT);
                            binding.mjpegViewDefault2.showFps(true);
                        },
                        throwable -> {
                            Log.e(getClass().getSimpleName(), "mjpeg error", throwable);
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIpCam1();
        loadIpCam2();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mjpegViewDefault1.stopPlayback();
        binding.mjpegViewDefault2.stopPlayback();
    }

}
