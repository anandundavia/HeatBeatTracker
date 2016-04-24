package com.example.anandundavia.heatbeattracker;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class HomeFragment extends Fragment
{
    public static String CURRENT_SELECTED_MODE;
    Spinner currentModeSpin;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String message = intent.getStringExtra(RandomDataGen.KEY);
            Log.e(RandomDataGen.KEY, "Got message: " + message);

            /*
            The toast is for degugging. Closing the acitivity, it will give null pointer
            Toast.makeText(getActivity(),"Got BPM : "+message,Toast.LENGTH_SHORT).show();
            */

            /*int soft = Database.LOCALDB.getSoftLimitFor(CURRENT_SELECTED_MODE);
            int hard = Database.LOCALDB.getHardLimitFor(CURRENT_SELECTED_MODE);
            int received = Integer.parseInt(message);
            if (received < soft || received > hard)
            {
                String numbers[] = Database.LOCALDB.getAllNumbers();
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numbers[0]));
                startActivity(i);
            }*/
        }
    };

    public HomeFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {


        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter(RandomDataGen.DATA_BROADCAST));

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        currentModeSpin = (Spinner) rootView.findViewById(R.id.currentmodespin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.modes, android.R.layout.simple_spinner_dropdown_item);
        currentModeSpin.setAdapter(adapter);
        currentModeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                CURRENT_SELECTED_MODE = Settings.MODES[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        return rootView;
    }

}
