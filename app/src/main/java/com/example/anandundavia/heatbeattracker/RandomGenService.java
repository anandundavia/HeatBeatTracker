package com.example.anandundavia.heatbeattracker;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Random;

public class RandomGenService extends Service implements Runnable
{
    public static final String DATA_BROADCAST = "databr";
    public static final String KEY = "key";
    private Context localContext;

    public RandomGenService()
    {
    }

    public RandomGenService(Context x)
    {
        localContext = x;
    }

    @Override
    public IBinder onBind(Intent intent)
    {

        return null;
    }

    @Override
    public void onCreate()
    {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        new Thread(this).start();
        return super.onStartCommand(intent, flags, startId);
    }

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

                    int soft = Database.LOCALDB.getSoftLimitFor(HomeFragment.CURRENT_SELECTED_MODE);
                    int hard = Database.LOCALDB.getHardLimitFor(HomeFragment.CURRENT_SELECTED_MODE);
                    if (result < soft || result > hard)
                    {
                        Log.e("Anand", "Should call");
                        String numbers[] = Database.LOCALDB.getAllNumbers();
                        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numbers[0]));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                        {
                            Log.e("Anand", "RETURN!");
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(i);
                    }

                }

                Log.e("Anand", "SERV Sleeping");

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
