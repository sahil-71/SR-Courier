package com.example.sr_courier;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewCouriers extends AppCompatActivity {
    private ArrayList<Courier> couriers;
    private DatabaseHandler dbHandler;
    private CourierAdapter courierAdapter;
    private RecyclerView couriersRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courier);

        dbHandler = new DatabaseHandler(ViewCouriers.this);

        couriers = dbHandler.getAllCouriers();

        courierAdapter = new CourierAdapter(couriers, ViewCouriers.this);
        couriersRecycleView = findViewById(R.id.idRVCouriers);
        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewCouriers.this, RecyclerView.VERTICAL, false);
        couriersRecycleView.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        couriersRecycleView.setAdapter(courierAdapter);

    }
}
