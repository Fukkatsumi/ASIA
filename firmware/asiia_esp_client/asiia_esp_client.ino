#define FASTLED_ESP8266_RAW_PIN_ORDER
//Using Esp-01 GPIO3 as WS2812 Din
#define LED_PIN 3
#define NUM_LEDS 61

#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>
#include <FastLED.h>

const byte c_RED = 0;
const byte c_GREEN = 100;
const byte c_BLUE = 150;
const byte c_YELLOW = 50;

const byte DEFAULT_COLOR = 255;
const byte DEFAULT_BRIGHTNESS = 50;
const byte DEFAULT_SPEED = 5;

const String SERIAL_NUMBER = "ELS01";
const String DEVICE_TYPE = "Led strip";
const String DEVICE_STATUS = "Connected";

//const char* ssid = "ASUS1";
//const char* password = "Raf7dom1";
const char* ssid = "HTC";
const char* password = "Error405";

HTTPClient http;

int deviceId = 0;
int stripId = 0;

long timer = 0;
long aliveTimer = 0;
int httpResponseCode;
String requestTime;

String http_get_device_data_url = "http://192.168.1.13:8090/devices?sn=" + SERIAL_NUMBER;
String http_put_device_data_url = "http://192.168.1.13:8090/devices/";
String http_post_device_data_url = "http://192.168.1.13:8090/devices";
String http_get_strip_data_url = "http://192.168.1.13:8090/strips?sn=" + SERIAL_NUMBER;
String http_put_strip_data_url = "http://192.168.1.13:8090/strips/";

CRGB leds[NUM_LEDS];

byte modeIndex = 0;

byte colorIndex = 0;
byte rgbCounter = 0;

byte color = DEFAULT_COLOR;
byte brightness = DEFAULT_BRIGHTNESS;
byte rgbSpeed = DEFAULT_SPEED;

boolean newMode = false;

int httpGetDeviceData() {
  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("Start http connection to " + http_get_device_data_url);
    http.begin(http_get_device_data_url);
    int httpCode = http.GET();
    Serial.println("Status code: " + httpCode);
    String json = http.getString();
    Serial.println("Data: " + json);
    http.end();

    if (httpCode > 0) {
      if (compareDeviceData(json)) {
        return httpCode;
      }
    }
  }
  return 0;
}

int httpPutDeviceData() {
  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("Start http connection to " + http_put_device_data_url + deviceId);
    http.begin(http_put_device_data_url + deviceId);
    http.addHeader("Content-Type", "application/json");
    int httpCode = http.PUT(deviceDataToJson());
    Serial.println("Status code: " + httpCode);
    http.end();
    return httpCode;
  }
  return 0;
}

int httpPostDeviceData() {
  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("Start http connection to " + http_post_device_data_url);
    http.begin(http_post_device_data_url);
    http.addHeader("Content-Type", "application/json");
    int httpCode = http.POST(deviceDataToJson());
    Serial.println("Status code: " + httpCode);
    http.end();
    return httpCode;
  }
  return 0;
}

int httpGetStripData() {
  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("Start http connection to " + http_get_strip_data_url);
    http.begin(http_get_strip_data_url);
    int httpCode = http.GET();
    Serial.println("Status code: " + httpCode);
    String json = http.getString();
    Serial.println("Data: " + json);
    http.end();
    
    if (httpCode > 0) {
      if (setupNewMode(json)) {
        return httpCode;
      }
    }
  }
  return 0;
}

int httpPutStripData() {
  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("Start http connection to " + http_put_strip_data_url + stripId);
    http.begin(http_put_strip_data_url + stripId);
    http.addHeader("Content-Type", "application/json");
    int httpCode = http.PUT(stripDataToJson());
    Serial.println("Status code: " + httpCode);
    http.end();
    return httpCode;
  }
  return 0;
}

String deviceDataToJson() {
  String json = "{\"id\":\"" + String(deviceId) + "\",\"sn\":\"" + SERIAL_NUMBER + "\",\"type\":\"" + DEVICE_TYPE + "\",\"status\":\"" + DEVICE_STATUS + "\",\"request-time\":\"\"}";
  return json;
}

String stripDataToJson() {
  String json = "{\"id\":\"" + String(stripId) + "\",\"sn\":\"" + SERIAL_NUMBER + "\",\"mode\":\"" + modeIndex + "\",\"brightness\":\"" + DEFAULT_BRIGHTNESS + "\",\"color\":\"" + DEFAULT_COLOR+ "\",\"speed\":\"" + DEFAULT_SPEED + "\"}";
  return json;
}

