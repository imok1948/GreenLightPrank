package com.example.greenlineprank.other;
import android.content.Context;
import android.content.SharedPreferences;

public class Utilities {
  public static final int SINGLE_LINE_WIDTH = 3; //3dp
  public static final int SINGLE_LINE_HEIGHT = 5000; //dp
  public static final int Y_NEGATIVE = -200; //dp

  private static final String PREFS_NAME = "MyPrefs";
  private static final String FIRST_LAUNCH_KEY = "firstLaunch";

  public enum LineType {
    SINGLE_LINE, FULL_SCREEN
  }

  public static enum LineLocation {
    LEFTMOST, LEFT, LEFT_CENTER, CENTER, RIGHT_CENTER, RIGHT, RIGHTMOST;

    private int value;

    LineLocation() {
    }

    LineLocation(int value) {
      this.value = value;
    }

    public int getValue() {
      return this.value;
    }
  }

  public static enum LineColor {
    GREEN(0xAA00FF00), RED(0xAAFF0000), BLUE(0xFFAA0000);
    private int value;

    LineColor() {
    }

    LineColor(int value) {
      this.value = value;
    }

    public int getValue() {
      return this.value;
    }
  }


  public static final String EXTRA_TYPE_OF_LINE = "EXTRA_TYPE_OF_LINE";
  public static final String EXTRA_NUMBER_OF_LINES = "EXTRA_NUMBER_OF_LINES";
  public static final String EXTRA_LOCATION_OF_LINE = "EXTRA_LOCATION_OF_LINE";
  public static final String EXTRA_COLOR_OF_LINE = "EXTRA_COLOR_OF_LINE";
  public static final String EXTRA_LINE_DELAY = "EXTRA_LINE_DELAY";
  public static final String EXTRA_LINE_COLOR = "EXTRA_LINE_COLOR";

  public static final LineType DEFAULT_LINE_TYPE = LineType.SINGLE_LINE;
  public static final int DEFAULT_NUMBER_OF_LINES = 1;
  public static final LineLocation DEFAULT_LINE_LOCATION = LineLocation.CENTER;
  public static final LineColor DEFAULT_LINE_COLOR = LineColor.BLUE;
  public static final int DEFAULT_LINE_DELAY = 0;
  public static final int LINE_DEFAULT_COLOR = 0xAA00FF00; // Default color: Green


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
