package com.example.ecommerce.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class IntervalService {

    private final ShipmentService shipmentService;

    public IntervalService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    // update locations of shipments every 1 minute
    @Scheduled(fixedRate = 60000 )
    public void updateLocationsOfShipments() {
        shipmentService.updateLocation();
    }
}
