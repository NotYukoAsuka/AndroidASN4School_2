package ydsun.mineseeker;

import org.junit.Assert;
import org.junit.Test;

import ydsun.mineseeker.models.Mines;

public class MineTests {
    @Test
    public void testing_getSet() throws Exception {
        Mines testing_game = Mines.getInstance(10,10,10);
        Assert.assertEquals(testing_game.getRowCount(), 10);
        Assert.assertEquals(testing_game.getColCount(), 10);
        Assert.assertEquals(testing_game.getMineCount(), 10);
        Assert.assertEquals(testing_game.getScanCount(), 0);

        testing_game.setMineCount(20);
        testing_game.setRowCount(20);
        testing_game.setColCount(20);
        Assert.assertEquals(testing_game.getRowCount(), 20);
        Assert.assertEquals(testing_game.getColCount(), 20);
        Assert.assertEquals(testing_game.getMineCount(), 20);
        Assert.assertEquals(testing_game.getScanCount(), 0);
    }

    @Test
    public void testing_scans() throws Exception {
        Mines testing_game = Mines.getInstance(10,11,6);
        testing_game.scanMines();
        int scanned_mine_total = 0;
        for(int i = 0; i < testing_game.getColCount() * testing_game.getRowCount(); i++){
            scanned_mine_total += testing_game.getScannedMineCRCount(i);
        }
        Assert.assertEquals(scanned_mine_total, 120);
    }

