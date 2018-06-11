package ydsun.mineseeker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ydsun.mineseeker.R;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        setupSkipButton();
    }

    private void setupSkipButton(){
        Button skp_btn = (Button) findViewById(R.id.skip_intro);
        skp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainMenu.makeIntentFromWelcome(WelcomeScreen.this);
                startActivity(intent);
                finish();
            }
        });
    }
}
