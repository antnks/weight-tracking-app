package com.github.antnks.android.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    int F = 0;
    int M = 1;
    String[] s = new String[2];
    float[] lastw = new float[2];
    int lasts;
    String TAG = "weight";
    NumberPicker picker1;
    NumberPicker picker2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lasts = 0;
        Button but = findViewById(R.id.button);
        if (lasts == F)
            but.setBackgroundColor(Color.argb(255, 255, 192, 203));
        else
            but.setBackgroundColor(Color.argb(255, 0, 0, 255));
        s[F] = "f";
        s[M] = "m";

        picker1 = findViewById(R.id.picker1);
        picker1.setMinValue(60);
        picker1.setMaxValue(90);
        picker1.setValue((int)lastw[F]);
        picker2 = findViewById(R.id.picker2);
        picker2.setMinValue(0);
        picker2.setMaxValue(9);
        picker1.setValue((int)lastw[lasts]);
        Log.d(TAG, String.format("%d %.1f, %.1f %.1f", lasts, lastw[lasts], lastw[0], lastw[1]));
        picker2.setValue(5);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.d(TAG, "onCreate done");
    }

    public void onSubmit(View view)
    {
        lastw[lasts] = picker1.getValue() + picker2.getValue()/10.0f;
        Log.d(TAG, String.format("%d %.1f, %.1f %.1f", lasts, lastw[lasts], lastw[0], lastw[1]));
        String url = String.format("https://server/script.php?s=%s&v=%.1f", s[lasts], lastw[lasts]);
        try
        {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).execute();

            String msg = String.format("%s %.1f", s[lasts], lastw[lasts]);
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
        }
        catch (IOException e)
        {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "onSubmit done");
    }

    public void onSwitch(View view)
    {
        if (lasts == M)
        {
            lasts = F;
            view.setBackgroundColor(Color.argb(255, 255, 192, 203));
        }
        else
        {
            lasts = M;
            view.setBackgroundColor(Color.argb(255, 0, 0, 255));
        }
        picker1.setValue((int)lastw[lasts]);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Last1", picker1.getValue());
        savedInstanceState.putInt("Last2", picker2.getValue());
        savedInstanceState.putInt("LastS", lasts);
        savedInstanceState.putFloat("LastW0", lastw[0]);
        savedInstanceState.putFloat("LastW1", lastw[1]);
        Log.d(TAG,"onSaveInstanceState done");
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        picker1.setValue(savedInstanceState.getInt("Last1"));
        picker2.setValue(savedInstanceState.getInt("Last2"));
        lasts = savedInstanceState.getInt("LastS");
        lastw[0] = savedInstanceState.getFloat("LastW0");
        lastw[1] = savedInstanceState.getFloat("LastW1");

        Button but = findViewById(R.id.button);
        if (lasts == F)
            but.setBackgroundColor(Color.argb(255, 255, 192, 203));
        else
            but.setBackgroundColor(Color.argb(255, 0, 0, 255));

        Log.d(TAG,"onRestoreInstanceState done");
    }


}

