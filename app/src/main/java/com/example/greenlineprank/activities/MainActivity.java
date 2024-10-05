package com.example.greenlineprank.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.greenlineprank.R;
import com.example.greenlineprank.other.Utilities;
import com.example.greenlineprank.services.OverlayService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

  private Button buttonClearSharedPrefs = null;
  private SwitchCompat switchStartPrank = null;
  private boolean isSwitchTouched = false;
  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle toggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (Utilities.isFirstLaunch(getApplicationContext())) {
      showWelcomeDialog();
    }
    if (!Settings.canDrawOverlays(this)) {
      showOverlayPermissionDialog();
    } else {
//      showServiceOverlay();
    }

    buttonClearSharedPrefs = findViewById(R.id.button_clear_shared_prefs);
    buttonClearSharedPrefs.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Utilities.clearSharedPreferences(getApplicationContext());
      }
    });
    switchStartPrank = findViewById(R.id.switchStartPrank);

    switchStartPrank.setChecked(false);

    switchStartPrank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
          showServiceOverlay();
        } else {
          hideServiceOverlay();
        }
      }
    });

    // Set up Toolbar
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Set up Drawer Layout
    drawerLayout = findViewById(R.id.drawer_layout);
    toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    // Set up NavigationView
    NavigationView navigationView = findViewById(R.id.navigation_view);
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
          Toast.makeText(MainActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_help) {
          Toast.makeText(MainActivity.this, "Help Selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_about) {
          Toast.makeText(MainActivity.this, "About Selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
          Toast.makeText(MainActivity.this, "Share Selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_rate) {
          Toast.makeText(MainActivity.this, "Rate Selected", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawers();
        return true;
      }
    });
  }

  private final ActivityResultLauncher<Intent> overlayPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
    if (Settings.canDrawOverlays(this)) {
      Toast.makeText(this, "Overlay permission granted", Toast.LENGTH_SHORT).show();
//      showServiceOverlay();
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

  private void showWelcomeDialog() {
    Toast.makeText(getApplicationContext(), "This is welcome text...", Toast.LENGTH_SHORT).show();
    //new MaterialAlertDialogBuilder(this).setTitle("Welcome to the Prank Line App!").setMessage(Html.fromHtml("<ol><li><p><strong>Get Ready to Prank</strong>: Welcome to the ultimate prank app that will leave your friends and relatives scratching their heads! With just a simple tap, we'll unleash a mysterious green vertical line that's bound to give them a tiny heart attack. Perfect for those moments when you want to test their reflexes or just have a good laugh! \uD83C\uDF89\uD83D\uDE02</p></li><li><p><strong>Totally Harmless</strong>: Don't worry! This app is as harmless as a butterfly landing on a daisy. It won't cause any damage to your phone, so feel free to prank away without any techy terrors! \uD83E\uDD8B</p></li><li><p><strong>Privacy First</strong>: Your secrets are safe with us! This app will <em>never</em> steal your personal information. So go ahead, prank away with peace of mind! \uD83E\uDD2B\uD83D\uDD12</p></li><li><p><strong>Permission Required</strong>: To pull off this epic prank, we need the \\\"Draw over other apps\\\" permission. It's like giving us the magical power to cast the green line spell! Just grant the permission when prompted, and let the pranking begin! \uD83E\uDE84✨</p></li><li><p><strong>Toggle the Fun</strong>: Once the laughter is over, you can easily turn off the prank by reopening the app and toggling the on/off button. No green lines lingering longer than necessary!</p></li><li><p><strong>Spread the Joy</strong>: Want to really bring the house down? Download this app on your friend's phone too! Just enable the toggle button, and watch the fun unfold! Share the laughter, and may the pranks be ever in your favor! \uD83D\uDE02\uD83C\uDF88</p></li></ol><p style=\\\"text-align: center;\\\"><strong>Enjoy Pranking!</strong> \uD83C\uDF8A</p>")).setPositiveButton("Got It!", (dialog, which) -> dialog.dismiss()).setCancelable(false).show();
  }

  private void showServiceOverlay() {
    Toast.makeText(getApplicationContext(), "Starting service showServiceOverlay...", Toast.LENGTH_SHORT).show();
    Intent serviceIntent = new Intent(this, OverlayService.class);
    startService(serviceIntent);
  }

  private void hideServiceOverlay() {
    Toast.makeText(getApplicationContext(), "Stopping service showServiceOverlay...", Toast.LENGTH_SHORT).show();
    Intent serviceIntent = new Intent(this, OverlayService.class);
    stopService(serviceIntent);
  }


}