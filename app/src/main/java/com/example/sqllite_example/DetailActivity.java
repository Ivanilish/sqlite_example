package com.example.sqllite_example;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class DetailActivity extends AppCompatActivity {

    private DataBaseHelper dbHelper;
    private TextView detailTitle;
    private TextView detailDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataBaseHelper(this);
        detailTitle = findViewById(R.id.detailTitle);
        detailDescription = findViewById(R.id.detailDescription);

        int itemId = getIntent().getIntExtra("ITEM_ID", -1);
        if (itemId != -1) {
            loadItemDetails(itemId);
        }
    }

    private void loadItemDetails(int itemId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = DataBaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(itemId) };

        Cursor cursor = db.query(DataBaseHelper.TABLE_ITEMS, null, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_DESCRIPTION));

            detailTitle.setText(title);
            detailDescription.setText(description);

            cursor.close();
        }
    }
}


