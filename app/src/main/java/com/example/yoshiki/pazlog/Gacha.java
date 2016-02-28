package com.example.yoshiki.pazlog;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Gacha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gacha);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn = (Button)findViewById(R.id.btnRegistGachaResult);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupEggType);
                int rid = rg.getCheckedRadioButtonId();
                if(rid != -1){
                    RadioButton rb = (RadioButton) findViewById(rid);
                    String eggType = rb.getText().toString();
                    EditText etext1 = (EditText)findViewById(R.id.editTextGachaResult);
                    String monster = etext1.getText().toString();
                    Log.d("mylog", "id:" + eggType + ":" + monster);
                }

            }
        });
    }

}
