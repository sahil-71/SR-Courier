package com.example.sr_courier.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class WhatsAppHelper {
    public static void openWhatsAppWithImage(Context context, String msg, Uri imageUri, String mobileNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            intent.setPackage("com.whatsapp");
            intent.putExtra("jid", mobileNumber + "@s.whatsapp.net");

            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void openWhatsApp(Context context, String msg, String mobileNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + mobileNumber + "&text=" + Uri.encode(msg)));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
