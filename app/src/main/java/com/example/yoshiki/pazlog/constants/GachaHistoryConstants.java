package com.example.yoshiki.pazlog.constants;

public class GachaHistoryConstants {

    // privateコンストラクタでインスタンス生成を抑制
    private GachaHistoryConstants(){}

    public static final Integer SYNC = 0;   //サーバ同期完了
    public static final Integer NEW = 1;    //クライアント新規追加、サーバ未同期
    public static final Integer EDIT = 2;   //クライアント編集、サーバ未同期
    public static final Integer DELETE = 3; //クライアント削除、サーバ未同期


}
