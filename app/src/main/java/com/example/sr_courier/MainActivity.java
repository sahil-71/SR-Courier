package com.example.sr_courier;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtTxtSender;
    private EditText edtTxtSenderMobile;
    private EditText edtTxtReceiver;
    private EditText edtTxtReceiverMobile;
    private EditText edtTxtLocation;
    private EditText edtTxtConsignment;
    private AutoCompleteTextView spnCourierProvider;
    private Button btnSelectReceiverContact;
    private Button btnSelectSenderContact;
    private Button btnSend;
    private int selectedContact;
    private static final int RECEIVER_CODE = 0;
    private static final int SENDER_CODE = 1;



    // Create an ActivityResultLauncher for the contacts picker
    private final ActivityResultLauncher<Intent> pickContactLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri contactUri = result.getData().getData();
                // Specify which fields you want your query to return values for
                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    // Retrieve the contact's name and phone number
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)).toUpperCase();
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\s+", "");
                    if (phoneNumber.contains("+")) {
                        phoneNumber = phoneNumber.split("\\+91")[1];
                    }
                    // Update UI with contact details
                    if(selectedContact == RECEIVER_CODE) {
                        edtTxtReceiver.setText(name);
                        edtTxtReceiverMobile.setText(phoneNumber);
                    }
                    else{
                        edtTxtSender.setText(name);
                        edtTxtSenderMobile.setText(phoneNumber);
                    }

                    cursor.close();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        createDropdown();
        selectContact();
        sendMessage(spnCourierProvider);
    }

    private void createDropdown() {
        final List<String> courierProviders = Arrays.asList("AKASH GANGA", "DELHIVERY", "DTDC", "MADHUR", "PROFESSIONAL", "SHREE MAHAVEER", "SHREE MARUTI", "SKYKING");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, courierProviders);
        spnCourierProvider.setAdapter(dataAdapter);
    }

    private void init() {
        edtTxtReceiver = findViewById(R.id.edtTxtReceiver);
        edtTxtReceiverMobile = findViewById(R.id.edtTxtReceiverMobile);
        edtTxtSender = findViewById(R.id.edtTxtSender);
        edtTxtSenderMobile = findViewById(R.id.edtTxtSenderMobile);
        edtTxtLocation = findViewById(R.id.edtTxtLocation);
        edtTxtConsignment = findViewById(R.id.edtTxtConsignment);
        spnCourierProvider = findViewById(R.id.spnCourierProvider);
        btnSelectReceiverContact = findViewById(R.id.selectReceiverContact);
        btnSelectSenderContact = findViewById(R.id.selectSenderContact);
        btnSend = findViewById(R.id.btnSend);
        selectedContact = 0;
    }

    private void selectContact() {
        btnSelectReceiverContact.setOnClickListener(view -> {
            selectedContact = RECEIVER_CODE;
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            pickContactLauncher.launch(intent);
        });
        btnSelectSenderContact.setOnClickListener(view -> {
            selectedContact = SENDER_CODE;
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            pickContactLauncher.launch(intent);
        });
    }


    private void sendMessage(AutoCompleteTextView spnCourierProvider) {
        btnSend.setOnClickListener(view -> {
            String consignmentMsg = edtTxtConsignment.getText().toString().isEmpty() ? ". Your C.No. will be shared shortly." : ". Your C.No. is " + edtTxtConsignment.getText();
            String msg = "Shipment from " + edtTxtSender.getText() + " has been booked with " + spnCourierProvider.getText() + " courier for " + edtTxtReceiver.getText() + " for " + edtTxtLocation.getText() + consignmentMsg + "\nThanks \nSR Courier \nDev Nagar \nDelhi-110005";

            if (!edtTxtReceiverMobile.getText().toString().isEmpty()) {
                Thread receiverThread = new Thread(() -> openWhatsApp("Hi " + edtTxtReceiver.getText().toString() + ",\n" + msg, edtTxtReceiverMobile.getText().toString()));
                receiverThread.start();
            }
            if (!edtTxtSenderMobile.getText().toString().isEmpty()) {
                Thread receiverThread = new Thread(() -> openWhatsApp("Hi " + edtTxtSender.getText().toString() + ",\n" + msg, edtTxtSenderMobile.getText().toString()));
                receiverThread.start();
            }
            clearData();
        });
    }

    private void clearData() {
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