package com.android.dmk78.clubolympus.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.dmk78.clubolympus.Data.ClubOlympusContract;
import com.android.dmk78.clubolympus.Data.ClubOlympusContract.MemberEntry;

public class OlympusDbOpenHelper extends SQLiteOpenHelper {
    public OlympusDbOpenHelper(Context context) {
        super(context, ClubOlympusContract.DATABASE_NAME, null, ClubOlympusContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEMBERS_TABLE = "CREATE TABLE " + MemberEntry.TABLE_NAME + " ("
                + MemberEntry._ID + " INTEGER PRIMARY KEY,"
                + MemberEntry.COLUMN_FIRST_NAME + " TEXT,"
                + MemberEntry.COLUMN_LAST_NAME + " TEXT,"
                + MemberEntry.COLUMN_GENDER + " INTEGER NOT NULL,"
                + MemberEntry.COLUMN_SPORT + " TEXT" + ")";

        db.execSQL(CREATE_MEMBERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        //???? Why not TABLE_NAME
        db.execSQL("DROP TABLE IF EXISTS " + ClubOlympusContract.DATABASE_NAME);
        onCreate(db);

    }


}
