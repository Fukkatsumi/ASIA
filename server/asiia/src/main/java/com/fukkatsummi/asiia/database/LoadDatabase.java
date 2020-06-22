package com.fukkatsummi.asiia.database;

import com.fukkatsummi.asiia.entity.Device;
import com.fukkatsummi.asiia.entity.LedStrip;
import com.fukkatsummi.asiia.repository.LedStripRepository;
import lombok.extern.slf4j.Slf4j;

import com.fukkatsummi.asiia.repository.DeviceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDeviceDatabase(DeviceRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Device("TLE01", "Led strip", "disconnected", new SimpleDateFormat("HH:mm:ss").format(new Date()))));
        };
    }

    @Bean
    CommandLineRunner initLedStripDatabase(LedStripRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new LedStrip("TLE01", 1, 50, 150, 5)));
        };
    }
}

