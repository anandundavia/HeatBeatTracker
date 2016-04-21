package com.example.anandundavia.heatbeattracker;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database.LOCALDB = new Database(this);
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
