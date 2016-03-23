package com.example.yoshiki.pazlog;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CorrectionInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Gacha extends AppCompatActivity {

    protected MyGachaMonsterListItem myGachaMonsterListItem;

    static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gacha);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Activity activity  =this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Gacha_save.class);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {

        List<MyGachaMonsterListItem> monsters = new ArrayList<>();

        List<Gacha_history> lists = new Select().from(Gacha_history.class).orderBy("GotAt DESC").execute();
        for (Gacha_history list : lists){
//            Log.d("mylog", String.valueOf(list.EggType));
//            Log.d("mylog", list.MonsterName);
//            Log.d("mylog", String.valueOf(list.GotAt));

            myGachaMonsterListItem = new MyGachaMonsterListItem(
                list.MonsterName,
                list.EggType,
                list.GotAt
            );
            monsters.add(myGachaMonsterListItem);

        }

        MyBaseAdaptor myBaseAdaptor = new MyBaseAdaptor(this, monsters);

        final ListView mlist = (ListView) this.findViewById(R.id.mListView);
        mlist.setAdapter(myBaseAdaptor);

        super.onResume();

        mlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = position + "番目のアイテムが押されました。";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }


    public class MyBaseAdaptor extends BaseAdapter{
        private Context context;
        private List<MyGachaMonsterListItem> monsters;

        public MyBaseAdaptor(Context context, List<MyGachaMonsterListItem> monsters) {
            this.context = context;
            this.monsters = monsters;
        }

        private class ViewHolder{
            TextView gotMonsterName;
            TextView gotEggType;
            TextView gotAt;
        }

        @Override
        public int getCount() {
            return monsters.size();
        }

        @Override
        public Object getItem(int position) {
            return monsters.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = new ViewHolder();
            myGachaMonsterListItem = monsters.get(position);
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_gacha, parent, false);
                TextView textGotMonsterName = (TextView) convertView.findViewById(R.id.got_monster_name);
                TextView textGotEggType = (TextView) convertView.findViewById(R.id.got_egg_type);
                TextView textGotAt = (TextView) convertView.findViewById(R.id.got_at);

                holder.gotMonsterName = textGotMonsterName;
                holder.gotEggType = textGotEggType;
                holder.gotAt = textGotAt;
                convertView.setTag(holder);
            } else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.gotMonsterName.setText(myGachaMonsterListItem.getMonster_name());
            holder.gotEggType.setText(myGachaMonsterListItem.getEggTypeStr());
            holder.gotAt.setText( new SimpleDateFormat(DATE_PATTERN).format( myGachaMonsterListItem.getGot_at()));

            return convertView;
        }
    }

}
