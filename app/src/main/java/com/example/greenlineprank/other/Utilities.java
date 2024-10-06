package com.example.greenlineprank.other;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Random;

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


  public static int getDisplayWidth(Context context) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics.widthPixels;
  }


  public static int getDisplayFirstX(Context context) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    return -(int) ((displayMetrics.widthPixels) / 2);
  }


  public static int getDisplayLastX(Context context) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    return (int) ((displayMetrics.widthPixels) / 2);
  }

  public static int[] getRandomLocations(int n, int k) {
    // Calculate the bounds based on k and 50 range
    if (n <= 0) {
      return new int[]{};
    }

    int lowerBound = k - 50;
    int upperBound = k + 50;

    int[] randomNumbers = new int[n];
    randomNumbers[0] = k;
    Random random = new Random();

    // Generate n random numbers in the range of lowerBound to upperBound
    for (int i = 1; i < n; i++) {
      randomNumbers[i] = random.nextInt((upperBound - lowerBound) + 1) + lowerBound;
    }
    return randomNumbers;
  }


  public static int getRandomInt(int n) {
    return new Random().nextInt(n) + 1;
  }

  public static int[] getSameColors(int n, int color) {
    int[] colors = new int[n];
    for (int i = 0; i < n; i++) {
      colors[i] = color;
    }
    return colors;
  }

  public static int[] getRandomColors(int n) {
    int[] colors = {0xFF00FF00, 0xFFFF0000, 0xFF00FF00, 0xFF00FF00, 0xFF00FF00, 0xFF0000FF, 0xFF00FF00, 0xFFFF00FF, 0xFF00FF00}; //Increased density for green

    int[] randomColors = new int[n];
    Random random = new Random();

    for (int i = 0; i < n; i++) {
      int randomIndex = random.nextInt(colors.length);
      randomColors[i] = colors[randomIndex];
    }
    return randomColors;
  }

  public static int[] getSameWidth(int n) {
    int[] widths = new int[n];
    for (int i = 0; i < n; i++) {
      widths[i] = SINGLE_LINE_WIDTH;
    }
    return widths;
  }

  public static int[] getRandomWidth(int n) {
    int[] widths = new int[n];
    int minWidth = 1;
    int maxWidth = 2 * SINGLE_LINE_WIDTH;

    Random random = new Random();
    for (int i = 0; i < n; i++) {
      widths[i] = random.nextInt((maxWidth - minWidth) + 1) + minWidth;
    }
    return widths;
  }

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
