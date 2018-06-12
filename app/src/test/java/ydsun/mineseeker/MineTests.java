package ydsun.mineseeker;

import org.junit.Assert;
import org.junit.Test;

import ydsun.mineseeker.models.Mines;

public class MineTests {
    @Test
    public void testing_getSet() throws Exception {
        Mines testing_game = new Mines(10,10,10);
        Assert.assertEquals(testing_game.getRowCount(), 10);
        Assert.assertEquals(testing_game.getColCount(), 10);
        Assert.assertEquals(testing_game.getMineCount(), 10);

        testing_game.setMineCount(20);
        testing_game.setRowCount(20);
        testing_game.settColCount(20);
        Assert.assertEquals(testing_game.getRowCount(), 20);
        Assert.assertEquals(testing_game.getColCount(), 20);
        Assert.assertEquals(testing_game.getMineCount(), 20);
    }

    @Test
    public void testing_scans() throws Exception {
        Mines testing_game = new Mines(10,11,6);
        testing_game.scanMines();
        int scanned_mine_total = 0;
        for(int i = 0; i < testing_game.getColCount() * testing_game.getRowCount(); i++){
            scanned_mine_total += testing_game.getScannedMineCount(i);
        }
        Assert.assertEquals(scanned_mine_total, 120);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases() throws Exception{
        Mines testing_game = new Mines(1,1,10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_2() throws Exception{
        Mines testing_game_again = new Mines(10,10,5);
        testing_game_again.settColCount(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_3() throws Exception{
        Mines testing_game_again = new Mines(10,10,5);
        testing_game_again.setRowCount(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_4() throws Exception{
        Mines testing_game_again = new Mines(2,2,1);
        testing_game_again.setMineCount(20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_5() throws Exception{
        Mines testing_game_again = new Mines(2,2,1);
        testing_game_again.scanMines();
        int mine_test = testing_game_again.getScannedMineCount(5);
    }
}
