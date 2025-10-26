package com.github.niqdev.ipcam;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.niqdev.ipcam.databinding.ActivityIpcamCustomAppearanceBinding;
import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity to show the possibilities of transparent stream background
 * and the actions which need to be done when hiding and showing the
 * stream with transparent background
 */
public class IpCamCustomAppearanceActivity extends AppCompatActivity {

    private static final int TIMEOUT = 5;

    private ActivityIpcamCustomAppearanceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIpcamCustomAppearanceBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setContentView(R.layout.activity_ipcam_custom_appearance);
    }

    private void loadIpCam() {
        binding.layoutProgressWrapper.setVisibility(View.VISIBLE);

        Mjpeg.newInstance()
                .open("http://62.176.195.157:80/mjpg/video.mjpg", TIMEOUT)
                .subscribe(
                        inputStream -> {
                            binding.layoutProgressWrapper.setVisibility(View.GONE);
                            binding.mjpegViewCustomAppearance.setFpsOverlayBackgroundColor(Color.DKGRAY);
                            binding.mjpegViewCustomAppearance.setFpsOverlayTextColor(Color.WHITE);
                            binding.mjpegViewCustomAppearance.setSource(inputStream);
                            binding.mjpegViewCustomAppearance.setDisplayMode(DisplayMode.BEST_FIT);
                            binding.mjpegViewCustomAppearance.showFps(true);
                        },
                        throwable -> {
                            Log.e(getClass().getSimpleName(), "mjpeg error", throwable);
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIpCam();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mjpegViewCustomAppearance.stopPlayback();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_toggleStream:
                if (binding.mjpegViewCustomAppearance.getVisibility() == View.VISIBLE) {
                    binding.mjpegViewCustomAppearance.stopPlayback();
                    binding.mjpegViewCustomAppearance.clearStream();
                    binding.mjpegViewCustomAppearance.resetTransparentBackground();

                    binding.mjpegViewCustomAppearance.setVisibility(View.GONE);

                    item.setIcon(R.drawable.ic_videocam_white_48dp);
                    item.setTitle(getString(R.string.menu_toggleStreamOn));
                } else {
                    binding.mjpegViewCustomAppearance.setTransparentBackground();
                    binding.mjpegViewCustomAppearance.setVisibility(View.VISIBLE);

                    item.setIcon(R.drawable.ic_videocam_off_white_48dp);
                    item.setTitle(getString(R.string.menu_toggleStreamOff));

                    loadIpCam();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_custom_appearance, menu);
        return true;
    }
}
