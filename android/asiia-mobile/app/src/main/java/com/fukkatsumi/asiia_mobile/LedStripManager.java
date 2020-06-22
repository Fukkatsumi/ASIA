package com.fukkatsumi.asiia_mobile;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.fukkatsumi.asiia_mobile.entity.Device;
import com.fukkatsumi.asiia_mobile.entity.LedStrip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class LedStripManager extends AppCompatActivity {

    private String[] modes = new String[]{
            "RGB",
            "Color",
            "Dancing",
            "Blinking",
            "Proxying"
    };

    private String[] colorCount = new String[]{
            "1",
            "2",
            "3",
            "4"
    };

    private LinearLayout[] modeLayouts;
    private LinearLayout[] colorLayouts;

    private int currentModeIndex = 1;
    private int currentColorsCount = 1;

    SeekBar colorOneBar;
    SeekBar speedBar;
    SeekBar brightnessRGBBar;
    SeekBar brightnessBar;

    LedStrip ledStrip;

    private TextView description;
    private View.OnClickListener setup = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.on_off_btn:
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "OnOff clicked", Toast.LENGTH_SHORT);
                    toast.show();
                    Button button = (Button) v;
                    Button button1 = findViewById(R.id.setup_btn);
                    if(button.getText().equals("On")) {
                        toast = Toast.makeText(getApplicationContext(),
                                "Off", Toast.LENGTH_SHORT);
                        toast.show();
                        button1.setEnabled(false);
                        button.setText(R.string.off);

                        ledStrip.setMode(0);

                        HttpService.getInstance()
                                .getJSONApi()
                                .putStripData(ledStrip.getId(), ledStrip)
                                .enqueue(new Callback<LedStrip>() {
                                    @Override
                                    public void onResponse(@NonNull Call<LedStrip> call, @NonNull Response<LedStrip> response) {
                                        LedStrip post = response.body();
                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                post.toString(), Toast.LENGTH_SHORT);
                                        toast.show();
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<LedStrip> call, @NonNull Throwable t) {
                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                "Error occurred while getting request!", Toast.LENGTH_SHORT);
                                        toast.show();
                                        System.out.println("Error occurred while getting request!");
                                        t.printStackTrace();
                                    }
                                });

                    } else
                    if(button.getText().equals("Off")) {
                        toast = Toast.makeText(getApplicationContext(),
                                "On", Toast.LENGTH_SHORT);
                        toast.show();
                        button1.setEnabled(true);
                        button.setText(R.string.on);
                    }

                    break;
                case R.id.setup_btn:
                    String text = "";
                    switch (currentModeIndex) {
                        case 1:

                            text += Integer.toString(speedBar.getProgress()) + " ";

                            text += Integer.toString(brightnessRGBBar.getProgress());

                            ledStrip.setBrightness(brightnessRGBBar.getProgress());
                            ledStrip.setSpeed(speedBar.getProgress());
                            ledStrip.setMode(currentModeIndex);
                            break;
                        case 2:

                            switch (currentColorsCount) {
//                        case 4:
//                            SeekBar colorFourBar = findViewById(R.id.color_four);
//                            text += Integer.toString(colorFourBar.getProgress()) + " ";
//                        case 3:
//                            SeekBar colorThreeBar = findViewById(R.id.color_three);
//                            text += Integer.toString(colorThreeBar.getProgress()) + " ";
//                        case 2:
//                            SeekBar colorTwoBar = findViewById(R.id.color_two);
//                            text += Integer.toString(colorTwoBar.getProgress()) + " ";
                                case 1:

                                    text += Integer.toString(colorOneBar.getProgress()) + " ";


                                    text += Integer.toString(brightnessBar.getProgress()) + " ";

                                    ledStrip.setBrightness(brightnessBar.getProgress());
                                    ledStrip.setColor(colorOneBar.getProgress());
                                    ledStrip.setMode(currentModeIndex);
                            }
                            break;
                        case 3:
                        case 5:
                        case 4:
                            ledStrip.setMode(currentModeIndex);
                            break;
                    }

                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "You setup: " + modes[currentModeIndex - 1] + "With values: " + text, Toast.LENGTH_SHORT);
                    toast1.show();

                    Toast toast2 = Toast.makeText(getApplicationContext(),
                            ledStrip.toString(), Toast.LENGTH_SHORT);
                    toast2.show();

                    HttpService.getInstance()
                            .getJSONApi()
                            .putStripData(ledStrip.getId(), ledStrip)
                            .enqueue(new Callback<LedStrip>() {
                                @Override
                                public void onResponse(@NonNull Call<LedStrip> call, @NonNull Response<LedStrip> response) {
                                    LedStrip post = response.body();
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            post.toString(), Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                                @Override
                                public void onFailure(@NonNull Call<LedStrip> call, @NonNull Throwable t) {
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "Error occurred while getting request!", Toast.LENGTH_SHORT);
                                    toast.show();
                                    System.out.println("Error occurred while getting request!");
                                    t.printStackTrace();
                                }
                            });

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_strip_manager);
        String title = getIntent().getExtras().getString("title");
        setTitle(title);

        Toast toast = Toast.makeText(getApplicationContext(),
                title , Toast.LENGTH_SHORT);
        toast.show();

        speedBar = findViewById(R.id.rgb_speed);
        brightnessRGBBar = findViewById(R.id.rgb_brightness);
        colorOneBar = findViewById(R.id.color_one);
        brightnessBar = findViewById(R.id.color_brightness);

        HttpService.getInstance()
                .getJSONApi()
                .getStripBySn(title)
                .enqueue(new Callback<LedStrip>() {
                    @Override
                    public void onResponse(@NonNull Call<LedStrip> call, @NonNull Response<LedStrip> response) {
                        ledStrip = response.body();

                        Toast toast = Toast.makeText(getApplicationContext(),
                                ledStrip.toString(), Toast.LENGTH_SHORT);
                        toast.show();

                        speedBar.setProgress(ledStrip.getSpeed());
                        brightnessRGBBar.setProgress(ledStrip.getBrightness());
                        colorOneBar.setProgress(ledStrip.getColor());
                        brightnessBar.setProgress(ledStrip.getBrightness());
                    }

                    @Override
                    public void onFailure(@NonNull Call<LedStrip> call, @NonNull Throwable t) {

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Error occurred while getting request!", Toast.LENGTH_SHORT);
                        toast.show();
                        System.out.println("Error occurred while getting request!");
                        t.printStackTrace();
                    }

                });

        modeLayouts = new LinearLayout[]{
                findViewById(R.id.rgb_mode),
                findViewById(R.id.color_mode),
                findViewById(R.id.new_year_mode)
        };

