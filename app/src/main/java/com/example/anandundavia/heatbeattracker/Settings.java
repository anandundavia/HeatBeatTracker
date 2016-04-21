package com.example.anandundavia.heatbeattracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class Settings extends Fragment
{

    public static final String MODES[] = {"Normal", "Running", "Sleeping"};
    EditText softLimitET, hardLimitET, messageET, nameET;

    public Settings()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Spinner modeSpinner = (Spinner)rootView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.modes,android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(adapter);
        modeSpinner.setOnItemSelectedListener(new ModeSelectionListener());

        softLimitET = (EditText) rootView.findViewById(R.id.softet);
        hardLimitET = (EditText) rootView.findViewById(R.id.hardet);
        messageET = (EditText) rootView.findViewById(R.id.msget);
        nameET = (EditText) rootView.findViewById(R.id.nameet);


        nameET.setText(Database.LOCALDB.getName());
        softLimitET.setText(Integer.toString(Database.LOCALDB.getSoftLimitFor(MODES[0])));
        hardLimitET.setText(Integer.toString(Database.LOCALDB.getHardLimitFor(MODES[0])));
        messageET.setText(Database.LOCALDB.getMessageFor(MODES[0]));

        return rootView;
    }


    class ModeSelectionListener implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            softLimitET.setText(Integer.toString(Database.LOCALDB.getSoftLimitFor(MODES[position])));
            hardLimitET.setText(Integer.toString(Database.LOCALDB.getHardLimitFor(MODES[position])));
            messageET.setText(Database.LOCALDB.getMessageFor(MODES[position]));

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    }

}
