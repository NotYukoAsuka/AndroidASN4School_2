package ydsun.mineseeker.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Mines {
    private static final int EXIST_MINE = 999727999;
    private static final int ALREADY_SCANNED = 100030001;
    private int mRowCount;
    private int mColCount;
    private int mMineCount;
    private int[] mScans;
    private int mScanUsed;
    private boolean mIfScanned[];
    private boolean mMines[];

    private Mines (int rowCount, int colCount, int mineCount) {

        if (mineCount > rowCount * colCount) {
            throw new IllegalArgumentException("mineCount cannot be more than rowCount*colCount");
        }

        mRowCount = rowCount;
        mColCount = colCount;
        mMineCount = mineCount;
        mMines = new boolean[rowCount * colCount];
        mIfScanned = new boolean[rowCount * colCount];
        mScans = new int[rowCount * colCount];
        mScanUsed = 0;


        for(int i = 0; i < mMineCount; i++) {
            mMines[i] = true;
        }

        int index;
        boolean temp;
        Random rnd = new Random();
        for (int i = mColCount * mRowCount - 1; i > 0; i--){
            index = rnd.nextInt(i + 1);
            // Simple swap
            temp = mMines[index];
            mMines[index] = mMines[i];
            mMines[i] = temp;
        }

        for(int i = 0; i < rowCount * colCount; i++){
            mScans[i] = 0;
        }

        for(int i = 0; i < rowCount * colCount; i++){
            mIfScanned[i] = false;
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

    public int msg_already_scanned(){
        return ALREADY_SCANNED;
    }

    public int msg_found_mine(){
        return EXIST_MINE;
    }

    // check if a position used a scan or not
    public boolean checkIfScanned(int index){
        if(index < 0 || index > this.mColCount * this.mRowCount){
            throw new IllegalArgumentException("bad index!");
        }
        return this.mIfScanned[index];
    }

    // check if a position has a mine or not
    public boolean getIndexStatus(int index){
        if(index < 0 || index > this.mColCount * this.mRowCount){
            throw new IllegalArgumentException("bad index!");
        }
        return this.mMines[index];
    }

    // place a mine in the given index if one does not already exist *for testing only*
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

    // removing a mine in the given index if one exists *for testing only*
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

    // change the row count *not so useful*
    public void setRowCount(int row){
        if(row <= 0){
            throw new IllegalArgumentException("row count must be greater than 0!");
        }
        else{
            this.mRowCount = row;
        }
    }

    // change the col count *not so useful*
    public void setColCount(int col){
        if(col <= 0){
            throw new IllegalArgumentException("column count must be greater than 0!");
        }
        else{
            this.mColCount = col;
        }
    }

    // set mine count *not so useful*
    public void setMineCount(int mine){
        if(mine <= 0 || mine > this.mRowCount * this.mColCount){
            throw new IllegalArgumentException("not a valid mine count!");
        }
        else{
            this.mMineCount = mine;
        }
    }

    // increment total scan used
    private void newScan(){
        if(this.getMineCount() != 0) {
            this.mScanUsed++;
        }
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
            return this.msg_found_mine();
        }
        else {
            if(!mIfScanned[index]) {
                this.newScan();
                this.scanMines();
                mIfScanned[index] = true;
                return this.mScans[index];
            }
            else{
                return this.msg_already_scanned();
            }
        }
    }
}
