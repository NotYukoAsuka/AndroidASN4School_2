package ydsun.mineseeker.models;

import java.util.Arrays;
import java.util.Collections;

public class Mines {
    private int mRowCount;
    private int mColCount;
    private int mMineCount;

    private boolean mMines[];

    public Mines (int rowCount, int colCount, int mineCount) {
        mRowCount = rowCount;
        mColCount = colCount;
        mMines = new boolean[rowCount * colCount];

        if (mineCount > rowCount * colCount) {
            throw new IllegalArgumentException("mineCount cannot be more than rowCount*colCount");
        }

        // TODO: randomly place `mineCount' mines in the array
        for(int i = 0; i < mineCount; i++) {
            mMines[i] = true;
        }
        Collections.shuffle(Arrays.asList(mMines));
    }
    public int useScan(int index){
        int count_mines = 0;
        int row_pos = index%this.mColCount;
        for(int i = index - row_pos; i < (index - row_pos) + this.mColCount; i++){
            if(this.mMines[i]){
                count_mines++;
            }
        }
        for(int j = row_pos; j <= row_pos + (this.mRowCount-1)*(this.mColCount); j+=this.mColCount){
            if(this.mMines[j] || j != index){
                count_mines++;
            }
        }
        return count_mines;
    }
}
