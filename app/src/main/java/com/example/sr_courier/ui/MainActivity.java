package com.example.sr_courier.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.sr_courier.R;
import com.example.sr_courier.helpers.ContactHelper;
import com.example.sr_courier.helpers.MessageBuilder;
import com.example.sr_courier.helpers.WhatsAppHelper;
import com.example.sr_courier.model.CourierProvider;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int RECEIVER_CODE = 0;
    private static final int SENDER_CODE = 1;
    private boolean pendingSenderMessage = false;

    private EditText edtTxtSender, edtTxtSenderMobile, edtTxtReceiver, edtTxtReceiverMobile,
            edtTxtLocation, edtTxtConsignment;
    private AutoCompleteTextView spnCourierProvider;
    private Button btnSelectReceiverContact, btnSelectSenderContact, btnSend, btnTakePicture;
    private Uri photoUri;
    private int selectedContact;

    // Contact picker
    private final ActivityResultLauncher<Intent> pickContactLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri contactUri = result.getData().getData();
                    String[] details = ContactHelper.getContactDetails(this, contactUri);
                    if (details != null) {
                        if (selectedContact == RECEIVER_CODE) {
                            edtTxtReceiver.setText(details[0]);
                            edtTxtReceiverMobile.setText(details[1]);
                        } else {
                            edtTxtSender.setText(details[0]);
                            edtTxtSenderMobile.setText(details[1]);
                        }
                    }
                }
            });

    // Camera capture
    private final ActivityResultLauncher<Intent> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    String msg = MessageBuilder.buildMessage(
                            edtTxtSender.getText().toString(),
                            edtTxtReceiver.getText().toString(),
                            edtTxtLocation.getText().toString(),
                            edtTxtConsignment.getText().toString(),
                            spnCourierProvider.getText().toString(),
                            CourierProvider.URL_MAP.getOrDefault(spnCourierProvider.getText().toString(), "")
                    );

                    if (!edtTxtReceiverMobile.getText().toString().isEmpty()) {
                        WhatsAppHelper.openWhatsAppWithImage(this,
                                "Hi " + edtTxtReceiver.getText().toString() + ",\n" + msg,
                                photoUri,
                                edtTxtReceiverMobile.getText().toString());
                    }
                    if (!edtTxtSenderMobile.getText().toString().isEmpty()) {
                        WhatsAppHelper.openWhatsAppWithImage(this,
                                "Hi " + edtTxtSender.getText().toString() + ",\n" + msg,
                                photoUri,
                                edtTxtSenderMobile.getText().toString());
                    }
                    clearData();
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        createDropdown();
        selectContact();
        sendMessage();
    }

    private void init() {
        edtTxtReceiver = findViewById(R.id.edtTxtReceiver);
        edtTxtReceiverMobile = findViewById(R.id.edtTxtReceiverMobile);
        edtTxtSender = findViewById(R.id.edtTxtSender);
        edtTxtSenderMobile = findViewById(R.id.edtTxtSenderMobile);
        edtTxtLocation = findViewById(R.id.edtTxtLocation);
        edtTxtConsignment = findViewById(R.id.edtTxtConsignment);
        btnSelectReceiverContact = findViewById(R.id.selectReceiverContact);
        btnSelectSenderContact = findViewById(R.id.selectSenderContact);
        btnSend = findViewById(R.id.btnSend);
        spnCourierProvider = findViewById(R.id.spnCourierProvider);
        selectedContact = 0;

        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnTakePicture.setOnClickListener(v -> {
            File photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "courier.jpg");
            photoUri = FileProvider.getUriForFile(this, "com.example.sr_courier.fileprovider", photoFile);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            takePictureLauncher.launch(intent);
        });
    }

    private void createDropdown() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.dropdown_item, CourierProvider.PROVIDERS);
        spnCourierProvider.setAdapter(dataAdapter);
    }

    private void selectContact() {
        btnSelectReceiverContact.setOnClickListener(view -> {
            selectedContact = RECEIVER_CODE;
            pickContactLauncher.launch(new Intent(Intent.ACTION_PICK,
                    android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI));
        });
        btnSelectSenderContact.setOnClickListener(view -> {
            selectedContact = SENDER_CODE;
            pickContactLauncher.launch(new Intent(Intent.ACTION_PICK,
                    android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI));
        });
    }

    private void sendMessage() {
        btnSend.setOnClickListener(view -> {
            String msg = MessageBuilder.buildMessage(
                    edtTxtSender.getText().toString(),
                    edtTxtReceiver.getText().toString(),
                    edtTxtLocation.getText().toString(),
                    edtTxtConsignment.getText().toString(),
                    spnCourierProvider.getText().toString(),
                    CourierProvider.URL_MAP.getOrDefault(spnCourierProvider.getText().toString(), "")
            );

            if (!edtTxtReceiverMobile.getText().toString().isEmpty()) {
                WhatsAppHelper.openWhatsApp(this,
                        "Hi " + edtTxtReceiver.getText().toString() + ",\n" + msg,
                        edtTxtReceiverMobile.getText().toString());
                pendingSenderMessage = !edtTxtSenderMobile.getText().toString().isEmpty();
            }
            if (!edtTxtSenderMobile.getText().toString().isEmpty()) {
                WhatsAppHelper.openWhatsApp(this,
                        "Hi " + edtTxtSender.getText().toString() + ",\n" + msg,
                        edtTxtSenderMobile.getText().toString());
            }
            if (!pendingSenderMessage) {
                clearData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pendingSenderMessage) {
            pendingSenderMessage = false;
            String msg = MessageBuilder.buildMessage(
                    edtTxtSender.getText().toString(),
                    edtTxtReceiver.getText().toString(),
                    edtTxtLocation.getText().toString(),
                    edtTxtConsignment.getText().toString(),
                    spnCourierProvider.getText().toString(),
                    CourierProvider.URL_MAP.getOrDefault(spnCourierProvider.getText().toString(), "")
            );
            WhatsAppHelper.openWhatsApp(this,
                    "Hi " + edtTxtSender.getText().toString() + ",\n" + msg,
                    edtTxtSenderMobile.getText().toString());

            clearData();
        }
    }

    private void clearData() {
        edtTxtSender.setText("");
        edtTxtReceiver.setText("");
        edtTxtSenderMobile.setText("");
        edtTxtReceiverMobile.setText("");
        edtTxtLocation.setText("");
        edtTxtConsignment.setText("");
    }
}
