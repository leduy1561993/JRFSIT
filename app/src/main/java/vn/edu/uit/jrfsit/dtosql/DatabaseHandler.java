package vn.edu.uit.jrfsit.dtosql;

/**
 * Created by LeDuy on 11/6/2015.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vn.edu.uit.jrfsit.entity.JobSearch;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "jobSearchRecently";
    private static final String TABLE_SEARCH = "search";
    private static final String KEY_ID = "id";
    private static final String KEYWORD = "keyword";
    private static final String LOCATION = "location";
    private static final String SEARCH_MODE = "searchmode";
    private static final String SPECIAL = "specialy";
    private static final String DATE_TIME = "DateTime";
    private static final String TIME_SAVE = "604800000";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SEARCH + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEYWORD + " TEXT,"
                + LOCATION + " TEXT," + SEARCH_MODE + " TEXT,"
                + SPECIAL + " TEXT,"
                + DATE_TIME +" int"+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addSearch(JobSearch search) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEYWORD, search.getJobName());
        values.put(LOCATION, search.getLocation());
        values.put(SEARCH_MODE, search.getSortmode());
        values.put(SPECIAL, search.getSpecialy());
        values.put(DATE_TIME, search.getDateTime());

        // Inserting Row
        db.insert(TABLE_SEARCH, null, values);
        db.delete("search","? - " + DATE_TIME + " > ?", new String[]{String.valueOf(search.getDateTime()),TIME_SAVE});
        db.close(); // Closing database connection
    }

    // code to get the single contact
    JobSearch getSearch(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SEARCH, new String[] { KEY_ID,
                        KEYWORD, LOCATION,SEARCH_MODE,SPECIAL }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        JobSearch jobSearch = new JobSearch(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5));
        // return contact
        return jobSearch;
    }

    // code to get all contacts in a list view
    public List<JobSearch> getAllContacts() {
        List<JobSearch> JobSearchList = new ArrayList<JobSearch>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SEARCH;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                JobSearch jobSearch = new JobSearch(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),cursor.getString(4),cursor.getString(3),cursor.getInt(5));
                // Adding contact to list
                JobSearchList.add(jobSearch);
            } while (cursor.moveToNext());
        }

        // return contact list
        return JobSearchList;
    }

    // code to update the single contact
    public int updateContact(JobSearch jobSearch) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEYWORD, jobSearch.getJobName());
        values.put(LOCATION, jobSearch.getLocation());
        values.put(SEARCH_MODE, jobSearch.getSalary());
        values.put(SPECIAL, jobSearch.getSpecialy());

        // updating row
        return db.update(TABLE_SEARCH, values, KEY_ID + " = ?",
                new String[] { String.valueOf(jobSearch.getId()) });
    }

    // Deleting single contact
    public void deleteJobAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SEARCH, "1 = 1",
                new String[] {});
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SEARCH;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}