    @Test
    public void test_playing() throws Exception {
        Mines testing_game = Mines.getInstance(12,11,20);
        int number = testing_game.click_mines(0);
        if(number == 999999999){
            Assert.assertEquals(testing_game.getScanCount(), 0);
            Assert.assertEquals(testing_game.getIndexStatus(0), false);
            Assert.assertEquals(testing_game.getMineCount(), 19);
            int scanned_mine_total = 0;
            for(int i = 0; i < testing_game.getColCount() * testing_game.getRowCount(); i++){
                scanned_mine_total += testing_game.getScannedMineCRCount(i);
            }
            Assert.assertEquals(scanned_mine_total, 418);
        }
        else{
            Assert.assertEquals(testing_game.getScanCount(), 1);
            Assert.assertEquals(testing_game.getIndexStatus(0), false);
            Assert.assertEquals(testing_game.getMineCount(), 20);
            int scanned_mine_total = 0;
            for(int i = 0; i < testing_game.getColCount() * testing_game.getRowCount(); i++){
                scanned_mine_total += testing_game.getScannedMineCRCount(i);
            }
            Assert.assertEquals(scanned_mine_total, 440);
        }

        testing_game.placeMine(0);
        if(number == 999999999) {
            Assert.assertEquals(testing_game.getScanCount(), 0);
            Assert.assertEquals(testing_game.getMineCount(), 20);
            int scanned_mine_total = 0;
            for(int i = 0; i < testing_game.getColCount() * testing_game.getRowCount(); i++){
                scanned_mine_total += testing_game.getScannedMineCRCount(i);
            }
            Assert.assertEquals(scanned_mine_total, 440);
        }
        else{
            Assert.assertEquals(testing_game.getScanCount(), 1);
            Assert.assertEquals(testing_game.getMineCount(), 21);
            int scanned_mine_total = 0;
            for(int i = 0; i < testing_game.getColCount() * testing_game.getRowCount(); i++){
                scanned_mine_total += testing_game.getScannedMineCRCount(i);
            }
            Assert.assertEquals(scanned_mine_total, 462);
        }
        Assert.assertEquals(testing_game.getIndexStatus(0), true);

        testing_game.removeMine(0);
        if(number == 999999999) {
            Assert.assertEquals(testing_game.getScanCount(), 0);
            Assert.assertEquals(testing_game.getMineCount(), 19);
            int scanned_mine_total = 0;
            for(int i = 0; i < testing_game.getColCount() * testing_game.getRowCount(); i++){
                scanned_mine_total += testing_game.getScannedMineCRCount(i);
            }
            Assert.assertEquals(scanned_mine_total, 418);
        }
        else{
            Assert.assertEquals(testing_game.getScanCount(), 1);
            Assert.assertEquals(testing_game.getMineCount(), 20);
            int scanned_mine_total = 0;
            for(int i = 0; i < testing_game.getColCount() * testing_game.getRowCount(); i++){
                scanned_mine_total += testing_game.getScannedMineCRCount(i);
            }
            Assert.assertEquals(scanned_mine_total, 440);
        }
        Assert.assertEquals(testing_game.getIndexStatus(0), false);

        int new_number;
        int total_mines = testing_game.getMineCount();
        new_number = testing_game.click_mines(0);
        Assert.assertEquals(new_number, testing_game.getScannedMineCRCount(0));
        Assert.assertEquals(testing_game.getIndexStatus(0), false);
        int scanned_mine_total = 0;
        for(int i = 0; i < testing_game.getColCount() * testing_game.getRowCount(); i++){
            scanned_mine_total += testing_game.getScannedMineCRCount(i);
        }
        Assert.assertEquals(scanned_mine_total, total_mines * (testing_game.getRowCount() + testing_game.getColCount() - 1));
        if(number == 999999999){
            Assert.assertEquals(testing_game.getScanCount(), 1);
        }
        else{
            Assert.assertEquals(testing_game.getScanCount(), 2);
        }

        testing_game.placeMine(0);
        int total_mines_again = testing_game.getMineCount();
        if(number == 999999999){
            Assert.assertEquals(total_mines_again, 20);
            Assert.assertEquals(testing_game.getScanCount(), 1);
        }
        else{
            Assert.assertEquals(total_mines_again, 21);
            Assert.assertEquals(testing_game.getScanCount(), 2);
        }
        int scanned_mine_total_again = 0;
        for(int i = 0; i < testing_game.getColCount() * testing_game.getRowCount(); i++){
            scanned_mine_total_again += testing_game.getScannedMineCRCount(i);
        }
        Assert.assertEquals(scanned_mine_total_again, total_mines_again * (testing_game.getRowCount() + testing_game.getColCount() - 1));
        int result = testing_game.click_mines(0);
        total_mines_again = testing_game.getMineCount();
        Assert.assertEquals(result, 999999999);
        if(number == 999999999){
            Assert.assertEquals(total_mines_again, 19);
            Assert.assertEquals(testing_game.getScanCount(), 1);
        }
        else{
            Assert.assertEquals(total_mines_again, 20);
            Assert.assertEquals(testing_game.getScanCount(), 2);
        }
        scanned_mine_total_again = 0;
        for(int i = 0; i < testing_game.getColCount() * testing_game.getRowCount(); i++){
            scanned_mine_total_again += testing_game.getScannedMineCRCount(i);
        }
        Assert.assertEquals(scanned_mine_total_again, total_mines_again * (testing_game.getRowCount() + testing_game.getColCount() - 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_1() throws Exception{
        Mines testing_game = Mines.getInstance(1,1,10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_2() throws Exception{
        Mines testing_game_again = Mines.getInstance(2,2,1);
        testing_game_again.setColCount(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_3() throws Exception{
        Mines testing_game_again = Mines.getInstance(2,2,1);
        testing_game_again.setRowCount(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_4() throws Exception{
        Mines testing_game_again = Mines.getInstance(2,2,1);
        testing_game_again.setMineCount(20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_5() throws Exception{
        Mines testing_game_again = Mines.getInstance(2,2,1);
        testing_game_again.scanMines();
        int mine_test = testing_game_again.getScannedMineCRCount(5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_6() throws Exception{
        Mines testing_game_again = Mines.getInstance(2,2,1);
        testing_game_again.getIndexStatus(55);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_7() throws Exception{
        Mines testing_game_again = Mines.getInstance(2,2,1);
        testing_game_again.click_mines(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_8() throws Exception{
        Mines testing_game_again = Mines.getInstance(2,2,1);
        testing_game_again.removeMine(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_9() throws Exception{
        Mines testing_game_again = Mines.getInstance(2,2,1);
        testing_game_again.placeMine(-1);
    }
}
