package com.example.sqllite_example;


public class MainActivity extends AppCompatActivity {

package com.example.maestrodetalle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

    public class DetailActivity extends AppCompatActivity {

        private DatabaseHelper dbHelper;
        private TextView detailTitle;
        private TextView detailDescription;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);

            dbHelper = new DatabaseHelper(this);
            detailTitle = findViewById(R.id.detailTitle);
            detailDescription = findViewById(R.id.detailDescription);

            int itemId = getIntent().getIntExtra("ITEM_ID", -1);
            if (itemId != -1) {
                loadItemDetails(itemId);
            }
        }

        private void loadItemDetails(int itemId) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selection = DatabaseHelper.COLUMN_ID + " = ?";
            String[] selectionArgs = { String.valueOf(itemId) };

            Cursor cursor = db.query(DatabaseHelper.TABLE_ITEMS, null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION));

                detailTitle.setText(title);
                detailDescription.setText(description);

                cursor.close();
            }
        }
    }

}