package com.fukkatsumi.asiia_mobile.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class LedStrip{

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("sn")
    @Expose
    private String sn;
    @SerializedName("mode")
    @Expose
    private int mode;
    @SerializedName("brightness")
    @Expose
    private int brightness;
    @SerializedName("color")
    @Expose
    private int color;
    @SerializedName("speed")
    @Expose
    private int speed;

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

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "LedStrip{" +
                "id=" + id +
                ", sn='" + sn + '\'' +
                ", mode=" + mode +
                ", brightness=" + brightness +
                ", color=" + color +
                ", speed=" + speed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LedStrip)) return false;
        if (!super.equals(o)) return false;
        LedStrip ledStrip = (LedStrip) o;
        return mode == ledStrip.mode &&
                brightness == ledStrip.brightness &&
                color == ledStrip.color &&
                speed == ledStrip.speed &&
                id.equals(ledStrip.id) &&
                sn.equals(ledStrip.sn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, sn, mode, brightness, color, speed);
    }
}
