package com.example.greenlineprank.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.greenlineprank.R;
import com.example.greenlineprank.other.Utilities;

public class OverlayService extends Service {

  private WindowManager mWindowManager;
  private View mOverlayView;

  private Point getDisplaySize(){
    Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mOverlayView = new View(this);
    mOverlayView.setBackgroundColor(0xAA00FF00);
    mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    WindowManager.LayoutParams params = new WindowManager.LayoutParams(
      WindowManager.LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
      PixelFormat.TRANSLUCENT);
    params.y = Utilities.Y_NEGATIVE;
    params.width = Utilities.SINGLE_LINE_WIDTH;
    params.height = Utilities.SINGLE_LINE_HEIGHT;
    mWindowManager.addView(mOverlayView, params);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mWindowManager.removeView(mOverlayView);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}