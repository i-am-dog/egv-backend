package com.ethgasviewer.server.grabber;

import com.ethgasviewer.server.properties.GrabProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static com.ethgasviewer.server.Application.GRAB_PROFILE;

@Service
//@Profile({GRAB_PROFILE})
public class NotificationService {
    private static final String PRICE_MSG = "Price changed from %.2f to %.2f (%.1f%%)";

    private final GrabProperties properties;

    private double lastPrice = 0.0;


    public NotificationService(GrabProperties properties) {
        this.properties = properties;
    }


    public String priceNotification(double currentPrice) {
        if (lastPrice == 0.0 || currentPrice <= 0.0) {
            lastPrice = currentPrice;
            return null;
        }

        double diff = currentPrice - lastPrice;
        double percentDiff = (diff / lastPrice) * 100;

        String message = null;
        if (Math.abs(percentDiff) > properties.getPriceBigMovePercent()) {
            message = String.format(PRICE_MSG, lastPrice, currentPrice, percentDiff);
        }
        lastPrice = currentPrice;
        return message;

    }

}
