package com.vidalibarraquer.euf2_andres_alex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Visual components assignation

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign visual component to the variable
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        // Let's put a onClick Listener
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
                // Load AddFilm Activity
                Intent intent = new Intent(this, AddFilmActivity.class);
                startActivity(intent);
                break;
        }
    }
}
