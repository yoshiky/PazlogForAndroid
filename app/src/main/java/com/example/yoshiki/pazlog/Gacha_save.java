package com.example.yoshiki.pazlog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.Date;
import com.example.yoshiki.pazlog.constants.GachaHistoryConstants;

public class Gacha_save extends AppCompatActivity {

    private final static int RB1 = View.generateViewId();
    private final static int RB2 = View.generateViewId();
    private final static int RB3 = View.generateViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gacha_save);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Activity activity = this;

        final RadioGroup rg;
        rg = (RadioGroup) findViewById(R.id.radioGroupEggType);

        RadioButton rbtn1 = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton rbtn2 = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton rbtn3 = (RadioButton) findViewById(R.id.radioButton3);
        rbtn1.setId(RB1);
        rbtn2.setId(RB2);
        rbtn3.setId(RB3);

        Button btn_gacha_save = (Button) findViewById(R.id.btn_gacha_save);
        btn_gacha_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int rid = rg.getCheckedRadioButtonId();
                if(rid != -1) {

                    int eggType = 0;
                    if(rid == RB1){
                        eggType = 1;
                    }else if(rid == RB2){
                        eggType = 2;
                    }else if(rid == RB3){
                        eggType = 3;
                    }

                    RadioButton rb = (RadioButton) findViewById(rid);
                    String eggTypeStr = rb.getText().toString();
                    EditText etext1 = (EditText) findViewById(R.id.edit_gacha);
                    String monster = etext1.getText().toString();
                    Log.d("mylog", "id:" + rid + " eggTypeStr:" + eggTypeStr + " monster:" + monster);

                    Gacha_history ga = new Gacha_history();
                    ga.EggType = eggType;
                    ga.MonsterName = monster;
                    ga.GotAt = new Date();
                    ga.Status = GachaHistoryConstants.NEW;
                    ga.save();

                    Snackbar.make(v, monster + " を登録しました。", Snackbar.LENGTH_LONG)
                            .setAction(R.string.undo, new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder alertDialog= new AlertDialog.Builder(activity);
                                    alertDialog.setTitle("登録取消");
                                    alertDialog.setMessage("登録を取り消しますか？");
                                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(Gacha_save.this, "取り消しを取り消しました。", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(Gacha_save.this, "取り消しました。", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    alertDialog.show();
                                }
                            })
                            .show();

                    etext1.setText("");
                }


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
