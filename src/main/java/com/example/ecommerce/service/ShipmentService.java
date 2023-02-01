package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Shipment;
import com.example.ecommerce.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

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

    public List<Shipment> allShipments() {
        List<Shipment> shipments = shipmentRepository.findAll();
        return shipments;
    }


    public String createRandomLocation() {
        double longitude = new Random().nextDouble() * (99) + 0;
        double latitude = new Random().nextDouble() * (99) + 0;
        DecimalFormat df = new DecimalFormat("#.#####");
        return "" + df.format(longitude) + ", " + df.format(latitude) + "";
    }

    public void updateLocation() {
        List<Shipment> allShipments = shipmentRepository.findAll();
        for(Shipment shipment : allShipments) {
            shipment.setLocation(createRandomLocation());
            shipmentRepository.save(shipment);
        }
    }
}
