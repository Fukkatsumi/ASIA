package com.fukkatsumi.asiia_mobile.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Device {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("sn")
    @Expose
    private String sn;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("request_time")
    @Expose
    private String request_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", sn='" + sn + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", request_time='" + request_time + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return id.equals(device.id) &&
                sn.equals(device.sn) &&
                type.equals(device.type) &&
                status.equals(device.status) &&
                request_time.equals(device.request_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sn, type, status, request_time);
    }
}
