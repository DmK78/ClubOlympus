package com.android.dmk78.clubolympus.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.dmk78.clubolympus.Data.ClubOlympusContract.*;

public class OlympusContentProvider extends ContentProvider {

    OlympusDbOpenHelper dbOpenHelper;

    private static final int MEMBERS = 111;
    private static final int MEMBER_ID = 222;
    //Create UriMatcher object
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS, MEMBERS);
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS + "/#", MEMBER_ID);
    }

    @Override
    public boolean onCreate() {
        dbOpenHelper = new OlympusDbOpenHelper(getContext());
        return true;
    }


    @Override

    //content://com.android.dmk78.sportclubolympus.Data.ClubOlympusContract/members/34
    //projection = {"lastName", "gender"}


    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch (match) {
            case MEMBERS:

                cursor = db.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case MEMBER_ID:

                // selection ="_id=?"
                //selectionArgs = 34


                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

                break;

            default:

                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can`t query incorrect URI" + uri);

        }

        return cursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        String firstName = values.getAsString(MemberEntry.COLUMN_FIRST_NAME);
        if (firstName==null){
            throw new IllegalArgumentException("You have to unput first name");
        }
        String lastName = values.getAsString(MemberEntry.COLUMN_LAST_NAME);
        if (lastName==null){
            throw new IllegalArgumentException("You have to unput last name");
        }
        Integer gender=values.getAsInteger(MemberEntry.COLUMN_GENDER);
        if (gender==null || !(gender==MemberEntry.GENDER_FEMALE || gender==MemberEntry.GENDER_MALE || gender==MemberEntry.GENDER_UNKNOWN)){
            throw new IllegalArgumentException("You have to select gender");
        }
        String sport = values.getAsString(MemberEntry.COLUMN_SPORT);
        if (sport==null){
            throw new IllegalArgumentException("You have to input sport");
        }


        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:

                long id = db.insert(MemberEntry.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e("InsertMethod", "Insertion of data in the table failed for " + uri);
                    return null;
                }
                return ContentUris.withAppendedId(uri, id);

            default:

                throw new IllegalArgumentException("Insertion of data in the table failed for " + uri);

        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return db.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);
            case MEMBER_ID:
                // selection ="_id=?"
                //selectionArgs = 34
                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Can`t delete this URI" + uri);

        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if(values.containsKey(MemberEntry.COLUMN_FIRST_NAME)){

            String firstName = values.getAsString(MemberEntry.COLUMN_FIRST_NAME);
            if (firstName==null){
                throw new IllegalArgumentException("You have to unput first name");
            }
        }

        if(values.containsKey(MemberEntry.COLUMN_LAST_NAME)){

            String lastName = values.getAsString(MemberEntry.COLUMN_LAST_NAME);
            if (lastName==null){
                throw new IllegalArgumentException("You have to unput last name");
            }

        }

        if(values.containsKey(MemberEntry.COLUMN_GENDER)){

            Integer gender=values.getAsInteger(MemberEntry.COLUMN_GENDER);
            if (gender==null || !(gender==MemberEntry.GENDER_FEMALE || gender==MemberEntry.GENDER_MALE || gender==MemberEntry.GENDER_UNKNOWN)){
                throw new IllegalArgumentException("You have to select gender");
            }

        }

        if (values.containsKey(MemberEntry.COLUMN_SPORT)){


            String sport = values.getAsString(MemberEntry.COLUMN_SPORT);
            if (sport==null){
                throw new IllegalArgumentException("You have to input sport");
            }

        }



        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return db.update(MemberEntry.TABLE_NAME, values, selection, selectionArgs);
            case MEMBER_ID:
                // selection ="_id=?"
                //selectionArgs = 34
                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.update(MemberEntry.TABLE_NAME, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Can`t update this URI" + uri);

        }
    }

    @Override
    public String getType(Uri uri) {

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:

         return MemberEntry.CONTENT_MULTIPLE_ITEMS;

            case MEMBER_ID:

         return MemberEntry.CONTENT_SINGLE_ITEM;

            default:
                throw new IllegalArgumentException("Unknown URI" + uri);

        }
    }
}
