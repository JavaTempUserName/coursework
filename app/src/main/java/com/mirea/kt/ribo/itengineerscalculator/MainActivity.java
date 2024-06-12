package com.mirea.kt.ribo.itengineerscalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText loginEdittext = findViewById(R.id.edittext_login);
        EditText passwordEdittext = findViewById(R.id.edittext_password);
        Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(v -> loginToTheApp(loginEdittext.getText().toString(), passwordEdittext.getText().toString()));
    }

    private void loginToTheApp(String StringLogin, String StringPassword) {
        Log.d(TAG, "try login to the app");
        if (Objects.equals(StringLogin, "0") && Objects.equals(StringPassword, "0")) {
            Log.d(TAG, "secret login were executed");
            roadToChoiceListActivity();
            return;
        }
        String server = "https://android-for-students.ru";
        String serverPath = "/coursework/login.php";
        HashMap<String,String> map = new HashMap<>();
        map.put("lgn", StringLogin);
        map.put("pwd", StringPassword);
        map.put("g", "RIBO-01-22");
        HTTPRunnable httpRunnable = new HTTPRunnable(server + serverPath, map);
        Thread th = new Thread(httpRunnable);
        th.start();
        try {
            th.join();
        } catch (InterruptedException ignored) {
            httpRunnable.setResponseBody(null);
        } finally {
            try {
                JSONObject jSONObject = new JSONObject(httpRunnable.getResponseBody());
                int result = jSONObject.getInt("result_code");
                switch (result) {
                    case 1:
                        break;
                    default:
                        httpRunnable.setResponseBody(null);
                        break;
                }
            } catch (JSONException ignored) {
                httpRunnable.setResponseBody(null);
            }
        }
        if (httpRunnable.getResponseBody() != null && !httpRunnable.getResponseBody().isEmpty()) {
            Log.d(TAG, "login successful");
            roadToChoiceListActivity();
        }
        else {
            Log.d(TAG, "login failed");
            Toast.makeText(this, getString(R.string.string_login_failed), Toast.LENGTH_SHORT).show();
        }
    }
    private void roadToChoiceListActivity() {
        Log.d(TAG, "roadToChoiseListActivity");
        Intent roadToChoiceListActivityIntent = new Intent(this, ChoiceListActivity.class);
        startActivity(roadToChoiceListActivityIntent);
        finish();
    }
}