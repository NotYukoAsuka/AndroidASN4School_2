package ydsun.mineseeker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import junit.framework.Assert;

import ydsun.mineseeker.R;
import ydsun.mineseeker.models.SettingsClass;

public class Settings extends AppCompatActivity {

    private SettingsClass my_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        my_settings = SettingsClass.sGetInstance();

        setupSizeSpin();
        setupMineSpin();
    }

    public static Intent makeIntentForSettings(MainMenu context){
        Intent intent = new Intent(context, Settings.class);
        return intent;
    }

    // initiate board size spinner
    private void setupSizeSpin(){
        Spinner select_board_size = (Spinner) findViewById(R.id.board_size);
        ArrayAdapter<CharSequence> adapter_1 = ArrayAdapter.createFromResource(this, R.array.board_sizes, android.R.layout.simple_spinner_item);
        adapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_board_size.setAdapter(adapter_1);
        select_board_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getItemAtPosition(position).toString().indexOf(" x");
                int row_count = Integer.parseInt(parent.getItemAtPosition(position).toString().substring(0, pos));
                int col_count = Integer.parseInt(parent.getItemAtPosition(position).toString().substring(pos+3));
                my_settings.sSetRowCount(row_count);
                my_settings.sSetColCount(col_count);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // initiate mine amount spinner
    private void setupMineSpin(){
        Spinner select_mine_amount = (Spinner) findViewById(R.id.mine_amount);
        ArrayAdapter<CharSequence> adapter_2 = ArrayAdapter.createFromResource(this, R.array.mine_amount, android.R.layout.simple_spinner_item);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_mine_amount.setAdapter(adapter_2);
        select_mine_amount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int mine = Integer.parseInt(parent.getItemAtPosition(position).toString());
                my_settings.sSetMineCount(mine);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
