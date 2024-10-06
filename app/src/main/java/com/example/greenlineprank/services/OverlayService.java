package com.example.greenlineprank.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.greenlineprank.other.Utilities;

public class OverlayService extends Service {

  private WindowManager mWindowManager;
  private View mOverlayView;
  private Handler mHandler;
  private Runnable mShowOverlayRunnable;

  @Override
  public void onCreate() {
    super.onCreate();
    mHandler = new Handler();
  }

  private void printExtras(Intent intent) {
    if (intent != null) {
      Bundle extras = intent.getExtras();
      if (extras != null) {
        // Iterate over all the keys in the bundle and print their values
        for (String key : extras.keySet()) {
          Object value = extras.get(key);
          String s = "Extra key: " + key + ", value: " + value;
          Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
          Log.d("EXTRAS", s);
        }
      } else {
        Toast.makeText(getApplicationContext(), "Extra == Null", Toast.LENGTH_SHORT).show();
      }
    }
  }


  private void showOverlay(int color, int width, int xAxis, int delayInSeconds) {
    // Convert delay from seconds to milliseconds
    int delayInMillis = delayInSeconds * 1000;

    // Create the overlay view
    mOverlayView = new View(this);
    mOverlayView.setBackgroundColor(color);

    // Set up the WindowManager and LayoutParams
    mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    WindowManager.LayoutParams params = new WindowManager.LayoutParams(width, // Width of the overlay
      WindowManager.LayoutParams.MATCH_PARENT, // Height (fill the screen vertically)
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, PixelFormat.TRANSLUCENT);

    params.x = xAxis;
    params.y = Utilities.Y_NEGATIVE;
    params.width = width;
    params.height = Utilities.SINGLE_LINE_HEIGHT;

    mShowOverlayRunnable = () -> mWindowManager.addView(mOverlayView, params);
    mHandler.postDelayed(mShowOverlayRunnable, delayInMillis);
  }


  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
//    printExtras(intent);
    Toast.makeText(getApplicationContext(), "Delay ==> " + intent.getExtras().get(Utilities.EXTRA_LINE_DELAY), Toast.LENGTH_SHORT).show();
    int delayToStart = intent != null ? intent.getIntExtra(Utilities.EXTRA_LINE_DELAY, Utilities.DEFAULT_LINE_DELAY) : Utilities.DEFAULT_LINE_DELAY;
    int lineColor = intent != null ? intent.getIntExtra(Utilities.EXTRA_LINE_COLOR, Utilities.LINE_DEFAULT_COLOR) : Utilities.LINE_DEFAULT_COLOR;
    showOverlay(lineColor, Utilities.SINGLE_LINE_WIDTH, 100, delayToStart);
    return START_NOT_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    // Clean up overlay view and handler
    if (mOverlayView != null && mOverlayView.isAttachedToWindow()) {
      mWindowManager.removeView(mOverlayView);
    }
    if (mHandler != null && mShowOverlayRunnable != null) {
      mHandler.removeCallbacks(mShowOverlayRunnable);
    }
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}