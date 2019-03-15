package com.android.dmk78.clubolympus.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class ClubOlympusContract {

    public final static String DATABASE_NAME = "olumpusDB";
    public final static int DATABASE_VERSION = 1;
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.android.dmk78.clubolympus";
    public static final String PATH_MEMBERS = "members";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    private ClubOlympusContract() {
    }

    public static final class MemberEntry implements BaseColumns {


        public static final String TABLE_NAME = "members";

        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_FIRST_NAME = "firstName";
        public static final String COLUMN_LAST_NAME = "lastName";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_SPORT = "sport";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS);
    }
}
