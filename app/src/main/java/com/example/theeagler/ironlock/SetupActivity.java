package com.example.theeagler.ironlock;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetupActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        final EditText editText = findViewById(R.id.user_name_et);
        final EditText editText1 = findViewById(R.id.password_et);
        Button save = findViewById(R.id.save);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!Settings.canDrawOverlays(SetupActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 0);
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (editText.getText().length() < 6 || editText1.getText().length() < 6) {
                    editText.setError("You should enter at least 6 characters");
                    editText1.setError("You should enter at least 6 characters");
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_name", editText.getText().toString());
                    editor.putString("password", editText1.getText().toString());
                    editor.putBoolean("first_run", false);
                    editor.apply();


                    Intent intent = new Intent(SetupActivity.this, SampleSetPatternActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });
    }

    private void checkAdminPolicy() {
        ComponentName componentName = new ComponentName(this, AdminPolicyReceiver.class);
        DevicePolicyManager policyManager
                = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (!policyManager.isAdminActive(componentName)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "On Call App Will Lock Phone in Incoming Calls");
            startActivityForResult(intent, 1);
        }
    }
}
