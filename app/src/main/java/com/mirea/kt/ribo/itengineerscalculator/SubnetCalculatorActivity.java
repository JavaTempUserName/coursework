package com.mirea.kt.ribo.itengineerscalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.renderscript.Matrix4f;
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

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubnetCalculatorActivity extends AppCompatActivity {

    private static final String TAG = "SCalculatorActivity: ";
    private TextView resultTextview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "created");
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subnet_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_subnet_calculator), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText ipEdittext = findViewById(R.id.edittext_ip);
        EditText maskEdittext = findViewById(R.id.edittext_mask);
        Button calculateButton = findViewById(R.id.button_calculate);
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> roadToChoiceListActivity());
        resultTextview = findViewById(R.id.textview_result);
        calculateButton.setOnClickListener(v -> calculateSubnet(ipEdittext.getText().toString(), maskEdittext.getText().toString()));
    }

    private void roadToChoiceListActivity() {
        Log.d(TAG, "roadToChoiceListActivity");
        Intent roadToChoiceListActivityIntent = new Intent(this, ChoiceListActivity.class);
        startActivity(roadToChoiceListActivityIntent);
        finish();
    }

    private void calculateSubnet(String ipString, String maskString) {
        Log.d(TAG, "calculateSubnet");
        if(isItIp(ipString) && isItMask(maskString)) {
            String resultString = calculateSubnetHelper(ipString, maskString);
            resultTextview.setText(resultString);
        }
        else {
            Toast.makeText(this, getString(R.string.string_calculation_failed), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        roadToChoiceListActivity();
    }
    private boolean isItIp(String ipString) {
        Pattern p = Pattern.compile("^(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$");
        Matcher m = p.matcher(ipString);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }


    private boolean isItMask(String maskString) {
        String[] allMaskStringArray = new String[] {
                "0.0.0.0",
                "128.0.0.0",
                "192.0.0.0",
                "224.0.0.0",
                "240.0.0.0",
                "248.0.0.0",
                "252.0.0.0",
                "254.0.0.0",
                "255.0.0.0",
                "255.128.0.0",
                "255.192.0.0",
                "255.224.0.0",
                "255.240.0.0",
                "255.248.0.0",
                "255.252.0.0",
                "255.254.0.0",
                "255.255.0.0",
                "255.255.128.0",
                "255.255.192.0",
                "255.255.224.0",
                "255.255.240.0",
                "255.255.248.0",
                "255.255.252.0",
                "255.255.254.0",
                "255.255.255.0",
                "255.255.255.128",
                "255.255.255.192",
                "255.255.255.224",
                "255.255.255.240",
                "255.255.255.248",
                "255.255.255.252",
                "255.255.255.254",
                "255.255.255.255"
        };
        for (int i = 0; i < allMaskStringArray.length; i++) {
            if(Objects.equals(allMaskStringArray[i], maskString)) {
                return true;
            }
        }
        return false;
    }
    private String calculateSubnetHelper(String ipString, String maskString) {
        String[] ipStringSplitted = ipString.split("\\.");
        String[] maskStringSplitted = maskString.split("\\.");
        String[] ipStringSplittedBinary = new String[ipStringSplitted.length];
        String[] maskStringSplittedBinary = new String[maskStringSplitted.length];
        for (int i = 0; i < ipStringSplitted.length; i ++) {
            ipStringSplittedBinary[i] = String.format("%8s", Integer.toBinaryString(Integer.parseInt(ipStringSplitted[i]))).replaceAll(" ", "0");
            maskStringSplittedBinary[i] = String.format("%8s", Integer.toBinaryString(Integer.parseInt(maskStringSplitted[i]))).replaceAll(" ", "0");
        }
        StringBuilder result = new StringBuilder();
        char nolik = '0';
        for (int i = 0; i < ipStringSplittedBinary.length; i++) {
            if (i != 0) {
                result.append(".");
            }
            for (int j = 0; j < ipStringSplittedBinary[i].length(); j++) {
                if (Objects.equals(ipStringSplittedBinary[i].charAt(j), nolik) | Objects.equals(maskStringSplittedBinary[i].charAt(j), nolik)) {
                    result.append("0");
                }
                else {
                    result.append("1");
                }
            }
        }
        String[] resultListBinary = result.toString().split("\\.");
        String[] resultListInt = new String[resultListBinary.length];
        for (int i = 0; i < resultListInt.length; i++) {
            resultListInt[i] = Integer.toString(Integer.parseInt(resultListBinary[i], 2));
        }
        StringBuilder resultFinal = new StringBuilder();
        for (int i = 0; i < resultListInt.length; i++) {
            if (i != 0) {
                resultFinal.append(".");
            }
            resultFinal.append(resultListInt[i]);
        }
        return resultFinal.toString();
    }
}