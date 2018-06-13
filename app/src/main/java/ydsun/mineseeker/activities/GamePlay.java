package ydsun.mineseeker.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import ydsun.mineseeker.R;
import ydsun.mineseeker.models.Mines;
import ydsun.mineseeker.models.SettingsClass;

public class GamePlay extends AppCompatActivity {

    public static final int CODE_WIN = 114;
    private SettingsClass my_settings;
    private Mines new_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        my_settings = SettingsClass.sGetInstance();
        new_game = Mines.getInstance(my_settings.sGetRowCount(),my_settings.sGetColCount(),my_settings.sGetMineCount());

        populateMines();
        setupMineLeftText();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case CODE_WIN:
                if (resultCode == Activity.RESULT_OK){
                    finish();
                }
        }
    }

    // populate mine buttons
    private void populateMines(){
        TableLayout table = (TableLayout) findViewById(R.id.game_board);
        for (int row = 0; row < my_settings.sGetRowCount(); row++){
            TableRow game_board_row = new TableRow(this);
            game_board_row.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(game_board_row);

            for(int col = 0; col < my_settings.sGetColCount(); col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                Button mine_unit = new Button(this);
                mine_unit.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                mine_unit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mine_button_clicked(FINAL_ROW, FINAL_COL);
                    }
                });

                game_board_row.addView(mine_unit);
            }
        }
    }

    private void mine_button_clicked(int x, int y){
        int position = (x * my_settings.sGetColCount()) + y;
//      Toast.makeText(this, "Button clicked: " + position, Toast.LENGTH_SHORT).show();
        if(new_game.click_mines(position) == new_game.msg_found_mine()){
            setupMineLeftText();
            if(new_game.getMineCount() == 0){
                Intent intent = Congratz.makeIntentForPopCongratz(GamePlay.this);
                startActivityForResult(intent, CODE_WIN);
            }
        }
    }

    // initiate how many mines are left message
    private void setupMineLeftText(){
        TextView display_number_mine_found = (TextView) findViewById(R.id.msg_found_mines);
        String mines_found = "Found " + (my_settings.sGetMineCount() - new_game.getMineCount()) + " of " + my_settings.sGetMineCount() + " Mines.";
        display_number_mine_found.setText(mines_found);
    }

    public static Intent makeIntentForPlay(MainMenu context){
        Intent intent = new Intent(context, GamePlay.class);
        return intent;
    }

}
