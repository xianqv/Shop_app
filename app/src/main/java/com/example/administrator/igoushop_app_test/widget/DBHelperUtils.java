package com.example.administrator.igoushop_app_test.widget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.administrator.igoushop_app_test.pojos.UserInfo;

public class DBHelperUtils{

    private static final String TAG = "DatabaseUtil";

    /**
     * Database Name
     */
    private static final String DATABASE_NAME = "user_database";

    /**
     * Database Version
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Table Name
     */
    private static final String DATABASE_TABLE = "user";

    /**
     * Table columns
     */
    public static final String KEY_NAME = "name";
    public static final String KEY_PWD = "pwd";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_HAND_IMG = "handImg";
    public static final String KEY_ROWID = "_id";

    /**
     * Database creation sql statement
     */
//    private static final String CREATE_STUDENT_TABLE =
//            "create table " + DATABASE_TABLE + " (" + KEY_ROWID + " integer primary, "
//                    + KEY_NAME +" text not null, " + KEY_PWD + " text not null);";

    private static final String CREATE_STUDENT_TABLE = "create table user (_id integer primary key,name varchar(64),pwd varchar(64),phone varchar(64),handImg varchar(64))";
    /**
     * Context
     */
    private final Context mCtx;

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Inner private class. Database Helper class for creating and updating database.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        /**
         * onCreate method is called for the 1st time when database doesn't exists.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "Creating DataBase: " + CREATE_STUDENT_TABLE);
            db.execSQL(CREATE_STUDENT_TABLE);
        }
        /**
         * onUpgrade method is called when database version changes.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public DBHelperUtils(Context ctx) {
        this.mCtx = ctx;
    }
    /**
     * This method is used for creating/opening connection
     * @return instance of DatabaseUtil
     * @throws SQLException
     */
    public DBHelperUtils open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    /**
     * This method is used for closing the connection.
     */
    public void close() {
        mDbHelper.close();
    }


    public long Login(UserInfo userInfo) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROWID, userInfo.getId());
        initialValues.put(KEY_NAME, userInfo.getName());
        initialValues.put(KEY_PWD, userInfo.getPwd());
        initialValues.put(KEY_PHONE, userInfo.getPhone());
        initialValues.put(KEY_HAND_IMG, userInfo.getHandImg());
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
    /**
     * This method will delete UserInfo record.
     * @param rowId
     * @return boolean
     */
    public boolean Logout(Integer rowId) {
       long id =  mDb.delete(DATABASE_TABLE, KEY_ROWID+ "= ?", new String[]{""+rowId});
        if(id == -1){
            return false;
        }else {
            return true;
        }
    }

    /**
     * This method will return Cursor holding all the Student records.
     * @return Cursor
     */
    public Cursor fetchAllUserInfo() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_PWD,KEY_PHONE,KEY_HAND_IMG}, null, null, null, null, null);
    }




    public boolean updateUserInfo(UserInfo userInfo) {
        ContentValues args = new ContentValues();
        args.put(KEY_ROWID, userInfo.getId());
        args.put(KEY_NAME, userInfo.getName());
        args.put(KEY_PWD, userInfo.getPwd());
        args.put(KEY_PHONE, userInfo.getPhone());
        args.put(KEY_HAND_IMG, userInfo.getHandImg());
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + userInfo.getId(), null) > 0;
    }

    public UserInfo toUserInfo(Cursor cursor) {
        UserInfo userInfo = new UserInfo();
        if(cursor!=null&&cursor.moveToNext()) {
            userInfo.setId(cursor.getInt(cursor.getColumnIndex(KEY_ROWID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            userInfo.setPwd(cursor.getString(cursor.getColumnIndex(KEY_PWD)));
            userInfo.setPhone(cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            userInfo.setHandImg(cursor.getString(cursor.getColumnIndex(KEY_HAND_IMG)));
        }
        return userInfo;
    }



}