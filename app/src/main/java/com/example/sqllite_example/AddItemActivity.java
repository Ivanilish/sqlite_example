package com.example.sqllite_example;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private DataBaseHelper dbHelper;
    private EditText editTitle, editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dbHelper = new DataBaseHelper(this);
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String description = editDescription.getText().toString().trim();

                if (!title.isEmpty() && !description.isEmpty()) {
                    addItemToDatabase(title, description);
                } else {
                    Toast.makeText(AddItemActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addItemToDatabase(String title, String description) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COLUMN_TITLE, title);
        values.put(DataBaseHelper.COLUMN_DESCRIPTION, description);

        long result = db.insert(DataBaseHelper.TABLE_ITEMS, null, values);
        if (result != -1) {
            Toast.makeText(this, "Elemento guardado", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad al guardar
        } else {
            Toast.makeText(this, "Error al guardar el elemento", Toast.LENGTH_SHORT).show();
        }
    }
}