//        colorLayouts = new LinearLayout[]{
//                findViewById(R.id.one_color),
//                findViewById(R.id.two_color),
//                findViewById(R.id.three_color),
//                findViewById(R.id.four_color)
//        };

        initModeSpinner();
        initColorCountSpinner();

        Button button = findViewById(R.id.setup_btn);
        button.setOnClickListener(setup);
        Button button1 = findViewById(R.id.on_off_btn);
        button1.setOnClickListener(setup);
        description = findViewById(R.id.description);
    }

    private void initModeSpinner() {
        final Spinner modeSpinner = findViewById(R.id.mode_spinner);

        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, modes);
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        modeSpinner.setAdapter(modeAdapter);

        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            LinearLayout layout = null;

            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                System.out.println("Selected...");

                switchLayout(selectedItemPosition);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initColorCountSpinner() {
//        final Spinner colorCountSpinner = findViewById(R.id.color_count_spinner);
//
//        ArrayAdapter<String> colorCountAdapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_spinner_item, colorCount);
//        colorCountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        colorCountSpinner.setAdapter(colorCountAdapter);
//
//        colorCountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent,
//                                       View itemSelected, int selectedItemPosition, long selectedId) {
//                System.out.println("Selected 2...");
//
//                displayColors(selectedItemPosition);
//                currentColorsCount = ++selectedItemPosition;
//
//            }
//
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
    }

    private void switchLayout(int index) {
        currentModeIndex = index + 1;
        setModeDescription();

        for (LinearLayout layout : modeLayouts) {
            layout.setVisibility(View.GONE);
        }

        if (index >= modeLayouts.length) {
            index = (modeLayouts.length - 1);
        }
        modeLayouts[index].setVisibility(View.VISIBLE);
    }

    private void displayColors(int index) {
        for (LinearLayout layout : colorLayouts) {
            layout.setVisibility(View.GONE);
        }
        for (int i = 0; i <= index; i++) {
            colorLayouts[i].setVisibility(View.VISIBLE);
        }
    }

    private void setModeDescription() {
        String text = "";
        switch (currentModeIndex) {
            case 1:
                text = "Display moving rainbow lighting.";
                break;
            case 2:
                text = "Display stable 1-4 color lighting.";
                break;
            case 3:
                text = "Classic new year mode.\n" +
                        "Display 4-color fast changing lighting.";
                break;
            case 4:
                text = "Classic new year mode.\n" +
                        "Display 4-color on-off lighting.";
                break;
            case 5:
                text = "Classic new year mode.\n" +
                        "Display 4-color lighting that slowly changes each other.";
                break;
        }
        description.setText(text);
    }

}
