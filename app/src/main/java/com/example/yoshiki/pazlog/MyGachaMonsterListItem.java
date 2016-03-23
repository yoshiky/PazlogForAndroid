package com.example.yoshiki.pazlog;

import java.util.Date;

public class MyGachaMonsterListItem {
    protected int id;
    protected String monster_name;
    protected int egg_type;
    protected Date got_at;

    public MyGachaMonsterListItem(String monster_name, int egg_type, Date got_at) {
        this.monster_name = monster_name;
        this.egg_type = egg_type;
        this.got_at = got_at;
    }

    public String getMonster_name() {
        return monster_name;
    }

    public int getEgg_type() {
        return egg_type;
    }

    public String getEggTypeStr() {
        String eggTypeStr;
        switch (egg_type){
            case 1:
                eggTypeStr = "金";
                break;
            case 2:
                eggTypeStr = "銀";
                break;
            case 3:
                eggTypeStr = "星";
                break;
            default:
                eggTypeStr = "不明";
        }
        return eggTypeStr;
    }

    public Date getGot_at() {
        return got_at;
    }
}
