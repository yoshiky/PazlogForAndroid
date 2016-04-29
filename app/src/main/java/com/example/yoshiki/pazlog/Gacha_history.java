package com.example.yoshiki.pazlog;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name="Gacha_history")
public class Gacha_history extends Model{

    @Column(name="EggType")
    public int EggType;

    @Column(name="MonsterName")
    public String MonsterName;

    @Column(name="GotAt")
    public Date GotAt;

    @Column(name="Status")
    public int Status;

    @Column(name="DeletedAt")
    public Date DeletedAt;
}
