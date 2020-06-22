package com.fukkatsummi.asiia.controller;

import com.fukkatsummi.asiia.entity.LedStrip;
import com.fukkatsummi.asiia.exception.LedStripNotFoundException;
import com.fukkatsummi.asiia.repository.LedStripRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/strips")
public class LedStripController {
    private final LedStripRepository repository;

    public LedStripController(LedStripRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    List<LedStrip> getAllLedStrips() {
        return repository.findAll();
    }

    @PostMapping
    LedStrip createLedStrip(@RequestBody LedStrip newLedStrip) {
        return repository.save(newLedStrip);
    }

    @GetMapping("/{id}")
    EntityModel<LedStrip> getLedStripById(@PathVariable Long id) {
        LedStrip ledStrip = repository.findById(id)
                .orElseThrow(() -> new LedStripNotFoundException(id));
        return new EntityModel<>(ledStrip,
                linkTo(methodOn(LedStripController.class).getAllLedStrips()).withRel("devices"));
    }

    @GetMapping
    LedStrip getLedStripBySn(@RequestParam(required = false) String sn) {
        return repository.findBySn(sn);
    }

    @PutMapping("/{id}")
    LedStrip updateLedStripById(@RequestBody LedStrip newLedStrip, @PathVariable Long id) {
        return repository.findById(id)
                .map(ledStrip -> {
                    ledStrip.setSn(newLedStrip.getSn());
                    ledStrip.setMode(newLedStrip.getMode());
                    ledStrip.setBrightness(newLedStrip.getBrightness());
                    ledStrip.setColor(newLedStrip.getColor());
                    ledStrip.setSpeed(newLedStrip.getSpeed());
                    return repository.save(ledStrip);
                })
                .orElseGet(() -> {
                    newLedStrip.setId(id);
                    return repository.save(newLedStrip);
                });
    }

    @DeleteMapping("/{id}")
    void deleteLedStrip(@PathVariable Long id) {
        repository.findById(id);
    }
}
