package com.example.sr_courier;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewCouriers extends AppCompatActivity {
    private ArrayList<Courier> couriers;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courier_list);

        dbHandler = new DatabaseHandler(ViewCouriers.this);
        couriers = dbHandler.getAllCouriers();

        //      (ListView + ArrayAdapter)
        CourierAdapter adapter = new CourierAdapter(this, couriers);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

    }
}
