// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.dtosql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import vn.edu.uit.jrfsit.entity.JobSearch;

public class DatabaseHandler extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "jobSearchRecently";
    private static final int DATABASE_VERSION = 1;
    private static final String DATE_TIME = "DateTime";
    private static final String KEYWORD = "keyword";
    private static final String KEY_ID = "id";
    private static final String LOCATION = "location";
    private static final String SEARCH_MODE = "searchmode";
    private static final String SPECIAL = "specialy";
    private static final String TABLE_SEARCH = "search";
    private static final String TIME_SAVE = "604800000";

    public DatabaseHandler(Context context)
    {
        super(context, "jobSearchRecently", null, 2);
    }

    public void addSearch(JobSearch jobsearch)
    {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("keyword", jobsearch.getJobName());
        contentvalues.put("location", jobsearch.getLocation());
        contentvalues.put("searchmode", jobsearch.getSortmode());
        contentvalues.put("specialy", jobsearch.getSpecialy());
        contentvalues.put("DateTime", Long.valueOf(jobsearch.getDateTime()));
        sqlitedatabase.insert("search", null, contentvalues);
        sqlitedatabase.delete("search", "? - DateTime > ?", new String[] {
            String.valueOf(jobsearch.getDateTime()), "604800000"
        });
        sqlitedatabase.close();
    }

    public void deleteJobAll()
    {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.delete("search", "1 = 1", new String[0]);
        sqlitedatabase.close();
    }

    public List getAllContacts()
    {
        ArrayList arraylist = new ArrayList();
        Cursor cursor = getWritableDatabase().rawQuery("SELECT  * FROM search", null);
        if (cursor.moveToFirst())
        {
            do
            {
                arraylist.add(new JobSearch(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getString(3), cursor.getInt(5)));
            } while (cursor.moveToNext());
        }
        return arraylist;
    }

    public int getContactsCount()
    {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT  * FROM search", null);
        cursor.close();
        return cursor.getCount();
    }

    JobSearch getSearch(int i)
    {
        Cursor cursor = getReadableDatabase().query("search", new String[] {
            "id", "keyword", "location", "searchmode", "specialy"
        }, "id=?", new String[] {
            String.valueOf(i)
        }, null, null, null, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        return new JobSearch(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
    }

    public void onCreate(SQLiteDatabase sqlitedatabase)
    {
        sqlitedatabase.execSQL("CREATE TABLE search(id INTEGER PRIMARY KEY,keyword TEXT,location TEXT,searchmode TEXT,specialy TEXT,DateTime int)");
    }

    public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j)
    {
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS search");
        onCreate(sqlitedatabase);
    }

    public int updateContact(JobSearch jobsearch)
    {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("keyword", jobsearch.getJobName());
        contentvalues.put("location", jobsearch.getLocation());
        contentvalues.put("searchmode", jobsearch.getSalary());
        contentvalues.put("specialy", jobsearch.getSpecialy());
        return sqlitedatabase.update("search", contentvalues, "id = ?", new String[] {
            String.valueOf(jobsearch.getId())
        });
    }
}
