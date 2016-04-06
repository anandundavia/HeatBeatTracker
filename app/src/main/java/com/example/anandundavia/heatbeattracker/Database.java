package com.example.anandundavia.heatbeattracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Anand Undavia on 3/28/2016.
 */
public class Database
{
    private static final String DATABASE_NAME = "MasterDB";

    private static final String USER_TABLE = "User";
    private static final String USER_NAME = "Name";

    private static final String CONTACTS_TABLE = "Contacts";
    private static final String CONTACT_NAME = "ContactName";
    private static final String CONTACT_NUMNBER = "ContactNumber";

    private static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (" + USER_NAME + " TEXT)";
    private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + CONTACTS_TABLE + " (" + CONTACT_NAME + " TEXT," + CONTACT_NUMNBER + " TEXT)";

    Context localContext;

    SQLiteDatabase db = null;

    public static Database LOCALDB = null;

    public Database(Context ct)
    {
        localContext = ct;
        db = localContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    void insertName(String name)
    {
        String q = "INSERT INTO " + USER_TABLE + " VALUES ('" + name + "')";
        db.execSQL(q);
    }

    void insertContact(String name, String number)
    {
        String q = "INSERT INTO " + CONTACTS_TABLE + " VALUES ('" + name + "','" + number + "')";
        db.execSQL(q);
    }

    String[] getAllNumbers()
    {
        String returnThis[] = new String[3];
        String q = "SELECT " + CONTACT_NUMNBER + " FROM " + CONTACTS_TABLE;
        Cursor c = db.rawQuery(q, null);

        c.moveToFirst();
        for (int i = 0; i < 3 && c.moveToNext(); i++)
        {
            returnThis[i] = c.getString(0);
        }
        c.close();
        return returnThis;
    }
}
