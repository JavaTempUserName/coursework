package com.mirea.kt.ribo.itengineerscalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PoECalculatorActivity extends AppCompatActivity {

    private static final String TAG = "PoECalculatorActivity: ";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "created");
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_poe_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_poe_calculator), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        WebView poeCalculatorWebview = findViewById(R.id.webview_poe_calculator);
        poeCalculatorWebview.getSettings().setJavaScriptEnabled(true);
        poeCalculatorWebview.getSettings().setUseWideViewPort(true);
        poeCalculatorWebview.setInitialScale(1);
        poeCalculatorWebview.loadUrl("https://www.linktest.ru/poecalc.html");
        Log.d(TAG, "webview_poe_calculator setted");
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> roadToChoiceListActivity());
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        roadToChoiceListActivity();
    }

    private void roadToChoiceListActivity() {
        Log.d(TAG, "roadToChoiceListActivity");
        Intent roadToChoiceListActivityIntent = new Intent(this, ChoiceListActivity.class);
        startActivity(roadToChoiceListActivityIntent);
        finish();
    }
}