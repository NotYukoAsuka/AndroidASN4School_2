package ydsun.mineseeker.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

    Button mine_buttons[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        my_settings = SettingsClass.sGetInstance();
        new_game = Mines.getInstance(my_settings.sGetRowCount(),my_settings.sGetColCount(),my_settings.sGetMineCount());
        mine_buttons = new Button[my_settings.sGetRowCount()][my_settings.sGetColCount()];

        populateMines();
        setupMineLeftText();
        setupScanText();
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
                String s_p_a_c_e_d = " ";
                mine_unit.setText(s_p_a_c_e_d);

                // make text not clip on small buttons...
                mine_unit.setPadding(0,0,0,0);

                mine_unit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mine_button_clicked(FINAL_ROW, FINAL_COL);
                    }
                });

                game_board_row.addView(mine_unit);
                mine_buttons[row][col] = mine_unit;
            }
        }
    }

    private void mine_button_clicked(int x, int y){
        int position = (x * my_settings.sGetColCount()) + y;
        Button this_mine = mine_buttons[x][y];
        int clicked = new_game.click_mines(position);
//      Toast.makeText(this, "Button clicked: " + position, Toast.LENGTH_SHORT).show();
        if(clicked == new_game.msg_found_mine()){
            setupMineLeftText();
            for(int i = 0; i < my_settings.sGetRowCount(); i++){
                for(int j = 0; j < my_settings.sGetColCount(); j++){
                    Button current_btn = mine_buttons[i][j];
                    if(current_btn.getText() != " "){
                        String mine_in_row_col = "" + new_game.getScannedMineCRCount((i * my_settings.sGetColCount()) + j);
                        current_btn.setText(mine_in_row_col);
                    }
                }
            }
            // lock Button sizes:
            lockButtonSizes();

            this_mine.setBackgroundResource(R.mipmap.monkas_round);
            //pls dont scale image :(
//            int newW = this_mine.getWidth();
//            int newH = this_mine.getHeight();
//            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.monkas);
//            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newW, newH, true);
//            Resources resource = getResources();
//            this_mine.setBackground(new BitmapDrawable(resource, scaledBitmap));

            // if the last mine was found
            if(new_game.getMineCount() == 0) {
                Intent intent = Congratz.makeIntentForPopCongratz(GamePlay.this);
                startActivityForResult(intent, CODE_WIN);
            }
        }
        else if(clicked != new_game.msg_already_scanned() && new_game.getMineCount()!=0){
            String row_col_mines = "" + clicked;
            this_mine.setText(row_col_mines);
            setupScanText();
        }
    }

    private void lockButtonSizes(){
        for (int row = 0; row < my_settings.sGetRowCount(); row++){
            for(int col = 0; col < my_settings.sGetColCount(); col++){
                Button btn = mine_buttons[row][col];

                int width = btn.getWidth();
                btn.setMinWidth(width);
                btn.setMaxWidth(width);

                int height = btn.getHeight();
                btn.setMinHeight(height);
                btn.setMaxHeight(height);
            }
        }
    }

    // initiate how many mines are left message
    private void setupMineLeftText(){
        TextView display_number_mine_found = (TextView) findViewById(R.id.msg_found_mines);
        String mines_found = "Found " + (my_settings.sGetMineCount() - new_game.getMineCount()) + " of " + my_settings.sGetMineCount() + " Mines.";
        display_number_mine_found.setText(mines_found);
    }

    // initiate how many scans used message
    private void setupScanText(){
        TextView display_scan_used = (TextView) findViewById(R.id.scan_used);
        String scan_used = "# Of Scans Used: " + new_game.getScanCount();
        display_scan_used.setText(scan_used);
    }

    public static Intent makeIntentForPlay(MainMenu context){
        Intent intent = new Intent(context, GamePlay.class);
        return intent;
    }

}
