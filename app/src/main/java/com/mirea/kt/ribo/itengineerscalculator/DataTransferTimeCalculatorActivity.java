package com.mirea.kt.ribo.itengineerscalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DataTransferTimeCalculatorActivity extends AppCompatActivity {

    private static final String TAG = "DTTCalculatorActivity: ";
    private TextView resultTextview = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "created");
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_transfer_time_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_data_transfer_time_calculator), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText dataTransferRateEdittext = findViewById(R.id.edittext_data_transfer_rate);
        EditText dataVolumeEdittext = findViewById(R.id.edittext_data_volume);
        Button calculateButton = findViewById(R.id.button_calculate);
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> roadToChoiceListActivity());
        resultTextview = findViewById(R.id.textview_result);
        calculateButton.setOnClickListener(v -> calculateDataTransferTime(dataTransferRateEdittext.getText().toString(), dataVolumeEdittext.getText().toString()));

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

    private void calculateDataTransferTime(String dataTransferRateString, String dataVolumeString) {
        Log.d(TAG, "calculateDataTransferTime");
        if (isItNumber(dataTransferRateString) && isItNumber(dataVolumeString)) {
            String resultString = calculateDataTransferTimeHelper(dataTransferRateString, dataVolumeString);
            resultTextview.setText(resultString);
        }
        else {
            Toast.makeText(this, getString(R.string.string_calculation_failed), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isItNumber(String numberString) {
        if (numberString.isEmpty()) {
            return false;
        }
        try {
            if (Double.parseDouble(numberString) == 0) {
                return false;
            }
            Double.parseDouble(numberString);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    private String calculateDataTransferTimeHelper(String dataTransferRateString, String dataVolumeString) {
        Log.d(TAG, "calculateDataTransferTimeHelper");
        double dataTransferTimeDouble = Double.parseDouble(dataVolumeString) / Double.parseDouble(dataTransferRateString);
        return roundResultString(Double.toString(dataTransferTimeDouble));
    }

    private String roundResultString(String resultString) {
        if (!resultString.contains(".")) {
            return resultString;
        }
        try {
            if (Double.parseDouble(resultString) == Math.round(Double.parseDouble(resultString))) {
                return Long.toString(Math.round((Double.parseDouble(resultString))));
            }
        } catch (NumberFormatException ignored) {
            return resultString;
        }
        String[] resultSplitStringArray = resultString.split("\\.");
        String resultAfterPointString = resultSplitStringArray[1];
        String resultAfterPointNewString = resultAfterPointString;
        char zeroChar = '0';
        for (int i = resultAfterPointString.length()-1; i > -1; i--) {
            if (resultAfterPointString.charAt(i) == zeroChar) {
                if (i != 0) {
                    resultAfterPointNewString = resultAfterPointString.substring(0, i);
                }
            }
            else {
                break;
            }
        }
        resultAfterPointString = resultAfterPointNewString;
        for (int i = resultAfterPointNewString.length()-1; i > 0; i--) {
            if (resultAfterPointNewString.charAt(i) == resultAfterPointNewString.charAt(i-1)) {
                resultAfterPointString = resultAfterPointNewString.substring(0, i);
            }
        }
        if (resultAfterPointString.length() > 7) {
            resultAfterPointString = resultAfterPointString.substring(0, 7);
        }
        return resultSplitStringArray[0] + "." + resultAfterPointString;
    }
}