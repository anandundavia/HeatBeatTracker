package com.example.anandundavia.heatbeattracker;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FirstTime extends Fragment
{
    private static final int PICK_EM1 = 1;
    private static final int PICK_EM2 = 2;
    private static final int PICK_DOC = 3;

    private View rootView;

    private Button em1Btn, em2Btn, docBtn, submitBtn;
    private boolean em1Selected = false, em2Selected = false, docSelected = false;
    private String em1Name, em2Name, docName, em1ContactNumber, em2ContactNumber, docContactNumber;

    private EditText nameET;

    public FirstTime()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_first_time, container, false);

        em1Btn = (Button) rootView.findViewById(R.id.em1Btn);
        em2Btn = (Button) rootView.findViewById(R.id.em2Btn);
        docBtn = (Button) rootView.findViewById(R.id.docBtn);
        submitBtn = (Button) rootView.findViewById(R.id.submitBtn);

        nameET = (EditText) rootView.findViewById(R.id.nameET);

        em1Btn.setOnClickListener(new MyContactPicker(PICK_EM1));
        em2Btn.setOnClickListener(new MyContactPicker(PICK_EM2));
        docBtn.setOnClickListener(new MyContactPicker(PICK_DOC));
        submitBtn.setOnClickListener(new SubmitHandler());
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            Uri contactData = data.getData();
            Cursor c = getContext().getContentResolver().query(contactData, null, null, null, null);
            if (c.moveToFirst())
            {
                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                Cursor phones = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                phones.moveToFirst();
                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //Toast.makeText(getContext(), number, Toast.LENGTH_SHORT).show();
                if (requestCode == PICK_EM1)
                {
                    em1Name = name;
                    em1ContactNumber = number.replaceAll("\\s+", "");
                    em1Btn.setText(name);
                    em1Selected = true;
                } else if (requestCode == PICK_EM2)
                {
                    em2Name = name;
                    em2ContactNumber = number.replaceAll("\\s+", "");
                    em2Btn.setText(name);
                    em2Selected = true;
                } else if (requestCode == PICK_DOC)
                {
                    docName = name;
                    docContactNumber = number.replaceAll("\\s+", "");
                    docBtn.setText(name);
                    docSelected = true;
                }
            }
        }
    }

    class MyContactPicker implements View.OnClickListener
    {
        int x;

        MyContactPicker(int ip)
        {
            x = ip;
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, x);
        }
    }

    class SubmitHandler implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            String enteredName = nameET.getText().toString();
            boolean allContactsPicked = em1Selected && em2Selected && docSelected;
            if (enteredName.equals(""))
            {
                Toast.makeText(getContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
                return;
            } else if (!allContactsPicked)
            {
                Toast.makeText(getContext(), "Please pick all the contacts", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getContext(), "Data will be saved", Toast.LENGTH_SHORT).show();

            /*
            String TAG = "DEBUG";
            Log.e(TAG,enteredName);
            Log.e(TAG,em1Name+" "+em1ContactNumber);
            Log.e(TAG,em2Name+" "+em2ContactNumber);
            Log.e(TAG,docName+" "+docContactNumber);
            */
            Database.LOCALDB.insertName(enteredName);
            Database.LOCALDB.insertContact(em1Name, em1ContactNumber);
            Database.LOCALDB.insertContact(em2Name, em2ContactNumber);
            Database.LOCALDB.insertContact(docName, docContactNumber);

        }
    }
}
