package com.example.greenlineprank.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.greenlineprank.R;
import com.example.greenlineprank.services.OverlayService;

public class MainActivity extends AppCompatActivity {

  private WindowManager windowManager;
  private View overlayView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    showServiceOverlay();

    Button button = findViewById(R.id.button);
  }

  private void showServiceOverlay() {
    Intent serviceIntent = new Intent(this, OverlayService.class);
    startService(serviceIntent);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1001) {
      if (Settings.canDrawOverlays(this)) {
//        showOverlay();
      } else {
        Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
      }
    }
  }
}
