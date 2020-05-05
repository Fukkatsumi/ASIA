#define NUM_LEDS 60
#include "FastLED.h"
#define LED_PIN 3
#define BTN_PIN 2
CRGB leds[NUM_LEDS];
byte counter;

byte color = 255;
byte rgbSpeed = 5;
byte brightness = 50;
byte modeIndex = 0;

int buttonState = 0;         // переменная для считывания состояния кнопки

void setup() {
  FastLED.addLeds<WS2812, LED_PIN, GRB>(leds, NUM_LEDS).setCorrection( TypicalLEDStrip );
  FastLED.setBrightness(brightness);
  pinMode(3, OUTPUT);

  pinMode(BTN_PIN, INPUT); 
}
byte colorIndex = 0;   
byte redColor = 0;
byte greenColor = 100;
byte blueColor = 150;
byte yellowColor = 50;


void loop() {

 // считываем значение, соответствующее состоянию кнопки:
  buttonState = digitalRead(BTN_PIN);

  // проверяем, не нажата ли кнопка.
  // если нажата, переводим buttonState в HIGH:
  if (buttonState == HIGH) {
    FastLED.clear();
      modeIndex++;
      if(modeIndex >= 6) {
        modeIndex = 0;
        }  
  } else {
  
switch(modeIndex) {
  case 0:
  break;
  
  case 1:
    colorMode();
  break;
  
  case 2:
  proxingMode();
  break;
  
  case 5:
  blinkingMode();
  break;
  
  case 4:
  dancingMode();
  break;
  
  case 3:
    rgbMode();
  break;
  }
  }
}

void rgbMode() {
if(counter >= 255) {
  counter = 0;
  }
  
    for (int i = 0; i < NUM_LEDS; i++ ) {         // от 0 до первой трети
    leds[i] = CHSV(counter + i * 2, 255, 255);  // HSV. Увеличивать HUE (цвет)
    // умножение i уменьшает шаг радуги
    }
  counter++;        // counter меняется от 0 до 255 (тип данных byte)

           // скорость движения радуги
 delay(50 );
FastLED.show(); 
  }
  

void colorMode() {
  for (int i = 0; i < NUM_LEDS; i++ ) {         // от 0 до первой трети
    leds[i] = CHSV(55, 255, 255);  // HSV. Увеличивать HUE (цвет)
    }
            delay(100);
        FastLED.show(); 
  }  


  void colorProxing(int colorIndex, int value) {
      for (int i = 0; i < NUM_LEDS; i++ ) {      
          if((colorIndex == 0) && (i % 4 == 0)){
            leds[i] = CHSV(redColor, 255, value);  
          }
          if((colorIndex == 1) && (i % 4 == 3)){
            leds[i] = CHSV(yellowColor, 255, value);  
          }
          if((colorIndex == 2) && ((i % 2 == 1) && !((i % 4) == 3))){
            leds[i] = CHSV(greenColor, 255, value);  
          }
          if((colorIndex == 3) && ((i % 2 == 0) && !(i % 4 == 0))){
            leds[i] = CHSV(blueColor, 255, value);  
          }         
          } 
  }
  
  void proxingMode() {
          if(colorIndex >= 4) {
        colorIndex = 0;
      }
      for (int j = 0; j < 200; j+=5 ) {
            colorProxing(colorIndex, j);  
        delay(30);
        FastLED.show();
      }
      
      for (int j = 200; j >= 0; j-=5 ) {    
          colorProxing(colorIndex, j);  
        delay(30);
        FastLED.show();
      }
  colorIndex++;
  }

    void blinkingMode() {
      for (int j = 0; j < 200; j+=5 ) {
        for (int i = 0; i < NUM_LEDS; i++ ) {         // от 0 до первой трети
        if(i % 4 == 0){
          leds[i] = CHSV(redColor, 255, j);  // HSV. Увеличивать HUE (цвет)
        }
         if((i % 2 == 0) && !(i % 4 == 0)) {
          leds[i] = CHSV(blueColor, 255, j);  // HSV. Увеличивать HUE (цвет)
        }
         if(i % 4 == 3) {          
            leds[i] = CHSV(yellowColor, 255, j);  // HSV. Увеличивать HUE (цвет)
        }
         if((i % 2 == 1) && !((i % 4) == 3)){
            leds[i] = CHSV(greenColor, 255, j);  // HSV. Увеличивать HUE (цвет)
        } 
    }
        delay(20);
        FastLED.show();
      }

            for (int j = 200; j >= 0; j-=5 ) {
      for (int i = 0; i < NUM_LEDS; i++ ) {         // от 0 до первой трети
        if(i % 4 == 0){
          leds[i] = CHSV(redColor, 255, j);  // HSV. Увеличивать HUE (цвет)
        } 
        if((i % 2 == 0) && !(i % 4 == 0)) {
          leds[i] = CHSV(blueColor, 255, j);  // HSV. Увеличивать HUE (цвет)
        }
        if(i % 4 == 3) {          
            leds[i] = CHSV(yellowColor, 255, j);  // HSV. Увеличивать HUE (цвет)
        } 
        if ((i % 2 == 1) && !((i % 4) == 3)) {
            leds[i] = CHSV(greenColor, 255, j);  // HSV. Увеличивать HUE (цвет)
        } 
    }
        delay(20);
        FastLED.show(); 
      }
  } 
     
      void dancingMode() {
      FastLED.clear();
      if(colorIndex >= 4) {
        colorIndex = 0;
      }
      
      for (int i = 0; i < NUM_LEDS; i++ ) {         // от 0 до первой трети
        if(i % 4 == 0){
          if(colorIndex == 0){
            leds[i] = CHSV(redColor, 255, 255);  // HSV. Увеличивать HUE (цвет)
          }
        }
        if((i % 2 == 0) && !(i % 4 == 0)) {
          if(colorIndex == 1){
            leds[i] = CHSV(blueColor, 255, 255);  // HSV. Увеличивать HUE (цвет)
          }
        }
        if(i % 4 == 3) {
          if(colorIndex == 2){
            leds[i] = CHSV(yellowColor, 255, 255);  // HSV. Увеличивать HUE (цвет)
          }
        } 
        if((i % 2 == 1) && !((i % 4) == 3)) {
          if(colorIndex == 3){
            leds[i] = CHSV(greenColor, 255, 255);  // HSV. Увеличивать HUE (цвет)
          }
        } 
    }

        delay(100);
        colorIndex++;
        FastLED.show();
  } 
