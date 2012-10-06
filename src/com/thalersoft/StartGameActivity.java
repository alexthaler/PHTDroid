package com.thalersoft;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.thalersoft.model.Game;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class StartGameActivity extends RoboActivity {

    private static final long updateIntervalMillis = 1000L;
    private Handler mHandler = new Handler();
    private Game game;

    @InjectView(R.id.countdownText)
    private TextView countDownText;

    @InjectView(R.id.numDrinksText)
    private TextView numDrinksText;

    @InjectView(R.id.drinkProgressBar)
    private ProgressBar drinkProgressBar;

    @InjectExtra(Constants.EXTRA_DRINKS_REQUESTED)
    private int numDrinksRequested = 60;

    @InjectExtra(Constants.EXTRA_ALERT_REQUESTED)
    private String alertRequested;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        numDrinksText.setText(generateDrinkRemainingText(numDrinksRequested, 0));

        game = new Game(System.currentTimeMillis(), 0L, false, false, 0, numDrinksRequested, alertRequested);
        mHandler.postDelayed(syncTimer, updateIntervalMillis);
        drinkProgressBar.setMax(60);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mHandler.removeCallbacks(syncTimer);
    }

    private Runnable syncTimer = new Runnable() {
        public void run() {

            long currentDelta = System.currentTimeMillis() - (game.getStartMillis() + game.getPausedMillis());

            if(!game.isPaused()) {
                Integer displayValue = Math.round(currentDelta / 1000) % 60;

                drinkProgressBar.setProgress(displayValue);

                int numDrinksCompleted = (int) Math.floor((currentDelta / 1000) / 60);

                countDownText.setText(displayValue.toString());
                numDrinksText.setText(generateDrinkRemainingText(game.getNumDrinksGoal(), numDrinksCompleted));

                if(numDrinksCompleted != game.getLastNumDrinksCompleted()) {
                    game.setLastNumDrinksCompleted(numDrinksCompleted);
                    playAlertSound(getResourceForAlertSound(game.getAlertSound()));
                }
            } else {
                game.setPausedMillis(game.getPausedMillis() + 1000);
            }

            if(!game.isStopped()) {
                mHandler.postDelayed(syncTimer, updateIntervalMillis);
            }

        }
    };

    private int getResourceForAlertSound(String alertSound) {
        if(alertSound.toLowerCase().equals("holy drink")) {
            return R.raw.holydrink_alert;
        } else if(alertSound.toLowerCase().equals("dumb drink")) {
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
        sb.append(numDrinksGoal + " ");
        sb.append(getString(R.string.drinksCompletedText));
        return sb.toString();
    }

    public void pauseResumeTimer(View view) {
        game.setPaused(!game.isPaused());
        Button button = (Button) findViewById(R.id.pauseButton);
        if(game.isPaused()) {
            button.setText(getString(R.string.resumeButtonText));
        } else {
            button.setText(getString(R.string.pauseButtonText));
        }
    }

    public void stopGame(View view) {
        mHandler.removeCallbacks(syncTimer);
        finish();
    }
}