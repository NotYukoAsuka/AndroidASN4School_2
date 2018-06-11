package ydsun.mineseeker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ydsun.mineseeker.R;

public class GamePlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
    }

    public static Intent makeIntentForPlay(MainMenu context){
        Intent intent = new Intent(context, GamePlay.class);
        return intent;
    }
}
