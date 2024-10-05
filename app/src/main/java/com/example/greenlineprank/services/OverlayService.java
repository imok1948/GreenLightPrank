package com.example.greenlineprank.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.greenlineprank.R;

public class OverlayService extends Service {

  private WindowManager windowManager;
  private View overlayView;


  public OverlayService() {
  }


  @Override
  public void onCreate() {
    super.onCreate();
    windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

    // Inflate your custom overlay layout
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    overlayView = inflater.inflate(R.layout.overlay_layout, null);

    // Set the layout parameters for the overlay
    WindowManager.LayoutParams params = new WindowManager.LayoutParams(
      WindowManager.LayoutParams.WRAP_CONTENT,
      WindowManager.LayoutParams.WRAP_CONTENT,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Android 8.0+
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
      PixelFormat.TRANSLUCENT
    );

    params.gravity = Gravity.TOP | Gravity.LEFT; // Adjust as needed
    windowManager.addView(overlayView, params);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return  null;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (overlayView != null) windowManager.removeView(overlayView);
  }
}