package ydsun.mineseeker.models;

import android.arch.core.BuildConfig;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import java.io.Serializable;

public class SettingsClass implements Serializable {
    private int sRowCount;
    private int sColCount;
    private int sMineCount;

    private SettingsClass () {
        sRowCount = 4;
        sColCount = 6;
        sMineCount = 6;
    }

    // Singleton support
    private static SettingsClass sInstance;
    public static SettingsClass sGetInstance(){
        if (sInstance == null){
            sInstance = new SettingsClass();
        }
        return sInstance;
    }

    public int sGetRowCount(){
        return this.sRowCount;
    }

    public int sGetColCount(){
        return this.sColCount;
    }

    public int sGetMineCount(){
        return this.sMineCount;
    }

    public void sSetRowCount(int row){
        if(row <= 0){
            throw new IllegalArgumentException("row count must be greater than 0!");
        }
        else{
            this.sRowCount = row;
        }
    }

    public void sSetColCount(int col){
        if(col <= 0){
            throw new IllegalArgumentException("column count must be greater than 0!");
        }
        else{
            this.sColCount = col;
        }
    }

    public void sSetMineCount(int mine){
        if(mine <= 0 || mine > this.sRowCount * this.sColCount){
            throw new IllegalArgumentException("not a valid mine count!");
        }
        else{
            this.sMineCount = mine;
        }
    }

    // save setting instance
    public String serialize(){
        return this.sGetRowCount() + "," + this.sGetColCount() + "," + this.sGetMineCount();
    }

    // load settings
    public void parse(String serialized){
        int pos_row = serialized.indexOf(",");
        int rowCount = Integer.parseInt(serialized.substring(0,pos_row));
        int pos_col = serialized.indexOf(",", pos_row + 2);
        int colCount = Integer.parseInt(serialized.substring(pos_row + 1, pos_col));
        int pos_mine = serialized.indexOf(",", pos_col + 2);
        int mineCount = Integer.parseInt(serialized.substring(pos_col + 1));
        this.sSetRowCount(rowCount);
        this.sSetColCount(colCount);
        this.sSetMineCount(mineCount);
    }
//
//    public void save(){
//        SharedPreferences sp = sCtx.getSharedPreferences(BuildConfig.APPLICATION_ID + ".PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
//        sp.edit().putString("Settings", this.serialize()).apply();
//    }
//
//    public void load(){
//        SharedPreferences sp = sCtx.getSharedPreferences(BuildConfig.APPLICATION_ID + ".PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
//            String settings_str = sp.getString("Settings", null);
//            if (settings_str != null) {
//                this.parse(settings_str);
//                sp.edit().putString("Settings", null).apply(); // clear storage
//            }
//    }
//
//    public void clear_data(){
//        SharedPreferences sp = sCtx.getSharedPreferences(BuildConfig.APPLICATION_ID + ".PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
//        sp.edit().clear().apply();
//    }
}