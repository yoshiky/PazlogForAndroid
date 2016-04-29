package com.example.yoshiki.pazlog;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.example.yoshiki.pazlog.constants.GachaHistoryConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Gacha extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

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

        final List<MyGachaMonsterListItem> monsters = new ArrayList<>();

        List<Gacha_history> lists = new Select().from(Gacha_history.class)
                .where("DeletedAt IS NULL")
                .orderBy("GotAt DESC")
                .execute();
        for (Gacha_history list : lists){
            myGachaMonsterListItem = new MyGachaMonsterListItem(
                list.getId(),
                list.MonsterName,
                list.EggType,
                list.GotAt,
                list.Status
            );
            monsters.add(myGachaMonsterListItem);

        }

        final MyBaseAdaptor myBaseAdaptor = new MyBaseAdaptor(this, monsters);
        final ListView mlist = (ListView) this.findViewById(R.id.mListView);
        mlist.setAdapter(myBaseAdaptor);

        super.onResume();

            mlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                startActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        mode.getMenuInflater().inflate(R.menu.gachalist, menu);

                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.icon_discard:

                                Long monster_id = monsters.get(position).getId();
                                new Update(Gacha_history.class)
                                        .set("Status = ? ,DeletedAt = ?", GachaHistoryConstants.DELETE, new Date())
                                        .where("id = ?", monster_id)
                                        .execute();
                                monsters.remove(position);
                                myBaseAdaptor.notifyDataSetChanged();
                                mode.finish();

                                return true;
                            case R.id.icon_edit:
                                break;
                        }
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                    }
                });
                return true;
            }
        });
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

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
            TextView gotStatus;
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
                TextView textStatus = (TextView) convertView.findViewById(R.id.got_status);

                holder.gotMonsterName = textGotMonsterName;
                holder.gotEggType = textGotEggType;
                holder.gotAt = textGotAt;
                holder.gotStatus = textStatus;
                convertView.setTag(holder);
            } else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.gotMonsterName.setText(myGachaMonsterListItem.getMonster_name());
            holder.gotEggType.setText(myGachaMonsterListItem.getEggTypeStr());
            holder.gotAt.setText( new SimpleDateFormat(DATE_PATTERN).format( myGachaMonsterListItem.getGot_at()));
            holder.gotStatus.setText(myGachaMonsterListItem.getStatusStr());

            return convertView;
        }

    }

}
