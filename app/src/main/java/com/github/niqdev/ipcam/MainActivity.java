package com.github.niqdev.ipcam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;

import com.github.niqdev.ipcam.databinding.ActivityMainBinding;
import com.github.niqdev.ipcam.settings.SettingsActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // load default values first time
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        verifySettings();
    }

    private void verifySettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (TextUtils.isEmpty(prefs.getString(SettingsActivity.PREF_IPCAM_URL, ""))) {
            binding.buttonDefault.setEnabled(false);
        }

        // TODO disabled
        binding.buttonNative.setEnabled(false);

        binding.buttonDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IpCamDefaultActivity.class));
            }
        });
        binding.buttonTwoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IpCamTwoActivity.class));
            }
        });
        binding.buttonSnapshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IpCamSnapshotActivity.class));
            }
        });
        binding.buttonNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IpCamNativeActivity.class));
            }
        });
        binding.buttonCustomAppearance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IpCamCustomAppearanceActivity.class));
            }
        });
        binding.buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
    }
}
