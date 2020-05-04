package com.fukkatsumi.mpsodrc_mobile;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
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

    private int currentModeIndex = 0;
    private int currentColorsCount = 1;

    private TextView description;
    private View.OnClickListener setup = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";
            switch (currentModeIndex) {
                case 0:
                    SeekBar speedBar = findViewById(R.id.rgb_speed);
                    text += Integer.toString(speedBar.getProgress()) + " ";
                    SeekBar brightnessRGBBar = findViewById(R.id.rgb_brightness);
                    text += Integer.toString(brightnessRGBBar.getProgress());

                    break;
                case 1:

                    switch (currentColorsCount) {
                        case 4:
                            SeekBar colorFourBar = findViewById(R.id.color_four);
                            text += Integer.toString(colorFourBar.getProgress()) + " ";
                        case 3:
                            SeekBar colorThreeBar = findViewById(R.id.color_three);
                            text += Integer.toString(colorThreeBar.getProgress()) + " ";
                        case 2:
                            SeekBar colorTwoBar = findViewById(R.id.color_two);
                            text += Integer.toString(colorTwoBar.getProgress()) + " ";
                        case 1:
                            SeekBar colorOneBar = findViewById(R.id.color_one);
                            text += Integer.toString(colorOneBar.getProgress()) + " ";

                            SeekBar brightnessBar = findViewById(R.id.color_brightness);
                            text += Integer.toString(brightnessBar.getProgress()) + " ";
                    }
                    break;
//                case 2:
//                    text = "Classic new year mode.\n" +
//                            "Display 4-color fast changing lighting.";
//                    break;
//                case 3:
//                    text = "Classic new year mode.\n" +
//                            "Display 4-color on-off lighting.";
//                    break;
//                case 4:
//                    text = "Classic new year mode.\n" +
//                            "Display 4-color lighting that slowly changes each other.";
//                    break;
            }
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You setup: " + modes[currentModeIndex] + "With values: " + text, Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String title = getIntent().getExtras().getString("title");
        setTitle(title);

        modeLayouts = new LinearLayout[]{
                findViewById(R.id.rgb_mode),
                findViewById(R.id.color_mode),
                findViewById(R.id.new_year_mode)
        };

        colorLayouts = new LinearLayout[]{
                findViewById(R.id.one_color),
                findViewById(R.id.two_color),
                findViewById(R.id.three_color),
                findViewById(R.id.four_color)
        };

        initModeSpinner();
        initColorCountSpinner();

        Button button = findViewById(R.id.setup_btn);
        button.setOnClickListener(setup);

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
        final Spinner colorCountSpinner = findViewById(R.id.color_count_spinner);

        ArrayAdapter<String> colorCountAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, colorCount);
        colorCountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        colorCountSpinner.setAdapter(colorCountAdapter);

        colorCountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                System.out.println("Selected 2...");

                displayColors(selectedItemPosition);
                currentColorsCount = ++selectedItemPosition;

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void switchLayout(int index) {
        currentModeIndex = index;
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
            case 0:
                text = "Display moving rainbow lighting.";
                break;
            case 1:
                text = "Display stable 1-4 color lighting.";
                break;
            case 2:
                text = "Classic new year mode.\n" +
                        "Display 4-color fast changing lighting.";
                break;
            case 3:
                text = "Classic new year mode.\n" +
                        "Display 4-color on-off lighting.";
                break;
            case 4:
                text = "Classic new year mode.\n" +
                        "Display 4-color lighting that slowly changes each other.";
                break;
        }
        description.setText(text);
    }
}
