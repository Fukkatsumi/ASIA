package com.fukkatsummi.asiia.controller;

import com.fukkatsummi.asiia.entity.Device;
import com.fukkatsummi.asiia.exception.DeviceNotFoundException;
import com.fukkatsummi.asiia.repository.DeviceRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceRepository repository;

    public DeviceController(DeviceRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    List <Device> getAllDevices() {
        return repository.findAll();
    }

    @PostMapping
    Device createDevice(@RequestBody Device newDevice) {
        return repository.save(newDevice);
    }

    @GetMapping("/{id}")
    EntityModel<Device> getDeviceById(@PathVariable Long id) {
        Device device = repository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));
        return new EntityModel<>(device,
                linkTo(methodOn(DeviceController.class).getAllDevices()).withRel("devices"));
    }

    @GetMapping
    Device getDeviceBySn(@RequestParam(required = false) String sn) {
        return repository.findBySn(sn);
    }

    @PutMapping("/{id}")
    Device updateDeviceById(@RequestBody Device newDevice, @PathVariable Long id) {
        return repository.findById(id)
                .map(device -> {
                    device.setSn(newDevice.getSn());
                    device.setType(newDevice.getType());
                    device.setStatus(newDevice.getStatus());
                    device.setRequest_time( new SimpleDateFormat("HH:mm:ss").format(new Date()) );
                    return repository.save(device);
                })
                .orElseGet(() -> {
                    newDevice.setId(id);
                    return repository.save(newDevice);
                });
    }

    @DeleteMapping("/{id}")
    void deleteDevice(@PathVariable Long id) {
        repository.findById(id);
    }
}
