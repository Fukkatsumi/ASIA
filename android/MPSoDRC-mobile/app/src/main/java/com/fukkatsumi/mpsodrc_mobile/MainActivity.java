package com.fukkatsumi.mpsodrc_mobile;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    String[] devices = new String[] {
            "Led strip 160",
            "Arduino Uno"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Devices");

        this.listView = (ListView)findViewById(R.id.listView);
        initItems();
    }

    private void initItems(){
        final String LOG_TAG = "myLogs";

        final List<String> device_list = new ArrayList<>(Arrays.asList(devices));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, device_list);

        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedFromList = (String) listView.getItemAtPosition(position);
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id + "Other: " + selectedFromList);

                manage(selectedFromList);
            }
        });
    }

    private void manage(String text){
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);

        intent.putExtra("title", text);
        startActivity(intent);
    }
}
