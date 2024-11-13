package com.example.sqllite_example;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DataBaseHelper dbHelper;
    private ListView listView;
    private ArrayList<String> itemList;
    private ArrayList<Integer> itemIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataBaseHelper(this);
        listView = findViewById(R.id.listView);
        itemList = new ArrayList<>();
        itemIds = new ArrayList<>();

        loadItemsFromDatabase();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedItemId = itemIds.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("ITEM_ID", selectedItemId);
                startActivity(intent);
            }
        });
    }

    private void loadItemsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DataBaseHelper.TABLE_ITEMS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_TITLE));

                itemList.add(title);
                itemIds.add(id);
            } while (cursor.moveToNext());

            cursor.close();
        }
    }
}