package com.example.ecommerce.controller;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Shipment;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/shipment")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }


    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        List<Shipment> shipments = shipmentService.allShipments();
        return new ResponseEntity<>(shipments, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateLocation() {
        shipmentService.updateLocation();
        return new ResponseEntity<>(new ApiResponse(1, "locations for shipments has been updated"), HttpStatus.OK);
    }

}
