package com.android.dmk78.clubolympus;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.dmk78.clubolympus.Data.ClubOlympusContract.MemberEntry;


public class MainActivity extends AppCompatActivity {

    TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataTextView = findViewById(R.id.dataTextView);


        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.android.dmk78.clubolympus.AddMemberActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    private void displayData() {
        String projection[] = {
                MemberEntry._ID,
                MemberEntry.COLUMN_FIRST_NAME,
                MemberEntry.COLUMN_LAST_NAME,
                MemberEntry.COLUMN_GENDER,
                MemberEntry.COLUMN_SPORT
        };
        Cursor cursor = getContentResolver().query(
                MemberEntry.CONTENT_URI,
                projection, null, null, null
        );
        dataTextView.setText("All members\n\n");
        dataTextView.append(MemberEntry._ID + " " +
                MemberEntry.COLUMN_FIRST_NAME + " " +
                MemberEntry.COLUMN_LAST_NAME + " " +
                MemberEntry.COLUMN_GENDER + " " +
                MemberEntry.COLUMN_SPORT);

        int idColumnIndex = cursor.getColumnIndex(MemberEntry._ID);
        int FirstColumnNameIndex = cursor.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME);
        int LastNameColumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_LAST_NAME);
        int GenderColumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_GENDER);
        int SportColumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_SPORT);

        while (cursor.moveToNext()){
            int curentId = cursor.getInt(idColumnIndex);
            String curentFirstName = cursor.getString(FirstColumnNameIndex);
            String curentLastName = cursor.getString(LastNameColumnIndex);
            int curentGender = cursor.getInt(GenderColumnIndex);
            String curentSport = cursor.getString(SportColumnIndex);

            dataTextView.append("\n" +
                    curentId+" "+
                    curentFirstName+" "+
                    curentLastName+" "+
                    curentGender+" "+
                    curentSport);
        }
        cursor.close();

    }
}