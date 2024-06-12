package com.mirea.kt.ribo.itengineerscalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChoiceListActivity extends AppCompatActivity {

    private static final String TAG = "ChoiceListActivity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "created");
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choice_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_choice_list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView choiceList = findViewById(R.id.listview_choice_list);
        choiceList.setOnItemClickListener((parent, view, position, id) -> roadToAnotherActivity(position));
    }

    private void roadToAnotherActivity(int position) {
        Log.d(TAG, "roadToAnotherActivity");
        Intent roadToAnotherActivityIntent = new Intent(this, MainActivity.class);
        switch (position) {
            case 0:
                roadToAnotherActivityIntent = new Intent(this, SubnetCalculatorActivity.class);
                break;
            case 1:
                roadToAnotherActivityIntent = new Intent(this, DataTransferTimeCalculatorActivity.class);
                break;
            case 2:
                roadToAnotherActivityIntent = new Intent(this, StrongPasswordGeneratorActivity.class);
                break;
            case 3:
                roadToAnotherActivityIntent = new Intent(this, PoECalculatorActivity.class);
                break;
        }
        startActivity(roadToAnotherActivityIntent);
        finish();
    }
}