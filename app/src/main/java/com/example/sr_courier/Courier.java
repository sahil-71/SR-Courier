package com.example.sr_courier;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Courier {
    private String UID;
    private String date;
    private String consignmentNumber;
    private String courierProvider;
    private String senderName;
    private String senderMobile;
    private String receiverName;
    private String receiverMobile;
    private String destination;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public Courier(String UID, String consignmentNumber, String courierProvider, String senderName, String senderMobile, String receiverName, String receiverMobile, String destination) {
        this.UID = UID;
        this.date = formatter.format(new Date()).toString();
        this.consignmentNumber = consignmentNumber;
        this.courierProvider = courierProvider;
        this.senderName = senderName;
        this.senderMobile = senderMobile;
        this.receiverName = receiverName;
        this.receiverMobile = receiverMobile;
        this.destination = destination;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConsignmentNumber() {
        return consignmentNumber;
    }

    public void setConsignmentNumber(String consignmentNumber) {
        this.consignmentNumber = consignmentNumber;
    }

    public String getCourierProvider() {
        return courierProvider;
    }

    public void setCourierProvider(String courierProvider) {
        this.courierProvider = courierProvider;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}