boolean compareDeviceData(String data) {
  Serial.println("Checking data from server...");
  if (data != "") {
    const size_t bufferSize = JSON_OBJECT_SIZE(2) + JSON_OBJECT_SIZE(3) + JSON_OBJECT_SIZE(5) + JSON_OBJECT_SIZE(8) + 370;
    DynamicJsonBuffer jsonBuffer(bufferSize);
    JsonObject& root = jsonBuffer.parseObject(data);
  
    if (deviceId == 0) {
      deviceId = root["id"];
    }
    String sn = root["sn"];
    //const char* sn = root["sn"];
    //std::string str(sn);
    String type = root["type"];
    //const char* type = root["type"];
    //std::string str(type);
    if ( SERIAL_NUMBER.equals(sn) && DEVICE_TYPE.equals(type) ) {
      Serial.println("Correct data.");
      return true;
    }
  }

  Serial.println("Incorrect data.");
  return false;
}

boolean setupNewMode(String data) {
  Serial.println("Setting up new data...");
  if ( data != "" ) {
    const size_t bufferSize = JSON_OBJECT_SIZE(2) + JSON_OBJECT_SIZE(3) + JSON_OBJECT_SIZE(5) + JSON_OBJECT_SIZE(8) + 370;
    DynamicJsonBuffer jsonBuffer(bufferSize);
    JsonObject& root = jsonBuffer.parseObject(data);

    if (stripId == 0) {
      stripId = root["id"];
    }
    modeIndex = root["mode"];
    String mi = root["mode"];
    Serial.println("modeIndex " + String(modeIndex) + "root modeIndex " + String(mi));
    brightness = root["brightness"];
    Serial.println("brightness " + String(brightness));
    color = root["color"];
    Serial.println("color " + String(color));
    rgbSpeed = root["speed"];
    Serial.println("rgbSpeed " + String(rgbSpeed));
    
    newMode = true;
    Serial.print("newMode ");
    Serial.println(newMode);
    
    Serial.println("Succsess.");
    return true;
  }

  Serial.println("Failed.");
  return false;
}

void clearStrip() {
  for (int i = 1; i < NUM_LEDS; i++ ) {
    leds[i] = CHSV(0, 0, 0);
  }
  FastLED.show();
}

void colorMode() {
  for (int i = 1; i < NUM_LEDS; i++ ) {         
    leds[i] = CHSV(color, 255, 255);  
  }
  delay(100);
  FastLED.show();
}

void rgbMode() {
  if (rgbCounter >= 3) {
    rgbCounter = 0;
  }

  for (int i = 1; i < NUM_LEDS; i++ ) {      
    if(rgbCounter == 0){   
    leds[i] = CHSV(c_RED, 255, 150);  
    }
    if(rgbCounter == 1){   
    leds[i] = CHSV(c_GREEN, 255, 150);  
    }
    if(rgbCounter == 2){   
    leds[i] = CHSV(c_BLUE, 255, 150);  
    }
  }
  rgbCounter++;        
  delay(rgbSpeed);
  FastLED.show();
}

void dancingMode() {
  FastLED.clear();
  if (colorIndex >= 4) {
    colorIndex = 0;
  }

  for (int i = 1; i < NUM_LEDS; i++ ) {         
    if (i % 4 == 0) {
      if (colorIndex == 0) {
        leds[i] = CHSV(c_RED, 255, 255);  
      }
    }
    if ((i % 2 == 0) && !(i % 4 == 0)) {
      if (colorIndex == 1) {
        leds[i] = CHSV(c_BLUE, 255, 255);  
      }
    }
    if (i % 4 == 3) {
      if (colorIndex == 2) {
        leds[i] = CHSV(c_YELLOW, 255, 255);  
      }
    }
    if ((i % 2 == 1) && !((i % 4) == 3)) {
      if (colorIndex == 3) {
        leds[i] = CHSV(c_GREEN, 255, 255);  
      }
    }
  }
  delay(100);
  colorIndex++;
  FastLED.show();
}

