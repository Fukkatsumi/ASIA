package com.fukkatsummi.asiia.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class LedStrip {
    private @Id
    @GeneratedValue
    Long id;

    private String sn;
    private int mode;
    private int brightness;
    private int color;
    private int speed;

    public LedStrip(){
    }

    public LedStrip(String sn, int mode, int brightness, int color, int speed) {
        this.sn = sn;
        this.mode = mode;
        this.brightness = brightness;
        this.color = color;
        this.speed = speed;
    }
}
