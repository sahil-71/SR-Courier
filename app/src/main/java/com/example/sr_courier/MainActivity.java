package com.example.sr_courier;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int UID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);

        final List<String> courierProviders = Arrays.asList("AKASH GANGA", "DELHIVERY", "DTDC", "MADHUR", "PROFESSIONAL", "SHREE MAHAVEER", "SHREE MARUTI", "SKYKING");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, courierProviders);
        AutoCompleteTextView spnCourierProvider = findViewById(R.id.spnCourierProvider);
        spnCourierProvider.setAdapter(dataAdapter);


        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(view -> {
            EditText edtTxtSender = findViewById(R.id.edtTxtSender);
            EditText edtTxtSenderMobile = findViewById(R.id.edtTxtSenderMobile);
            EditText edtTxtReceiver = findViewById(R.id.edtTxtReceiver);
            EditText edtTxtReceiverMobile = findViewById(R.id.edtTxtReceiverMobile);
            EditText edtTxtLocation = findViewById(R.id.edtTxtLocation);
            EditText edtTxtConsignment = findViewById(R.id.edtTxtConsignment);


            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            db.addCourier(new Courier(UID++,
                    spnCourierProvider.getText().toString(),
                    edtTxtConsignment.getText().toString(),
                    edtTxtSender.getText().toString(),
                    edtTxtSenderMobile.getText().toString(),
                    edtTxtReceiver.getText().toString(),
                    edtTxtReceiverMobile.getText().toString(),
                    edtTxtLocation.getText().toString(),
                    formatter.format(new Date()).toString()));
            Log.d("","Added Courier Successfully" + UID);

            String consignmentMsg = edtTxtConsignment.getText().toString().isEmpty() ?
                    ". Your C.No. will be shared shortly." :
                    ". Your C.No. is " + edtTxtConsignment.getText();
            String msg = "Shipment from " + edtTxtSender.getText()
                    + " has been booked with " + spnCourierProvider.getText()
                    + " Courier for " + edtTxtReceiver.getText()
                    + " for " + edtTxtLocation.getText()
                    + consignmentMsg
                    + "\nThanks \nSR Courier \nDev Nagar \nDelhi-110005";


            if (!edtTxtSenderMobile.getText().toString().isEmpty()) {
                Thread senderThread = new Thread(() -> openWhatsApp("Hi " + edtTxtSender.getText().toString() + ",\n" + msg, edtTxtSenderMobile.getText().toString()));
                senderThread.start();
            }
            if (!edtTxtReceiverMobile.getText().toString().isEmpty()) {
                Thread receiverThread = new Thread(() -> openWhatsApp("Hi " + edtTxtReceiver.getText().toString() + ",\n" + msg, edtTxtReceiverMobile.getText().toString()));
                receiverThread.start();
            }
            clearData();
        });

        Button btnSearchDB = findViewById(R.id.btnSearchDB);
        btnSearchDB.setOnClickListener(view -> {
            Intent intent = new Intent(this, ViewCouriers.class);
            startActivity(intent);
        });
    }

    private void clearData() {
        EditText edtTxtSender = findViewById(R.id.edtTxtSender);
        EditText edtTxtReceiver = findViewById(R.id.edtTxtReceiver);
        EditText edtTxtSenderMobile = findViewById(R.id.edtTxtSenderMobile);
        EditText edtTxtReceiverMobile = findViewById(R.id.edtTxtReceiverMobile);
        EditText edtTxtLocation = findViewById(R.id.edtTxtLocation);
        EditText edtTxtConsignment = findViewById(R.id.edtTxtConsignment);

        edtTxtSender.setText("");
        edtTxtReceiver.setText("");
        edtTxtSenderMobile.setText("");
        edtTxtReceiverMobile.setText("");
        edtTxtLocation.setText("");
        edtTxtConsignment.setText("");
    }

    private void openWhatsApp(String msg, String mobileNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=91" + mobileNumber + "&text=" + msg));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}