package com.fukkatsummi.asiia.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Device {

    private @Id
    @GeneratedValue
    Long id;
    private String sn;
    private String type;
    private String status;
    private String request_time;

    public Device() {
    }

    public Device(String sn, String type, String status, String request_time) {
        this.sn = sn;
        this.type = type;
        this.status = status;
        this.request_time = request_time;
    }
}