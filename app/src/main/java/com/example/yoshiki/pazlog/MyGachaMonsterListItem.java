package com.example.yoshiki.pazlog;

import java.util.Date;

public class MyGachaMonsterListItem {
    protected Long id;
    protected String monster_name;
    protected int egg_type;
    protected Date got_at;
    protected int status;

    public MyGachaMonsterListItem(Long id, String monster_name, int egg_type, Date got_at, int status) {
        this.id = id;
        this.monster_name = monster_name;
        this.egg_type = egg_type;
        this.got_at = got_at;
        this.status = status;
    }

    public Long getId(){
        return id;
    }

    public String getMonster_name() {
        return monster_name;
    }

    public String getStatusStr() {
        String statusStr;
        switch (status){
            case 0:
                statusStr = "SYNC";
                break;
            case 1:
                statusStr = "NEW";
                break;
            case 2:
                statusStr = "EDIT";
                break;
            case 3:
                statusStr = "DELETE";
                break;
            default:
                statusStr = "unknown status";
        }
        return statusStr;
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
