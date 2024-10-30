package gt.edu.umg.proyectoprogra2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gt.edu.umg.proyectoprogra2.Adaptador.ImageAdapter;
import gt.edu.umg.proyectoprogra2.BaseDatos.DatabaseHelper;
import gt.edu.umg.proyectoprogra2.Modelo.ImageItem;

public class GaleriaActivity extends AppCompatActivity {

    private TextView tvLocationName, tvDate;
    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private List<ImageItem> imageList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galeria_activity);

        tvLocationName = findViewById(R.id.tvLocationName);
        tvDate = findViewById(R.id.tvDate);
        recyclerView = findViewById(R.id.galeriaView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        imageList = new ArrayList<>();

        // Retrieve location name and date from intent
        Intent intent = getIntent();
        String locationName = intent.getStringExtra("locationName");
        String date = intent.getStringExtra("date");

        // Set location name and date
        tvLocationName.setText(locationName);
        tvDate.setText(date);

        // Save location name and date in the database
        saveLocationAndDate(locationName, date);

        // Load images from the database
        cargarImagenes();

        // Get coordinates from the intent
        double latitude = intent.getDoubleExtra("latitude", 0.0);
        double longitude = intent.getDoubleExtra("longitude", 0.0);
        adapter = new ImageAdapter(imageList, latitude, longitude);
        recyclerView.setAdapter(adapter);
    }

    private void cargarImagenes() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT IMAGEN FROM " + DatabaseHelper.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                byte[] imageBytes = cursor.getBlob(0); // Get the BLOB
                imageList.add(new ImageItem(imageBytes));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void saveLocationAndDate(String locationName, String date) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("location", locationName);
        contentValues.put("date", date);
        db.insert("local_table", null, contentValues);
    }
}