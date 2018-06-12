package ydsun.mineseeker.models;

import java.util.Arrays;
import java.util.Collections;

public class Mines {
    private int mRowCount;
    private int mColCount;
    private int mMineCount;
    private int[] mScans;

    private boolean mMines[];

    public Mines (int rowCount, int colCount, int mineCount) {
        mRowCount = rowCount;
        mColCount = colCount;
        mMineCount = mineCount;
        mMines = new boolean[rowCount * colCount];
        mScans = new int[rowCount * colCount];

        if (mMineCount > rowCount * colCount) {
            throw new IllegalArgumentException("mineCount cannot be more than rowCount*colCount");
        }

        // TODO: randomly place `mineCount' mines in the array
        for(int i = 0; i < mMineCount; i++) {
            mMines[i] = true;
        }
        Collections.shuffle(Arrays.asList(mMines));

        for(int i = 0; i < rowCount * colCount; i++){
            mScans[i] = 0;
        }
    }

    public int getRowCount(){
        return this.mRowCount;
    }

    public int getColCount(){
        return this.mColCount;
    }

    public int getMineCount(){
        return this.mMineCount;
    }

    public void setRowCount(int row){
        if(row <= 0){
            throw new IllegalArgumentException("row count must be greater than 0!");
        }
        else{
            this.mRowCount = row;
        }
    }

    public void settColCount(int col){
        if(col <= 0){
            throw new IllegalArgumentException("column count must be greater than 0!");
        }
        else{
            this.mColCount = col;
        }
    }

    public void setMineCount(int mine){
        if(mine <= 0 || mine > this.mRowCount * this.mColCount){
            throw new IllegalArgumentException("not a valid mine count!");
        }
        else{
            this.mMineCount = mine;
        }
    }

    private int scan(int index){
        int count_mines = 0;
        int row_pos = index%this.mColCount;
        for(int i = index - row_pos; i < (index - row_pos) + this.mColCount; i++){
            if(this.mMines[i]){
                count_mines++;
            }
        }
        for(int j = row_pos; j <= row_pos + (this.mRowCount-1)*(this.mColCount); j+=this.mColCount){
            if(this.mMines[j] && j != index){
                count_mines++;
            }
        }
        return count_mines;
    }

    public void scanMines(){
        for(int i = 0; i < this.mRowCount * this.mColCount; i++){
            this.mScans[i] = scan(i);
        }
    }

    public int getScannedMineCount(int index){
        if(index < 0 || index > this.mColCount * this.mRowCount){
            throw new IllegalArgumentException("bad index!");
        }
        else {
            return this.mScans[index];
        }
    }
}
