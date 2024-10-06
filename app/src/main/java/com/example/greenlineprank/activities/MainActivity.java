package com.example.greenlineprank.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.example.greenlineprank.other.PrankSettings;
import com.example.greenlineprank.other.Utilities;
import com.example.greenlineprank.services.OverlayService;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

  private Button buttonClearSharedPrefs = null;
  private SwitchCompat switchStartPrank = null;
  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle toggle;

  private EditText selectTypeOfLines, selectNumberOfLines, selectLocationOfLines, selectColorOfLines, selectDelayToStart;
  private PrankSettings prankSettings = null;

  private final ActivityResultLauncher<Intent> overlayPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
    if (Settings.canDrawOverlays(this)) {
      Toast.makeText(this, "Overlay permission granted", Toast.LENGTH_SHORT).show();
//      showServiceOverlay();
    } else {
      showOverlayPermissionDialog();
    }
  });

  private void setAssignments() {
    buttonClearSharedPrefs = findViewById(R.id.button_clear_shared_prefs);
    switchStartPrank = findViewById(R.id.switchStartPrank);
    drawerLayout = findViewById(R.id.drawer_layout);
    selectTypeOfLines = findViewById(R.id.setting_type_of_line);
    selectNumberOfLines = findViewById(R.id.setting_number_of_line);
    selectLocationOfLines = findViewById(R.id.setting_location_of_line);
    selectColorOfLines = findViewById(R.id.setting_color_of_line);
    selectDelayToStart = findViewById(R.id.setting_delay_to_start);
  }

  private void setDefaultValues() {
    switchStartPrank.setChecked(false);

    String typeOfLines = prankSettings.getTypeOfLines() < prankSettings.TYPE_OF_LINES_OPTIONS.length ? prankSettings.TYPE_OF_LINES_OPTIONS[prankSettings.getTypeOfLines()] : "";
    int numberOfLines = prankSettings.getNumberOfLines();
    String locationOfLines = prankSettings.getLocationOfLines() < prankSettings.LOCATION_OF_LINES_OPTIONS.length ? prankSettings.LOCATION_OF_LINES_OPTIONS[prankSettings.getLocationOfLines()] : "";
    String colorOfLines = prankSettings.getColorOfLines() < prankSettings.COLOR_OF_LINES_OPTIONS.length ? prankSettings.COLOR_OF_LINES_OPTIONS[prankSettings.getColorOfLines()] : "";
    int delayToStartPrank = prankSettings.getDelayStart();

    selectTypeOfLines.setText(typeOfLines);
    if (numberOfLines == prankSettings.RANDOM_LINES) {
      selectNumberOfLines.setText("Random lines");
    } else {
      selectNumberOfLines.setText(numberOfLines + " Lines");
    }

    selectLocationOfLines.setText(locationOfLines);
    selectColorOfLines.setText(colorOfLines);
    selectDelayToStart.setText("" + delayToStartPrank);
  }

  private void setDrawerLayout() {
    // Set up Toolbar
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Set up Drawer Layout
    toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();
  }

  private void setupNavigationLayout() {
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
    if (Utilities.isFirstLaunch(getApplicationContext())) {
      Toast.makeText(getApplicationContext(), "This is welcome text...", Toast.LENGTH_SHORT).show();
      //new MaterialAlertDialogBuilder(this).setTitle("Welcome to the Prank Line App!").setMessage(Html.fromHtml("<ol><li><p><strong>Get Ready to Prank</strong>: Welcome to the ultimate prank app that will leave your friends and relatives scratching their heads! With just a simple tap, we'll unleash a mysterious green vertical line that's bound to give them a tiny heart attack. Perfect for those moments when you want to test their reflexes or just have a good laugh! \uD83C\uDF89\uD83D\uDE02</p></li><li><p><strong>Totally Harmless</strong>: Don't worry! This app is as harmless as a butterfly landing on a daisy. It won't cause any damage to your phone, so feel free to prank away without any techy terrors! \uD83E\uDD8B</p></li><li><p><strong>Privacy First</strong>: Your secrets are safe with us! This app will <em>never</em> steal your personal information. So go ahead, prank away with peace of mind! \uD83E\uDD2B\uD83D\uDD12</p></li><li><p><strong>Permission Required</strong>: To pull off this epic prank, we need the \\\"Draw over other apps\\\" permission. It's like giving us the magical power to cast the green line spell! Just grant the permission when prompted, and let the pranking begin! \uD83E\uDE84✨</p></li><li><p><strong>Toggle the Fun</strong>: Once the laughter is over, you can easily turn off the prank by reopening the app and toggling the on/off button. No green lines lingering longer than necessary!</p></li><li><p><strong>Spread the Joy</strong>: Want to really bring the house down? Download this app on your friend's phone too! Just enable the toggle button, and watch the fun unfold! Share the laughter, and may the pranks be ever in your favor! \uD83D\uDE02\uD83C\uDF88</p></li></ol><p style=\\\"text-align: center;\\\"><strong>Enjoy Pranking!</strong> \uD83C\uDF8A</p>")).setPositiveButton("Got It!", (dialog, which) -> dialog.dismiss()).setCancelable(false).show();
    } else {

    }
  }

  private void showServiceOverlay() {
//    Toast.makeText(getApplicationContext(), "Starting service showServiceOverlay...", Toast.LENGTH_SHORT).show();
    Intent serviceIntent = new Intent(this, OverlayService.class);
    int lineType = prankSettings.getTypeOfLines() < prankSettings.SETTING_LINE_TYPE.length ? prankSettings.SETTING_LINE_TYPE[prankSettings.getTypeOfLines()] : prankSettings.SETTING_LINE_TYPE[0];
    int lineNumbers = prankSettings.getNumberOfLines();
    int lineLocation = prankSettings.getLocationOfLines() < prankSettings.SETTING_LINE_LOCATION.length ? prankSettings.SETTING_LINE_LOCATION[prankSettings.getLocationOfLines()] : prankSettings.SETTING_LINE_LOCATION[0];
    int lineColor = prankSettings.getColorOfLines() < prankSettings.SETTING_LINE_COLOR.length ? prankSettings.SETTING_LINE_COLOR[prankSettings.getColorOfLines()] : prankSettings.SETTING_LINE_COLOR[0];
    int delayToStart = prankSettings.getDelayStart() < prankSettings.DELAY_START_OPTIONS.length ? prankSettings.DELAY_START_OPTIONS[prankSettings.getDelayStart()] : prankSettings.DELAY_START_OPTIONS[0];

    serviceIntent.putExtra(Utilities.EXTRA_TYPE_OF_LINE, lineType);
    serviceIntent.putExtra(Utilities.EXTRA_NUMBER_OF_LINES, lineNumbers);
    serviceIntent.putExtra(Utilities.EXTRA_LOCATION_OF_LINE, lineLocation);
    serviceIntent.putExtra(Utilities.EXTRA_LINE_COLOR, lineColor);
    serviceIntent.putExtra(Utilities.EXTRA_LINE_DELAY, delayToStart);
    startService(serviceIntent);
  }

  private void hideServiceOverlay() {
//    Toast.makeText(getApplicationContext(), "Stopping service showServiceOverlay...", Toast.LENGTH_SHORT).show();
    Intent serviceIntent = new Intent(this, OverlayService.class);
    stopService(serviceIntent);
  }

  private void showSelectTypeOfLines(final View v) {
    final String[] array = prankSettings.TYPE_OF_LINES_OPTIONS;
    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
    builder.setTitle("Types of lines");

    builder.setSingleChoiceItems(array, prankSettings.getTypeOfLines(), new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        ((EditText) v).setText(array[i]);
        prankSettings.setTypeOfLines(i);
        dialogInterface.dismiss();
      }
    });
    builder.show();
  }

  private void showSelectNumberOfLines(final View v) {
    final String[] array = new String[prankSettings.NUMBER_OF_LINES_OPTIONS.length];
    for (int index = 0; index < prankSettings.NUMBER_OF_LINES_OPTIONS.length; index++) {
      if (prankSettings.NUMBER_OF_LINES_OPTIONS[index] == PrankSettings.RANDOM_LINES) {
        array[index] = "Random Lines";
      } else {
        array[index] = prankSettings.NUMBER_OF_LINES_OPTIONS[index] + " Lines";
      }
    }

    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
    builder.setTitle("Number of lines");

    builder.setSingleChoiceItems(array, prankSettings.getNumberOfLines(), new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        ((EditText) v).setText(array[i]);
        prankSettings.setNumberOfLines(i);
        dialogInterface.dismiss();
      }
    });
    builder.show();
  }

  private void showSelectLocationOfLines(final View v) {
    final String[] array = prankSettings.LOCATION_OF_LINES_OPTIONS;
    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
    builder.setTitle("Location of lines");

    builder.setSingleChoiceItems(array, prankSettings.getLocationOfLines(), new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        ((EditText) v).setText(array[i]);
        prankSettings.setLocationOfLines(i);
        dialogInterface.dismiss();
      }
    });
    builder.show();
  }

  private void showSelectColorOfLines(final View v) {
    final String[] array = prankSettings.COLOR_OF_LINES_OPTIONS;
    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
    builder.setTitle("Color of lines");

    builder.setSingleChoiceItems(array, prankSettings.getColorOfLines(), new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        ((EditText) v).setText(array[i]);
        prankSettings.setColorOfLines(i);
        dialogInterface.dismiss();
      }
    });
    builder.show();
  }

  private void showSelectDelayToStart(final View v) {
    final String[] array = new String[prankSettings.DELAY_START_OPTIONS.length];
    for (int index = 0; index < prankSettings.DELAY_START_OPTIONS.length; index++) {
      array[index] = prankSettings.DELAY_START_OPTIONS[index] + " Seconds";
    }

    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
    builder.setTitle("Delay to start");

    builder.setSingleChoiceItems(array, prankSettings.getDelayStart(), new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        ((EditText) v).setText(array[i]);
        prankSettings.setDelayStart(i);
        dialogInterface.dismiss();
      }
    });
    builder.show();
  }

  private void setListeners() {
    buttonClearSharedPrefs.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        prankSettings.clearSettings();
        Utilities.clearSharedPreferences(getApplicationContext());
      }
    });
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
    selectTypeOfLines.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showSelectTypeOfLines(view);
      }
    });
    selectNumberOfLines.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showSelectNumberOfLines(view);
      }
    });
    selectLocationOfLines.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showSelectLocationOfLines(view);
      }
    });
    selectColorOfLines.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showSelectColorOfLines(view);
      }
    });
    selectDelayToStart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showSelectDelayToStart(view);
      }
    });
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    prankSettings = new PrankSettings(getApplicationContext());

    showWelcomeDialog();
    if (!Settings.canDrawOverlays(this)) {
      showOverlayPermissionDialog();
    }
    setAssignments();
    setDefaultValues();
    setListeners();
    setDrawerLayout();
    setupNavigationLayout();
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    prankSettings.saveSettings();
  }
}