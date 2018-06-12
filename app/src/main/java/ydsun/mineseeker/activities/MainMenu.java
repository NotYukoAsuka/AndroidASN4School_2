package ydsun.mineseeker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ydsun.mineseeker.R;
import ydsun.mineseeker.models.Mines;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Mines my_game = Mines.getInstance(4,6,6);

        setupPlayButton();
        setupSettingButton();
        setupHelpButton();
    }

    public static Intent makeIntentFromWelcome(WelcomeScreen context) {
        Intent intent = new Intent(context, MainMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private void setupHelpButton(){
        Button skp_btn = (Button) findViewById(R.id.help_btn);
        skp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = HELP.makeIntentForHelp(MainMenu.this);
                startActivity(intent);
            }
        });
    }

    private void setupSettingButton(){
        Button skp_btn = (Button) findViewById(R.id.settings_btn);
        skp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Settings.makeIntentForSettings(MainMenu.this);
                startActivity(intent);
            }
        });
    }

    private void setupPlayButton(){
        Button skp_btn = (Button) findViewById(R.id.start_game_btn);
        skp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GamePlay.makeIntentForPlay(MainMenu.this);
                startActivity(intent);
            }
        });
    }
}
