package com.fukkatsumi.asiia_mobile;

import com.fukkatsumi.asiia_mobile.entity.Device;
import com.fukkatsumi.asiia_mobile.entity.LedStrip;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface JSONPlaceHolderApi {

    @GET("/devices/all")
    public Call<List<Device>> getDevices();

    @GET("/devices/{id}}")
    public Call<Device> getDeviceById(@Path("id") long id);

    @GET("/devices")
    public Call<Device> getDeviceBySn(@Query("sn") String sn);

    @GET("/strips")
    public Call<LedStrip> getStripBySn(@Query("sn") String sn);

    @PUT("/strips/{id}")
    public Call<LedStrip> putStripData(@Path("id") long id, @Body LedStrip data);
}

