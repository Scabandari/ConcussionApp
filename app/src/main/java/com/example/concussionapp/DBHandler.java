package com.example.concussionapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by scabandari on 03/03/17.
 */

// the is Maries's version !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/* the code for our database was mostly taken from these two sites:
    https://github.com/codepath/android_guides/wiki/Local-Databases-with-SQLiteOpenHelper
    http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/


SOMETHING I NEED TO LOOK INTO TO SEE IF ITS IMPORTANT

Important Note: The SQLite database is lazily initialized.
This means that it isn't actually created until it's first
accessed through a call to getReadableDatabase() or
getWriteableDatabase(). This also means that any methods that
call getReadableDatabase() or getWriteableDatabase() should
be done on a background thread as there is a possibility
 that they might be kicking off the initial creation of the
  database.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static DBHandler sInstance;
        // Database Info
        private static final String DATABASE_NAME = "userDatabase";
        private static final int DATABASE_VERSION = 1;

        // Table Name
        private static final String TABLE_USERS = "users";

        // com.example.concussionapp.User Table Columns
        private static final String KEY_USER_ID = "id";
        private static final String KEY_USER_NAME = "userName";
        private static final String KEY_USER_PASSWORD = "password";
        private static final String KEY_USER_CP_EMAIL = "careProviderEmail";


        public static synchronized DBHandler getInstance(Context context) {
            // Use the application context, which will ensure that you
            // don't accidentally leak an Activity's context.
            // See this article for more information: http://bit.ly/6LRzfx
            if (sInstance == null) {
                sInstance = new DBHandler(context.getApplicationContext());
            }
            return sInstance;
        }

        /**
         * Constructor should be private to prevent direct instantiation.
         * Make a call to the static method "getInstance()" instead.
         */
        private DBHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Called when the database connection is being configured.
        // Configure database settings for things like foreign key support, write-ahead logging, etc.
        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
        }

        // Called when the database is created for the FIRST time.
        // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
        @Override
        public void onCreate(SQLiteDatabase db) {

            String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                    "(" +
                    KEY_USER_ID + " INTEGER PRIMARY KEY," +
                    KEY_USER_NAME + " TEXT," +
                    KEY_USER_PASSWORD + " TEXT," +
                    KEY_USER_CP_EMAIL + " TEXT" +
                    ")";

            db.execSQL(CREATE_USERS_TABLE);
        }

        // Called when the database needs to be upgraded.
        // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
        // but the DATABASE_VERSION is different than the version of the database that exists on disk.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion != newVersion) {
                // Simplest implementation is to drop all old tables and recreate them
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
                onCreate(db);
            }
        }

    // Insert a com.example.concussionapp.User into the database, returns a long we can store if we need it
    public Long addUser(User user) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;
        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).

            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, user.getUserName());
            values.put(KEY_USER_PASSWORD, user.getPassWord());
            values.put(KEY_USER_CP_EMAIL, user.getCareProviderEmailAddress());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            userId = db.insertOrThrow(TABLE_USERS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add a com.example.concussionapp.User to database");
        } finally {
            db.endTransaction();
        }

        return userId;
    }

    /*fetching a com.example.concussionapp.User from the database when given a user name ie when a user logs in
    with their user name we can fetch their password to make sure that they should be
    allowed access to their profile, and their care providers email so that when they finish
    their workout, then their results will be emailed */

    //we could run into a problem later if the username isn't found in the database
    // and a com.example.concussionapp.User object is returned which was created via Empty constructor
    public User fetchUser(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                KEY_USER_NAME + " = '" + name +"'";

        User newUser = new User();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor != null) {
                boolean moveToFirst = cursor.moveToFirst();
                if(moveToFirst) {
                    newUser.setCareProviderEmailAddress(cursor.getString(cursor.getColumnIndex(KEY_USER_CP_EMAIL)));
                    newUser.setUserName(cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
                    newUser.setPassWord(cursor.getString(cursor.getColumnIndex(KEY_USER_PASSWORD)));

                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to fetch a com.example.concussionapp.User from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return newUser;
    }


    public void deleteAllUsers() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_USERS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all users");
        } finally {
            db.endTransaction();
        }

}


}


