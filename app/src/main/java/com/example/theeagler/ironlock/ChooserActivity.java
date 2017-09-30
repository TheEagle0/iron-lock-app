package com.example.theeagler.ironlock;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ChooserActivity extends AppCompatActivity {
    static final int MY_PERMISSIONS_REQUEST_CALL_PHONE_CALL_PHONE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(ChooserActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ChooserActivity.this,
                    Manifest.permission.CALL_PHONE)) {
                Toast.makeText(ChooserActivity.this, "I know you said no, but " +
                        "I'm asking again", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(ChooserActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE},
                    ChooserActivity.MY_PERMISSIONS_REQUEST_CALL_PHONE_CALL_PHONE);
            return;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("first_run", true)) {
            Intent intent = new Intent(this, SetupActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, InnerActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ChooserActivity.this, "Permission was granted!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, SetupActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(ChooserActivity.this, "Permission was denied!",
                            Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}

