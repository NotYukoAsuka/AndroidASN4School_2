package ydsun.mineseeker.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Mines {
    private int mRowCount;
    private int mColCount;
    private int mMineCount;
    private int[] mScans;
    private int mScanUsed;

    private boolean mMines[];

    private Mines (int rowCount, int colCount, int mineCount) {
        mRowCount = rowCount;
        mColCount = colCount;
        mMineCount = mineCount;
        mMines = new boolean[rowCount * colCount];
        mScans = new int[rowCount * colCount];
        mScanUsed = 0;

        if (mMineCount > rowCount * colCount) {
            throw new IllegalArgumentException("mineCount cannot be more than rowCount*colCount");
        }

        // TODO: randomly place `mineCount' mines in the array
        for(int i = 0; i < mMineCount; i++) {
            mMines[i] = true;
        }

        Random rnd = ThreadLocalRandom.current();
        for (int i = mColCount * mRowCount - 1; i > 0; i--){
            int index = rnd.nextInt(i + 1);
            // Simple swap
            boolean temp = mMines[index];
            mMines[index] = mMines[i];
            mMines[i] = temp;
        }

        for(int i = 0; i < rowCount * colCount; i++){
            mScans[i] = 0;
        }
    }

    // Singleton support
    private static Mines instance;
    public static Mines getInstance(int row, int col, int mines){
        if (instance == null){
            instance = new Mines(row, col, mines);
        }
        return new Mines(row, col, mines);
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

    public int getScanCount(){
        return this.mScanUsed;
    }

    public boolean getIndexStatus(int index){
        if(index < 0 || index > this.mColCount * this.mRowCount){
            throw new IllegalArgumentException("bad index!");
        }
        return this.mMines[index];
    }

    public void placeMine(int index){
        if(index < 0 || index > this.mColCount * this.mRowCount){
            throw new IllegalArgumentException("bad index!");
        }
        else if(!this.mMines[index]){
            this.mMines[index] = true;
            this.mMineCount++;
            this.scanMines();
        }
    }

    public void removeMine(int index){
        if(index < 0 || index > this.mColCount * this.mRowCount){
            throw new IllegalArgumentException("bad index!");
        }
        else if(this.mMines[index]){
            this.mMines[index] = false;
            this.mMineCount--;
            this.scanMines();
        }
    }

    public void setRowCount(int row){
        if(row <= 0){
            throw new IllegalArgumentException("row count must be greater than 0!");
        }
        else{
            this.mRowCount = row;
        }
    }

    public void setColCount(int col){
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

    private void newScan(){
            this.mScanUsed ++;
    }

    // how to scan mines
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

    // scanned all the mines' row and col mine count
    public void scanMines(){
        for(int i = 0; i < this.mRowCount * this.mColCount; i++){
            this.mScans[i] = scan(i);
        }
    }

    // return the mine's row and col mine count at given index
    public int getScannedMineCRCount(int index){
        if(index < 0 || index > this.mColCount * this.mRowCount){
            throw new IllegalArgumentException("bad index!");
        }
        else {
            return this.mScans[index];
        }
    }

    // what happens if a mine is clicked
    public int click_mines(int index){
        if(index < 0 || index > this.mColCount * this.mRowCount){
            throw new IllegalArgumentException("bad index!");
        }
        else if(this.mMines[index]){
            mMines[index] = false;
            this.mMineCount --;
            this.scanMines();
            return 999999999;
        }
        else {
            this.newScan();
            this.scanMines();
            return this.mScans[index];
        }
    }
}
