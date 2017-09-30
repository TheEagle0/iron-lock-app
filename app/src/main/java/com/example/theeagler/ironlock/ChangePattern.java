package com.example.theeagler.ironlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePattern extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pattern);
        final EditText confirm_user_name = findViewById(R.id.confirm_name);
        final EditText confirm_password = findViewById(R.id.confirm_pass);
        Button next = findViewById(R.id.next);

        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        final String saved_name = (sharedPreferences.getString("user_name", ""));
        final String saved_password = (sharedPreferences.getString("password", null));


        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = confirm_user_name.getText().toString();
                String password = confirm_password.getText().toString();
                if (name.equals(saved_name) && password.equals(saved_password)) {
                    Intent intent = new Intent(ChangePattern.this, SampleSetPatternActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ChangePattern.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, InnerActivity.class);
        startActivity(intent);
        finish();
    }
}
