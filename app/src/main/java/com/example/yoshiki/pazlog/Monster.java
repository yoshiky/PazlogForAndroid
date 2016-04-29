package com.example.yoshiki.pazlog;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Monster extends AppCompatActivity implements LoaderManager.LoaderCallbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monster);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Bundle args = new Bundle();
        //args.putString("url", "http://192.168.1.11:3000/stub/gacha_histories/success");
        args.putString("url", "http://pazlog.herokuapp.com/stub/gacha_histories/success");

        // Activityに対応づけるLoaderの初期化。LoaderManagerインスタンスはActivityごとに一つ割り当てられる。
        getLoaderManager().initLoader(0, args, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        MyAsyncLoader loader = new MyAsyncLoader(this, args.getString("url"));
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if(loader.getId() == 0 && data != null){
            Log.d("mytag", String.valueOf(data));
            Toast toast = Toast.makeText(this, "synchronized !", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
