package com.thalersoft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class HomeActivity extends RoboActivity {

    @InjectView(R.id.startButton)
    private Button startButton;

    @InjectView(R.id.alertSoundSpinner)
    private Spinner alertSoundSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.alerts_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alertSoundSpinner.setAdapter(adapter);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(view);
            }
        });
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        EditText numDrinksInput = (EditText) findViewById(R.id.numDrinks);
        int numDrinksRequested = Integer.parseInt(numDrinksInput.getText().toString());
        intent.putExtra(Constants.EXTRA_DRINKS_REQUESTED, numDrinksRequested);

        Spinner spinner = (Spinner) findViewById(R.id.alertSoundSpinner);
        String selectedSound = spinner.getSelectedItem().toString();
        intent.putExtra(Constants.EXTRA_ALERT_REQUESTED, selectedSound);

        startActivity(intent);
    }
}
