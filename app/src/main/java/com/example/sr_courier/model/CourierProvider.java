package com.example.sr_courier.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum CourierProvider {
    AKASH_GANGA("AKASH GANGA", "https://www.akashganga.info/"),
    DELHIVERY("DELHIVERY", "https://www.delhivery.com/tracking"),
    DTDC("DTDC", "https://www.dtdc.in/tracking.asp"),
    MADHUR("MADHUR", "https://www.google.com/search?q=madhurcouriertracking"),
    PROFESSIONAL("PROFESSIONAL", "https://www.tpcindia.com/Network.aspx"),
    SHREE_MAHAVEER("SHREE MAHAVEER", "https://www.google.com/search?q=shreemahavircouriertracking"),
    SHREE_MARUTI("SHREE MARUTI", "https://www.shreemaruti.com/track-your-shipment/"),
    SKYKING("SKYKING", "https://skyking.co/track");

    private final String displayName;
    private final String trackingUrl;

    CourierProvider(String displayName, String trackingUrl) {
        this.displayName = displayName;
        this.trackingUrl = trackingUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    // ✅ List of provider names
    public static final List<String> PROVIDERS =
            Arrays.stream(values())
                    .map(CourierProvider::getDisplayName)
                    .collect(Collectors.toList());

    // ✅ Map of provider name → URL
    public static final Map<String, String> URL_MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(
                            CourierProvider::getDisplayName,
                            CourierProvider::getTrackingUrl
                    ));
}
