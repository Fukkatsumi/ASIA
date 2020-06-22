package com.fukkatsummi.asiia.repository;

import com.fukkatsummi.asiia.entity.Device;
import com.fukkatsummi.asiia.entity.LedStrip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedStripRepository extends JpaRepository<LedStrip, Long> {
    LedStrip findBySn(String sn);
}
