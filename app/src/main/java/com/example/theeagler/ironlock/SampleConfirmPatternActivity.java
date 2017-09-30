package com.example.theeagler.ironlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by theeagler on 9/18/17.
 */
public class SampleConfirmPatternActivity extends me.zhanghai.android.patternlock.ConfirmPatternActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            actionBar.hide();
        }
    }

    @Override
    protected boolean isStealthModeEnabled() {
        //  SharedPreferences sharedPreferences = getSharedPreferences("pin_view", MODE_PRIVATE);
        // String pattern_set = (sharedPreferences.getString("pattern_set", ""));
        return false;
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String pattern_set = (sharedPreferences.getString("pattern_set", ""));

        return TextUtils.equals(PatternUtils.patternToSha1String(pattern), pattern_set);

    }

    @Override
    protected void onForgotPassword() {
        Intent intent = new Intent(this, ChangePattern.class);
        startActivity(intent);
        super.onForgotPassword();
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
