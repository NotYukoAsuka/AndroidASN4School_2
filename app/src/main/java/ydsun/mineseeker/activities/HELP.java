package ydsun.mineseeker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ydsun.mineseeker.R;

public class HELP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public static Intent makeIntentForHelp(MainMenu context){
        Intent intent = new Intent(context, HELP.class);
        return intent;
    }
}
