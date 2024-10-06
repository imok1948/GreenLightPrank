package com.example.greenlineprank.other;

import android.content.Context;
import android.content.SharedPreferences;

public class PrankSettings {
  private static final String PREF_NAME = "PrankSettingsPrefs";
  private static final String KEY_PRANK_STARTED = "prank_started";
  private static final String KEY_TYPE_OF_LINES = "type_of_lines";
  private static final String KEY_NUMBER_OF_LINES = "number_of_lines";
  private static final String KEY_LOCATION_OF_LINES = "location_of_lines";
  private static final String KEY_COLOR_OF_LINES = "color_of_lines";
  private static final String KEY_DELAY_START = "delay_start";

  private final SharedPreferences sharedPreferences;

  // Settings variables
  private int typeOfLines;
  private int numberOfLines;
  private int locationOfLines;
  private int colorOfLines;
  private int delayStart;
  private boolean prankStarted;

  // Arrays for options to show user
  public static final int RANDOM_LINES = -1;

  public static final String[] TYPE_OF_LINES_OPTIONS = {"Entire screen", "Few lines"};
  public static final int[] NUMBER_OF_LINES_OPTIONS = {1, 2, 4, 8, 16, RANDOM_LINES}; // -1 for Random
  public static final String[] LOCATION_OF_LINES_OPTIONS = {"leftmost of screen", "left", "left-center", "center", "right-center", "right", "rightmost of screen"};
  public static final String[] COLOR_OF_LINES_OPTIONS = {"Red", "Green", "Blue", "Purple"};
  public static final int[] DELAY_START_OPTIONS = {0, 10, 15, 20}; // Delay in seconds

  // Constructor
  public PrankSettings(Context context) {
    sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    loadSettings();
  }

  // Save settings to SharedPreferences
  public void saveSettings() {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(KEY_PRANK_STARTED, prankStarted);
    editor.putInt(KEY_TYPE_OF_LINES, typeOfLines);
    editor.putInt(KEY_NUMBER_OF_LINES, numberOfLines);
    editor.putInt(KEY_LOCATION_OF_LINES, locationOfLines);
    editor.putInt(KEY_COLOR_OF_LINES, colorOfLines);
    editor.putInt(KEY_DELAY_START, delayStart);
    editor.apply();
  }

  public void clearSettings(){
    prankStarted = false;
    typeOfLines = 0;
    numberOfLines = 0;
    locationOfLines = 0;
    colorOfLines = 0;
    delayStart = 0;

    saveSettings();
  }
  // Load settings from SharedPreferences
  private void loadSettings() {
    prankStarted = sharedPreferences.getBoolean(KEY_PRANK_STARTED, false);
    typeOfLines = sharedPreferences.getInt(KEY_TYPE_OF_LINES, 0);
    numberOfLines = sharedPreferences.getInt(KEY_NUMBER_OF_LINES, 0);
    locationOfLines = sharedPreferences.getInt(KEY_LOCATION_OF_LINES, 0);
    colorOfLines = sharedPreferences.getInt(KEY_COLOR_OF_LINES, 0);
    delayStart = sharedPreferences.getInt(KEY_DELAY_START, 0);
  }

  public SharedPreferences getSharedPreferences() {
    return sharedPreferences;
  }

  public boolean isPrankStarted() {
    return prankStarted;
  }

  public void setPrankStarted(boolean prankStarted) {
    this.prankStarted = prankStarted;
  }

  public int getTypeOfLines() {
    return typeOfLines;
  }

  public void setTypeOfLines(int typeOfLines) {
    this.typeOfLines = typeOfLines;
  }

  public int getNumberOfLines() {
    return numberOfLines;
  }

  public void setNumberOfLines(int numberOfLines) {
    this.numberOfLines = numberOfLines;
  }

  public int getLocationOfLines() {
    return locationOfLines;
  }

  public void setLocationOfLines(int locationOfLines) {
    this.locationOfLines = locationOfLines;
  }

  public int getColorOfLines() {
    return colorOfLines;
  }

  public void setColorOfLines(int colorOfLines) {
    this.colorOfLines = colorOfLines;
  }

  public int getDelayStart() {
    return delayStart;
  }

  public void setDelayStart(int delayStart) {
    this.delayStart = delayStart;
  }
}
