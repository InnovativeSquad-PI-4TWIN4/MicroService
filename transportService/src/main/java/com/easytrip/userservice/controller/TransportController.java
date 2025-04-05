package com.easytrip.userservice.controller;

import com.easytrip.userservice.models.Transport;
import com.easytrip.userservice.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transports") // plus cohérent avec la ressource
public class TransportController {

    @Autowired
    private TransportService transportService;

    @GetMapping
    public List<Transport> getAll() {
        return transportService.getAllTransports();
    }

    @GetMapping("/{id}")
    public Transport getById(@PathVariable String id) {
        return transportService.getTransportById(id);
    }

    @PostMapping
    public Transport create(@RequestBody Transport transport) {
        return transportService.createTransport(transport);
    }

    @PutMapping("/{id}")
    public Transport update(@PathVariable String id, @RequestBody Transport updatedTransport) {
        return transportService.updateTransport(id, updatedTransport);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        transportService.deleteTransport(id);
    }

    @GetMapping("/user/{userId}")
    public List<Transport> getByUserId(@PathVariable Long userId) {
        return transportService.getTransportsByUserId(userId);
    }

}
