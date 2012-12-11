package com.thalersoft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.thalersoft.Constants;
import com.thalersoft.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class FormActivity extends RoboActivity {

    @InjectView(R.id.startButton)
    private Button startButton;

    @InjectView(R.id.alertSoundSpinner)
    private Spinner alertSoundSpinner;

    @InjectView(R.id.numDrinks)
    private EditText numberOfDrinks;

    @InjectView(R.id.powerHourHourButton)
    private Button powerHourHourButton;

    @InjectView(R.id.centuryHourButton)
    private Button centuryHourButton;

    @InjectView(R.id.halfPowerHourButton)
    private Button halfPowerHourButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        bindViews();
    }

    protected void bindViews() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.alerts_array,
            android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alertSoundSpinner.setAdapter(adapter);

        startButton.setOnClickListener(new StartGameClickListener());

        halfPowerHourButton.setOnClickListener(new HalfPowerHourClickListener());
        powerHourHourButton.setOnClickListener(new PowerHourHourClickListener());
        centuryHourButton.setOnClickListener(new CenturyHourClickListener());
    }

    public void startGame() {

        Intent intent = new Intent(this, GameActivity.class);
        int numDrinksRequested = Integer.parseInt(numberOfDrinks.getText().toString());
        intent.putExtra(Constants.EXTRA_DRINKS_REQUESTED, numDrinksRequested);

        String selectedSound = alertSoundSpinner.getSelectedItem().toString();
        intent.putExtra(Constants.EXTRA_ALERT_REQUESTED, selectedSound);

        startActivity(intent);
    }

    private class StartGameClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            startGame();
        }
    }

    private class HalfPowerHourClickListener implements View.OnClickListener {

        @Override public void onClick(View v) {
            numberOfDrinks.setText("30");
        }
    }

    private class PowerHourHourClickListener implements View.OnClickListener {

        @Override public void onClick(View v) {
            numberOfDrinks.setText("60");
        }
    }

    private class CenturyHourClickListener implements View.OnClickListener {

        @Override public void onClick(View v) {
            numberOfDrinks.setText("100");
        }
    }

}
