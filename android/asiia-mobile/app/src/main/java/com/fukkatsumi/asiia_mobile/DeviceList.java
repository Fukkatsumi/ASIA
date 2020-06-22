package com.fukkatsumi.asiia_mobile;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.fukkatsumi.asiia_mobile.entity.Device;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeviceList extends AppCompatActivity {

    ListView listView;

    List<Device> devices;
//    ArrayList<Device> proxyTestDevices;
//    String[] proxyDevices = new String[] {
//            "Led strip 160",
//            "Arduino Uno"
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        setTitle("Devices");

//        proxyTestDevices = new ArrayList<>();
//        Device d1 = new Device();
//        d1.setSn("TeSt");
//        d1.setStatus("connected");
//        d1.setType("Test");
//        System.out.println(d1.toString());
//        proxyTestDevices.add(d1);
//        Device d2 = new Device();
//        d2.setSn("TeSt 2");
//        d2.setStatus("disconnected");
//        d2.setType("Test");
//        proxyTestDevices.add(d2);
//        System.out.println(proxyTestDevices.toString());
        this.listView = (ListView)findViewById(R.id.listView);

        HttpService.getInstance()
                .getJSONApi()
                .getDevices()
                .enqueue(new Callback<List<Device>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Device>> call, @NonNull Response<List<Device>> response) {
                        devices = response.body();

                        for (Device d: devices) {

                            Toast toast = Toast.makeText(getApplicationContext(),
                                    d.toString(), Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        initItems();
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<Device>> call, @NonNull Throwable t) {

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Error occurred while getting request!", Toast.LENGTH_SHORT);
                        toast.show();
                        System.out.println("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });

    }

    private boolean initItems(){
//        final String LOG_TAG = "myLogs";
//
//        final List<String> device_list_proxy = new ArrayList<>(Arrays.asList(proxyDevices));
//        List<String> device_list_0 = new ArrayList<>();

//        for (Device d: devices) {
//            d.setStatus("Connected");
//            device_list_0.add(d.getType() + " " + d.getSn());
//        }

//        List<String> device_list = new ArrayList<>(device_list_0);

        DeviceListAdapter listAdapter = new DeviceListAdapter(this, (ArrayList<Device>) devices);
        System.out.println(devices.toString());
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, device_list_proxy);

        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Device device = (Device) listView.getAdapter().getItem(position);
//                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = ");

                if(device.getStatus().equals("Connected")) {
                    manage(device);
                }

//                manage("Test");
                Toast toast = Toast.makeText(getApplicationContext(),
                        "itemClick: position = " + position + ", id = "
                                + id + "Other: ", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return true;
    }

    public boolean manage(Device device){
        boolean success = false;
       switch (device.getType()){
        case "Led strip":
            Intent intent = new Intent(DeviceList.this, LedStripManager.class);

            intent.putExtra("title", device.getSn());
            success = true;
            startActivity(intent);
            break;
        }
        return success;
    }
}
