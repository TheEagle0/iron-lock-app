package com.example.theeagler.ironlock;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

import static android.content.Context.MODE_PRIVATE;
import static android.telephony.TelephonyManager.EXTRA_STATE_RINGING;

public class Phone_Receiver extends BroadcastReceiver implements View.OnClickListener {

    public static WindowManager windowManager;
    public View passwordView;
    public static boolean isCallLocked;
    public EditText ed_Password;
    public String saved_password;
    public String enteredPassword;

    private void hidePassword() {
        isCallLocked = false;
        windowManager.removeViewImmediate(passwordView);
        passwordView = null;
    }

    //public static String preState= TelephonyManager.EXTRA_STATE_IDLE;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            final Bundle bundle = intent.getExtras();
            if (bundle != null) {
                final String state = bundle.getString(TelephonyManager.EXTRA_STATE);
                if (state != null)
                    if (state.equals(EXTRA_STATE_RINGING) && passwordView == null) {
                        ShowWindowPatternScreen(context);
                    } else {
                        if (isCallLocked) {
                            hidePassword();
                        }
                    }
            }
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", MODE_PRIVATE);
        saved_password = (sharedPreferences.getString("password", null));
    }

    @SuppressLint("InflateParams")
    private void ShowWindowPinScreen(Context context) {
        isCallLocked = true;
        passwordView = LayoutInflater.from(context).inflate(R.layout.pin_view, null);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params =
                new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT
                        , WindowManager.LayoutParams.MATCH_PARENT
                        , Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                        ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.
                        LayoutParams.TYPE_SYSTEM_ERROR | WindowManager.LayoutParams.TYPE_PHONE
                        , WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_DIM_BEHIND
                        , PixelFormat.TRANSLUCENT);
        params.dimAmount = 1f;
        windowManager.addView(passwordView, params);
        ed_Password = passwordView.findViewById(R.id.ed_password);
        passwordView.findViewById(R.id.btn_number_0).setOnClickListener(this);
        passwordView.findViewById(R.id.btn_number_1).setOnClickListener(this);
        passwordView.findViewById(R.id.btn_number_2).setOnClickListener(this);
        passwordView.findViewById(R.id.btn_number_3).setOnClickListener(this);
        passwordView.findViewById(R.id.btn_number_4).setOnClickListener(this);
        passwordView.findViewById(R.id.btn_number_5).setOnClickListener(this);
        passwordView.findViewById(R.id.btn_number_6).setOnClickListener(this);
        passwordView.findViewById(R.id.btn_number_7).setOnClickListener(this);
        passwordView.findViewById(R.id.btn_number_8).setOnClickListener(this);
        passwordView.findViewById(R.id.btn_number_9).setOnClickListener(this);
        passwordView.findViewById(R.id.btn_clear_all).setOnClickListener(this);
        passwordView.findViewById(R.id.iv_backspace).setOnClickListener(this);
    }

    @SuppressLint("InflateParams")
    private void ShowWindowPatternScreen(final Context context) {
        isCallLocked = true;
        passwordView = LayoutInflater.from(context).inflate(R.layout.pattern_view, null);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params =
                new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT
                        , WindowManager.LayoutParams.MATCH_PARENT
                        , Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                        ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.
                        LayoutParams.TYPE_SYSTEM_ERROR | WindowManager.LayoutParams.TYPE_PHONE
                        , WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_DIM_BEHIND
                        , PixelFormat.TRANSLUCENT);
        params.dimAmount = 1f;
        windowManager.addView(passwordView, params);
        PatternView patternView = passwordView.findViewById(R.id.pv);
        patternView.setOnPatternListener(new PatternView.OnPatternListener() {
            @Override
            public void onPatternStart() {

            }

            @Override
            public void onPatternCleared() {

            }

            @Override
            public void onPatternCellAdded(List<PatternView.Cell> pattern) {

            }

            @Override
            public void onPatternDetected(List<PatternView.Cell> pattern) {
                String patternSha1 = PatternUtils.patternToSha1String(pattern);
                SharedPreferences sharedPreferences = context.getSharedPreferences("data", MODE_PRIVATE);
                if (patternSha1.equals(sharedPreferences.getString("pattern_set", ""))) {
                    hidePassword();
                }
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_number_0:
                ed_Password.append("0");
                break;
            case R.id.btn_number_1:
                ed_Password.append("1");
                break;
            case R.id.btn_number_2:
                ed_Password.append("2");
                break;
            case R.id.btn_number_3:
                ed_Password.append("3");
                break;
            case R.id.btn_number_4:
                ed_Password.append("4");
                break;
            case R.id.btn_number_5:
                ed_Password.append("5");
                break;
            case R.id.btn_number_6:
                ed_Password.append("6");
                break;
            case R.id.btn_number_7:
                ed_Password.append("7");
                break;
            case R.id.btn_number_8:
                ed_Password.append("8");
                break;
            case R.id.btn_number_9:
                ed_Password.append("9");
                break;
            case R.id.btn_clear_all:
                ed_Password.setText("");
                break;
            case R.id.iv_backspace:
                String password = ed_Password.getText().toString();
                if (password.length() > 0) {
                    password = password.substring(0, password.length() - 1);
                    ed_Password.setText(password);
                }
                break;
        }
        CheckPassword();
    }

    private void CheckPassword() {
        enteredPassword = ed_Password.getText().toString();
        if (saved_password.length() == enteredPassword.length()) {
            if (saved_password.equals(enteredPassword)) {
                hidePassword();
            } else {
                ed_Password.setError("Wrong Password");
                ed_Password.setText("");
            }
        }
    }
}