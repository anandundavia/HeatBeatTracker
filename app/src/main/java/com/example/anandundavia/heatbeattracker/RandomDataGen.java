package com.example.anandundavia.heatbeattracker;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Random;

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
                Thread.sleep(10000);
                if (Database.LOCALDB.isUserRegistered())
                {
                    final Intent intent = new Intent(DATA_BROADCAST);

                    Random r = new Random();
                    int low = Database.LOCALDB.getSoftLimitFor(HomeFragment.CURRENT_SELECTED_MODE) - 5;
                    int high = Database.LOCALDB.getHardLimitFor(HomeFragment.CURRENT_SELECTED_MODE) + 5;
                    int result = r.nextInt(high - low) + low;

                    intent.putExtra(KEY, Integer.toString(result));
                    LocalBroadcastManager.getInstance(localContext).sendBroadcast(intent);
                }

                Log.e("Anand", "Sleeping");

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
