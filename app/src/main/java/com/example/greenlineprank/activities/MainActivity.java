package com.example.greenlineprank.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.greenlineprank.R;
import com.example.greenlineprank.services.OverlayService;


public class MainActivity extends AppCompatActivity {
  private static final int REQUEST_SYSTEM_ALERT_WINDOW = 123;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (!Settings.canDrawOverlays(this)) {
      showOverlayPermissionDialog();
    } else {
      showServiceOverlay();
    }
  }

  private final ActivityResultLauncher<Intent> overlayPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
    if (Settings.canDrawOverlays(this)) {
      Toast.makeText(this, "Overlay permission granted", Toast.LENGTH_SHORT).show();
      showServiceOverlay();
    } else {
      showOverlayPermissionDialog();
    }
  });


  private void showPermissionDeniedDialog() {
    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
  }


  private void showOverlayPermissionDialog() {
    new AlertDialog.Builder(this).setTitle("Overlay Permission Required").setMessage("This app requires permission to draw over other apps. Please grant this permission to continue.").setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        overlayPermissionLauncher.launch(intent);
      }
    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        Toast.makeText(MainActivity.this, "Overlay permission is required for this feature.", Toast.LENGTH_SHORT).show();
      }
    }).setCancelable(false).show();
  }


  private void showServiceOverlay() {
    Toast.makeText(getApplicationContext(), "Starting service showServiceOverlay...", Toast.LENGTH_SHORT).show();
    Intent serviceIntent = new Intent(this, OverlayService.class);
    startService(serviceIntent);
  }
}