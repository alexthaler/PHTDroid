package com.thalersoft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class HomeActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    public static final String NUM_DRINKS_REQUESTED = "com.thalersoft.NUMDRINKSREQUESTED";
    public static final String ALERT_SOUND_REQUESTED = "com.thalersoft.ALERTSOUNDREQUESTED";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Spinner spinner = (Spinner) findViewById(R.id.alertSoundSpinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.alerts_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, StartGameActivity.class);
        EditText numDrinksInput = (EditText) findViewById(R.id.numDrinks);
        int numDrinksRequested = Integer.parseInt(numDrinksInput.getText().toString());
        intent.putExtra(NUM_DRINKS_REQUESTED, numDrinksRequested);

        Spinner spinner = (Spinner) findViewById(R.id.alertSoundSpinner);
        String selectedSound = spinner.getSelectedItem().toString();
        intent.putExtra(ALERT_SOUND_REQUESTED, selectedSound);

        startActivity(intent);
    }

}
