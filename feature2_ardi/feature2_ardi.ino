#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

#define SCREEN_WIDTH 128  // OLED display width
#define SCREEN_HEIGHT 64  // OLED display height

// Initialize OLED display with I2C address 0x3C
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, -1);

void setup() {
    Serial.begin(115200);

    // Initialize OLED
    if (!display.begin(SSD1306_SWITCHCAPVCC, 0x3C)) {
        Serial.println("SSD1306 initialization failed!");
        for (;;);
    }

    display.clearDisplay();
    display.setTextSize(2);
    display.setTextColor(SSD1306_WHITE);
    display.setCursor(0, 10);
    display.println("Waiting for Text...");
    display.display();
}

void loop() {
    if (Serial.available()) {
        String receivedText = Serial.readStringUntil('\n'); // Read text from Python
        Serial.println("Received: " + receivedText);

        // Clear display and print new text
        display.clearDisplay();
        display.setCursor(0, 10);
        display.println(receivedText);
        display.display();
    }
}
