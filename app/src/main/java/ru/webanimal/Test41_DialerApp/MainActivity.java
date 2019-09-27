package ru.webanimal.Test41_DialerApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // =============================================================================================
    // Fields
    // =============================================================================================

    private boolean askingPermission = false;


    // =============================================================================================
    // Listeners
    // =============================================================================================

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        askingPermission = true;
                        return;
                    }
                }
                call();

            } catch (Exception ignored) {
            }
        }
    };


    // =============================================================================================
    // AppCompatActivity callbacks
    // =============================================================================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setupUX(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        setupUX(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && askingPermission == true) {

                askingPermission = false;
                call();
            }
        }
    }


    // =============================================================================================
    // Private methods
    // =============================================================================================

    private void setupUX(final boolean setEnabled) {
        findViewById(R.id.button).setOnClickListener(setEnabled ? clickListener : null);
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:+79999999999"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        startActivity(intent);
    }
}
