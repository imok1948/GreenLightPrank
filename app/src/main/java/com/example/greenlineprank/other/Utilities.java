package com.example.greenlineprank.other;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Display;
import android.view.WindowManager;

public class Utilities {
  public static final int SINGLE_LINE_WIDTH = 3; //3dp
  public static final int SINGLE_LINE_HEIGHT = 5000; //dp
  public static final int Y_NEGATIVE = -200; //dp

  private static final String PREFS_NAME = "MyPrefs";
  private static final String FIRST_LAUNCH_KEY = "firstLaunch";

  public static boolean isFirstLaunch(Context context) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    boolean isFirstLaunch = sharedPreferences.getBoolean(FIRST_LAUNCH_KEY, true);
    if (isFirstLaunch) {
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putBoolean(FIRST_LAUNCH_KEY, false);
      editor.apply();
    }
    return isFirstLaunch;
  }


  public static void clearSharedPreferences(Context context) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.clear(); // Clears all data
    editor.apply(); // Apply changes asynchronously
  }
}
