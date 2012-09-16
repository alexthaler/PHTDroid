package com.thalersoft;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.thalersoft.model.Game;

public class StartGameActivity extends Activity {

    private static final long updateIntervalMillis = 1000L;
    private Handler mHandler = new Handler();
    private Game game;

    private TextView countDownText;
    private TextView numDrinksText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        countDownText = (TextView) findViewById(R.id.countdownText);
        numDrinksText = (TextView) findViewById(R.id.numDrinksText);

        Intent intent = getIntent();
        int numDrinksRequested = intent.getIntExtra(HomeActivity.NUM_DRINKS_REQUESTED, 60);
        String alertRequested = intent.getStringExtra(HomeActivity.ALERT_SOUND_REQUESTED);

        numDrinksText.setText(generateDrinkRemainingText(numDrinksRequested, 0));

        game = new Game(System.currentTimeMillis(), 0L, false, false, 0, numDrinksRequested, alertRequested);

        mHandler.postDelayed(syncTimer, updateIntervalMillis);
    }

    private Runnable syncTimer = new Runnable() {
        public void run() {
            long currentDelta = System.currentTimeMillis() - game.getStartMillis();
            Integer displayValue = Math.round(currentDelta/1000)%60;

            int numDrinksCompleted = (int) Math.floor((currentDelta/1000)/60);

            countDownText.setText(displayValue.toString());
            numDrinksText.setText(generateDrinkRemainingText(game.getNumDrinksGoal(), numDrinksCompleted));

            if(numDrinksCompleted != game.getLastNumDrinksCompleted()) {
                game.setLastNumDrinksCompleted(numDrinksCompleted);
                playAlertSound(getResourceForAlertSound(game.getAlertSound()));
            }

            mHandler.postDelayed(syncTimer, updateIntervalMillis);
        }
    };

    private int getResourceForAlertSound(String alertSound) {
        if(alertSound.toLowerCase().equals("holy drink")) {
            return R.raw.holydrink_alert;
        } else if (alertSound.toLowerCase().equals("dumb drink")) {
            return R.raw.dumbdrink_alert;
        } else {
            throw new IllegalArgumentException("Requested alert sound not found");
        }
    }

    private void playAlertSound(int alertSoundResource) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, alertSoundResource);
        mediaPlayer.start();
    }

    private String generateDrinkRemainingText(int numDrinksGoal, int numDrinksCurrent) {
        StringBuilder sb = new StringBuilder();
        sb.append(numDrinksCurrent);
        sb.append(" of ");
        sb.append(numDrinksGoal);
        sb.append(getString(R.string.drinksCompletedText));
        return sb.toString();
    }

    public void pauseGame(View view) {
        game.setPaused(true);
        Button button = (Button) findViewById(R.id.pauseGame);
        button.setText(getString(R.string.resumeButtonText));
    }

    public void stopGame(View view) {
        mHandler.removeCallbacks(syncTimer);
        Intent intent = new Intent(this, HomeActivity.class);
        game = null;
        startActivity(intent);
    }

}