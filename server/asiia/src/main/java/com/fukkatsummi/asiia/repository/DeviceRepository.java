package com.fukkatsummi.asiia.repository;

import com.fukkatsummi.asiia.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findBySn(String sn);
}
