package com.mirea.kt.ribo.itengineerscalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class StrongPasswordGeneratorActivity extends AppCompatActivity {

    private static final String TAG = "SPGeneratorActivity: ";
    private String passwordLengthString = "7";
    TextView resultTextview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "created");
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_strong_password_generator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_strong_password_generator), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner passwordLengthSpinner = findViewById(R.id.spinner_password_length);
        String[] passwordLengthSpinnerListString = new String[] {"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};
        ArrayAdapter<String> passwordLengthSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, passwordLengthSpinnerListString);
        passwordLengthSpinner.setAdapter(passwordLengthSpinnerAdapter);
        passwordLengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                passwordLengthString = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        resultTextview = findViewById(R.id.textview_result);

        Button generateButton = findViewById(R.id.button_generate);
        generateButton.setOnClickListener(v -> generateStrongPassword());
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

    private void generateStrongPassword() {
        Log.d(TAG, "generateStrongPassword");
        String resultString = generateStrongPasswordString(this.passwordLengthString);
        resultTextview.setText(resultString);
    }

    private String generateStrongPasswordString(String passwordLengthString) {
        int passwordLengthInt = Integer.parseInt(passwordLengthString);
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < passwordLengthInt; i++) {
            char c = chars[r.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}