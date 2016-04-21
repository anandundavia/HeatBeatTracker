package com.example.anandundavia.heatbeattracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
{

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String message = intent.getStringExtra(RandomDataGen.KEY);
            Log.e(RandomDataGen.KEY, "Got message: " + message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database.LOCALDB = new Database(this);
        new Thread(new RandomDataGen(this)).start();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(RandomDataGen.DATA_BROADCAST));
        if (!Database.LOCALDB.isUserRegistered())
        {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ftm = fm.beginTransaction();
            ftm.replace(R.id.container, new FirstTime()).commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_settings)
        {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ftm = fm.beginTransaction();
            ftm.replace(R.id.container, new Settings()).commit();
        }

        return super.onOptionsItemSelected(item);
    }
}
