package com.example.greenlineprank.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.greenlineprank.R;
import com.example.greenlineprank.services.OverlayService;

public class MainActivity extends AppCompatActivity {

  Button button = null;

  // Define the ActivityResultLauncher to handle the overlay permission request
  private final ActivityResultLauncher<Intent> overlayPermissionLauncher = registerForActivityResult(
    new ActivityResultContracts.StartActivityForResult(),
    result -> {
      if (Settings.canDrawOverlays(this)) {
        // Permission granted, you can now show the overlay
        Toast.makeText(this, "Overlay permission granted", Toast.LENGTH_SHORT).show();
      } else {
        // Permission not granted, show a message
        Toast.makeText(this, "Overlay permission is required for this feature", Toast.LENGTH_SHORT).show();
      }
    }
  );

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    assignments();

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // Check for overlay permission and ask if not granted
    if (!Settings.canDrawOverlays(this)) {
      showOverlayPermissionDialog();
    }

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (Settings.canDrawOverlays(MainActivity.this)) {
          Toast.makeText(getApplicationContext(), "Started showing overlay...", Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(getApplicationContext(), OverlayService.class);
          startService(intent);
        } else {
          Toast.makeText(getApplicationContext(), "Overlay permission is not granted!", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private void showOverlayPermissionDialog() {
    new AlertDialog.Builder(this)
      .setTitle("Overlay Permission Required")
      .setMessage("This app requires permission to draw over other apps. Please grant this permission to continue.")
      .setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          // Launch the overlay permission settings
          Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + getPackageName()));
          overlayPermissionLauncher.launch(intent);
        }
      })
      .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          // Show a message indicating that the feature cannot work without the permission
          Toast.makeText(MainActivity.this, "Overlay permission is required for this feature.", Toast.LENGTH_SHORT).show();
        }
      })
      .setCancelable(false)
      .show();
  }

  private void assignments() {
    button = findViewById(R.id.button);
  }
}
