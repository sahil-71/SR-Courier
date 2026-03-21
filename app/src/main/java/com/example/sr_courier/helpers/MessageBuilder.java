package com.example.sr_courier.helpers;

import com.example.sr_courier.model.CourierProvider;

public class MessageBuilder {
    public static String buildMessage(String sender, String receiver, String location,
                                      String consignment, String provider, String url) {
        String consignmentMsg = consignment.isEmpty()
                ? ". Your C.No. will be shared shortly."
                : ". Your C.No. is " + consignment;

        String signOffMessage = "SR Courier";
        if(provider.equals(CourierProvider.AKASH_GANGA.getDisplayName())) {
            signOffMessage = "AGC";
        }

        return "Shipment from " + sender + " has been booked with " + provider +
                " courier for " + receiver + " for " + location + consignmentMsg +
                "\nTrack your courier at " + url +
                "\nThanks \n" + signOffMessage + "\nDev Nagar \nDelhi-110005";
    }
}
