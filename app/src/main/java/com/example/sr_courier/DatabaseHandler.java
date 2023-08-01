package com.example.sr_courier;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import android.content.ContentValues;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "courierManager";
    private static final String COURIER_TABLE = "courierDetails";
    private static final String UID = "UID";
    private static final String date = "date";
    private static final String consignmentNumber = "consignmentNumber";
    private static final String courierProvider = "courierProvider";
    private static final String senderName = "senderName";
    private static final String senderMobile = "senderMobile";
    private static final String receiverName = "receiverName";
    private static final String receiverMobile = "receiverMobile";
    private static final String destination = "destination";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COURIER_TABLE = "CREATE TABLE " + COURIER_TABLE + "("
                + UID + " TEXT,"
                + consignmentNumber + " TEXT PRIMARY KEY,"
                + courierProvider + " TEXT,"
                + senderName + " TEXT,"
                + senderMobile + " TEXT,"
                + receiverName + " TEXT,"
                + receiverMobile + " TEXT,"
                + destination + " TEXT,"
                + date + " TEXT" + "\n)";
        db.execSQL(CREATE_COURIER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + COURIER_TABLE);

        // Create tables again
        onCreate(db);
    }
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    // code to add the new Courier
    void addCourier(Courier courier) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UID, courier.getUID());
        values.put(consignmentNumber, courier.getConsignmentNumber());
        values.put(courierProvider, courier.getCourierProvider());
        values.put(senderName, courier.getSenderName());
        values.put(senderMobile, courier.getSenderMobile());
        values.put(receiverName, courier.getReceiverName());
        values.put(receiverMobile, courier.getReceiverMobile());
        values.put(destination, courier.getDestination());

        // Inserting Row
        db.insert(COURIER_TABLE, null, values);
        db.close(); // Closing database connection
    }

    // code to get the single contact
//    Courier getCourier(String consignmentNumber) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor =
//                db.query(COURIER_TABLE, new String[]{
//                                UID,
//                                consignmentNumber,
//                                courierProvider,
//                                senderName,
//                                senderMobile,
//                                receiverName,
//                                receiverMobile,
//                                destination,
//                                date
//                        }, consignmentNumber + "=?",
//                        new String[]{consignmentNumber}, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Courier courier = new Courier(
//                Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1),
//                cursor.getString(2),
//                cursor.getString(3),
//                cursor.getString(4),
//                cursor.getString(5),
//                cursor.getString(6),
//                cursor.getString(7),
//                cursor.getString(8));
//        // return contact
//        return courier;
//    }

    // code to get all contacts in a list view
    public ArrayList<Courier> getAllCouriers() {
        ArrayList<Courier> couriers = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + COURIER_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.d("","0 - " +cursor.getString(0));
                Log.d("","1 - " +cursor.getString(1));
                Log.d("","2 - " +cursor.getString(2));
                Log.d("","3 - " +cursor.getString(3));
                Log.d("","4 - " +cursor.getString(4));
                Log.d("","5 - " +cursor.getString(5));
                Log.d("","6 - " +cursor.getString(6));
                Log.d("","7 - " +cursor.getString(7));
                couriers.add(new Courier(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)));
                Log.d("","couriers size is " + couriers.size());
            } while (cursor.moveToNext());
        }
        cursor.close();
        return couriers;
    }
}
