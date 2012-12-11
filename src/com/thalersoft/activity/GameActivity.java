package com.thalersoft.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.thalersoft.Constants;
import com.thalersoft.R;
import com.thalersoft.model.Game;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class GameActivity extends RoboActivity {

    private static final long updateIntervalMillis = 1000L;
    private Handler mHandler = new Handler();
    private Game game;

    @InjectView(R.id.pauseResumeButton)
    private Button pauseResumeButton;

    @InjectView(R.id.stopButton)
    private Button stopButton;

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


        game = new Game(System.currentTimeMillis(), 0L, false, false, 0, numDrinksRequested, alertRequested);
        mHandler.postDelayed(syncTimer, updateIntervalMillis);

        bindViews();
    }

    private void bindViews() {
        numDrinksText.setText(generateDrinkRemainingText(numDrinksRequested, 0));
        drinkProgressBar.setMax(60);

        pauseResumeButton.setOnClickListener(new PauseResumeClickListener());
        stopButton.setOnClickListener(new StopClickListener());
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
                Integer displayValue = 1 + Math.round(currentDelta / 1000) % 60;

                drinkProgressBar.setProgress(displayValue);

                int numDrinksCompleted = (int) Math.floor((currentDelta / 1000) / 60);

                countDownText.setText(displayValue.toString());
                numDrinksText.setText(generateDrinkRemainingText(game.getNumDrinksGoal(), numDrinksCompleted));

                if(numDrinksCompleted != game.getLastNumDrinksCompleted()) {
                    game.setLastNumDrinksCompleted(numDrinksCompleted);
                    playAlertSound(getResourceForAlertSound(game.getAlertSound()));

                    if(numDrinksCompleted == game.getNumDrinksGoal()) {
                        game.setStopped(true);
                        mHandler.removeCallbacks(syncTimer);
                        finish();
                    }
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
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
            MediaPlayer mediaPlayer = MediaPlayer.create(this, alertSoundResource);
            mediaPlayer.start();
        } else if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(400);
        }
    }

    private String generateDrinkRemainingText(int numDrinksGoal, int numDrinksCurrent) {
        StringBuilder sb = new StringBuilder();
        sb.append(numDrinksCurrent);
        sb.append(" of ");
        sb.append(numDrinksGoal + " ");
        sb.append(getString(R.string.drinksCompletedText));
        return sb.toString();
    }

    private class PauseResumeClickListener implements View.OnClickListener {

        @Override public void onClick(View v) {
            game.setPaused(!game.isPaused());
            if(game.isPaused()) {
                pauseResumeButton.setText(getString(R.string.resumeButtonText));
            } else {
                pauseResumeButton.setText(getString(R.string.pauseButtonText));
            }
        }
    }

    private class StopClickListener implements View.OnClickListener {

        @Override public void onClick(View v) {
            mHandler.removeCallbacks(syncTimer);
            finish();
        }
    }

}