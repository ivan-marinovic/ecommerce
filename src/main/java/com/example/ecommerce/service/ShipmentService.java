package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Shipment;
import com.example.ecommerce.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public void createShipment(Order order) {
        Shipment shipment = new Shipment();
        shipment.setOrder(order);
        shipment.setLocation(null);
        shipment.setPayed(false);
        shipmentRepository.save(shipment);
    }

}