void blinkingMode() {
  for (int j = 0; j < 200; j += 5 ) {
    for (int i = 1; i < NUM_LEDS; i++ ) {         
      if (i % 4 == 0) {
        leds[i] = CHSV(c_RED, 255, j);  
      }
      if ((i % 2 == 0) && !(i % 4 == 0)) {
        leds[i] = CHSV(c_BLUE, 255, j);  
      }
      if (i % 4 == 3) {
        leds[i] = CHSV(c_YELLOW, 255, j);  
      }
      if ((i % 2 == 1) && !((i % 4) == 3)) {
        leds[i] = CHSV(c_GREEN, 255, j);  
      }
    }
    delay(20);
    FastLED.show();
  }

  for (int j = 200; j >= 0; j -= 5 ) {
    for (int i = 1; i < NUM_LEDS; i++ ) {         
      if (i % 4 == 0) {
        leds[i] = CHSV(c_RED, 255, j);  
      }
      if ((i % 2 == 0) && !(i % 4 == 0)) {
        leds[i] = CHSV(c_BLUE, 255, j);  
      }
      if (i % 4 == 3) {
        leds[i] = CHSV(c_YELLOW, 255, j);  
      }
      if ((i % 2 == 1) && !((i % 4) == 3)) {
        leds[i] = CHSV(c_GREEN, 255, j);  
      }
    }
    delay(20);
    FastLED.show();
  }
}

void colorProxing(int colorIndex, int value) {
  for (int i = 1; i < NUM_LEDS; i++ ) {
    if ((colorIndex == 2) && (i % 4 == 0)) {
      leds[i] = CHSV(c_RED, 255, value);
    }
    if ((colorIndex == 3) && (i % 4 == 3)) {
      leds[i] = CHSV(c_YELLOW, 255, value);
    }
    if ((colorIndex == 1) && ((i % 2 == 1) && !((i % 4) == 3))) {
      leds[i] = CHSV(c_GREEN, 255, value);
    }
    if ((colorIndex == 0) && ((i % 2 == 0) && !(i % 4 == 0))) {
      leds[i] = CHSV(c_BLUE, 255, value);
    }
  }
}

void proxingMode() {
  if (colorIndex >= 4) {
    colorIndex = 0;
  }
  for (int j = 0; j < 200; j += 5 ) {
    colorProxing(colorIndex, j);
    delay(30);
    FastLED.show();
  }
  for (int j = 200; j >= 0; j -= 5 ) {
    colorProxing(colorIndex, j);
    delay(30);
    FastLED.show();
  }
  colorIndex++;
}

void displayConnectionStatus(byte color) {
  leds[0] = CHSV(color, 255, 25);
  FastLED.show();
  }

void setup() {
  Serial.begin(115200);
  Serial.println("Initializating strip... ");
  FastLED.addLeds<WS2812, LED_PIN, GRB>(leds, NUM_LEDS).setCorrection( TypicalLEDStrip );
  FastLED.setBrightness(brightness);
  pinMode(3, OUTPUT);
  FastLED.show();
  Serial.println("Complete.");

  Serial.println("Initializating wifi... ");
  displayConnectionStatus(c_RED);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting... ");
    displayConnectionStatus(c_YELLOW);
  }
  Serial.println("Complete.");
  displayConnectionStatus(c_GREEN);

  Serial.println("Connecting to server... ");
  if (httpGetDeviceData() == 200) {
    delay(1000);
    httpResponseCode = httpPutDeviceData();
    delay(1000);
    if (httpResponseCode == 200){
      Serial.println("Device connected to server.");
    }
  } else {
    Serial.println("Initializing first connection...");
    httpResponseCode = httpPostDeviceData();
    delay(1000);
    if (httpResponseCode = 200) {
      Serial.println("Succssesfull.");
      httpResponseCode = httpGetDeviceData();
      delay(1000);
      if (httpResponseCode == 200) {
        Serial.println("Device connected to server.");
      }
    }
  }
  Serial.println("Synchronize data...");
  if (httpGetStripData() == 200) {
    Serial.println("Device data already synchronized.");
  } else {
    httpResponseCode = httpPutStripData();
    delay(1000);
    if (httpResponseCode == 200) {
      Serial.println("Device data synchronized.");
    }
  }
}

void loop() {
  if (aliveTimer == 50) {
    Serial.println("Send device data to server...");
    httpPutDeviceData();
    aliveTimer = 0;
  }

  if (timer == 15) {
    Serial.println("Getting strip data from server...");
    httpGetStripData();
    timer = 0;
  }

  if (newMode) {
    Serial.println("Changing mode to: " + String(modeIndex));
    clearStrip();
    newMode = false;
    Serial.println("Setup brightness value to: " + String(brightness));
    FastLED.setBrightness(brightness);
  }

  switch (modeIndex) {
    case 0:                   break;
    case 1:  rgbMode();       break;
    case 2:  colorMode();     break;
    case 3:  dancingMode();   break;
    case 4:  blinkingMode();  break;
    case 5:  proxingMode();   break;
  }

  timer++;
  aliveTimer++;
  Serial.println("timer: " + String(timer));
  Serial.println("aliveTimer: " + String(aliveTimer));
  delay(500);
}  
