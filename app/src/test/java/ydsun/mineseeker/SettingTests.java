package ydsun.mineseeker;


import org.junit.Assert;
import org.junit.Test;

import ydsun.mineseeker.models.SettingsClass;

public class SettingTests {

    @Test
    public void testing_getSet() throws Exception {
        SettingsClass testing_settings = SettingsClass.sGetInstance();
        Assert.assertEquals(testing_settings.sGetRowCount(), 4);
        Assert.assertEquals(testing_settings.sGetColCount(), 6);
        Assert.assertEquals(testing_settings.sGetMineCount(), 6);

        testing_settings.sSetRowCount(20);
        testing_settings.sSetColCount(20);
        testing_settings.sSetMineCount(20);
        Assert.assertEquals(testing_settings.sGetRowCount(), 20);
        Assert.assertEquals(testing_settings.sGetColCount(), 20);
        Assert.assertEquals(testing_settings.sGetMineCount(), 20);
    }

    @Test
    public void testing_serialize_parse() throws Exception {
        SettingsClass testing_settings = SettingsClass.sGetInstance();
        String saved = testing_settings.serialize();
        testing_settings.sSetRowCount(10);
        testing_settings.sSetColCount(10);
        testing_settings.sSetMineCount(10);
        testing_settings.parse(saved);
        Assert.assertEquals(testing_settings.sGetRowCount(), 20);
        Assert.assertEquals(testing_settings.sGetColCount(), 20);
        Assert.assertEquals(testing_settings.sGetMineCount(), 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_1() throws Exception {
        SettingsClass testing_game_again = SettingsClass.sGetInstance();
        testing_game_again.sSetColCount(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_2() throws Exception {
        SettingsClass testing_game_again = SettingsClass.sGetInstance();
        testing_game_again.sSetRowCount(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testing_bad_cases_3() throws Exception {
        SettingsClass testing_game_again = SettingsClass.sGetInstance();
        testing_game_again.sSetMineCount(100);
    }

}
