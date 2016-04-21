package com.example.anandundavia.heatbeattracker;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by Anand  Undavia on 4/21/2016.
 */
public class RandomDataGen implements Runnable
{
    public static final String DATA_BROADCAST = "databr";
    public static final String KEY = "key";
    private Context localContext;

    RandomDataGen(Context x)
    {
        localContext = x;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                final Intent intent = new Intent(DATA_BROADCAST);
                intent.putExtra(KEY, "1");
                LocalBroadcastManager.getInstance(localContext).sendBroadcast(intent);
                Log.e("Anand", "Sleeping");
                Thread.sleep(4000);
            } catch (Exception e)
            {

            }
        }
    }
}
