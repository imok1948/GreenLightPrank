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

import androidx.appcompat.app.AppCompatActivity;

import com.example.greenlineprank.R;

public class MainActivity extends AppCompatActivity {

  private WindowManager windowManager;
  private View overlayView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    showOverlay();

    Button button = findViewById(R.id.button);
  }

  private void showOverlay() {
    if (overlayView != null) {
      windowManager.removeView(overlayView);
    }
    overlayView = new View(this);
    overlayView.setBackgroundColor(0xAA00FF00);
    WindowManager.LayoutParams params = new WindowManager.LayoutParams(
      WindowManager.LayoutParams.WRAP_CONTENT,
      WindowManager.LayoutParams.WRAP_CONTENT,
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, android.graphics.PixelFormat.TRANSLUCENT);

    params.y = -200;
    params.height = 5000;
    params.width = 3;

    windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    windowManager.addView(overlayView, params);
    Toast.makeText(this, "Overlay Started", Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1001) {
      if (Settings.canDrawOverlays(this)) {
        showOverlay();
      } else {
        Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
      }
    }
  }
}
