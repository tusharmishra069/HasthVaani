#include <Wire.h>
#include <MPU6050.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

// OLED display size
#define SCREEN_WIDTH 128
#define SCREEN_HEIGHT 64

// Create OLED display object
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, -1);

// MPU6050 object
MPU6050 mpu;

// Flex Sensor Pins
const int flex1 = A0, flex2 = A1, flex3 = A2, flex4 = A3, flex5 = A4;

void setup() {
    Serial.begin(115200);
    Wire.begin();

    // Initialize OLED
    if (!display.begin(SSD1306_SWITCHCAPVCC, 0x3C)) { 
        Serial.println("SSD1306 allocation failed");
        while (1);
    }

    // Initialize MPU6050
    mpu.initialize();
    if (!mpu.testConnection()) {
        Serial.println("MPU6050 connection failed!");
        while (1);
    }

    // Initial display
    display.clearDisplay();
    display.setTextSize(1);
    display.setTextColor(WHITE);
    display.setCursor(20, 0);
    display.print("HasthVaani");
    display.display();
    delay(2000);  // Show for 2 seconds
}

void loop() {
    // Read Flex Sensors
    int f1 = analogRead(flex1), f2 = analogRead(flex2);
    int f3 = analogRead(flex3), f4 = analogRead(flex4), f5 = analogRead(flex5);

    // Read MPU6050 Sensor
    int16_t ax, ay, az, gx, gy, gz;
    mpu.getMotion6(&ax, &ay, &az, &gx, &gy, &gz);

    // Convert raw values
    float accX = ax / 16384.0, accY = ay / 16384.0, accZ = az / 16384.0;
    float gyroX = gx / 131.0, gyroY = gy / 131.0, gyroZ = gz / 131.0;

    // Print to Serial Monitor
    Serial.print("F:"); Serial.print(f1); Serial.print(",");
    Serial.print(f2); Serial.print(",");
    Serial.print(f3); Serial.print(",");
    Serial.print(f4); Serial.print(",");
    Serial.print(f5);
    Serial.print(" | Acc:"); Serial.print(accX,2); Serial.print(",");
    Serial.print(accY,2); Serial.print(",");
    Serial.print(accZ,2);
    Serial.print(" | Gyro:"); Serial.print(gyroX,2); Serial.print(",");
    Serial.print(gyroY,2); Serial.print(",");
    Serial.println(gyroZ,2);

    // Update OLED Display
    display.clearDisplay();
    display.setTextSize(1);
    display.setTextColor(WHITE);

    // Title
    display.setCursor(20, 0);
    display.print("HasthVaani");

    // Flex Sensor Data
    display.setCursor(0, 10);
    display.print("F1:"); display.print(f1);
    display.setCursor(64, 10);
    display.print("F2:"); display.print(f2);
    display.setCursor(0, 20);
    display.print("F3:"); display.print(f3);
    display.setCursor(64, 20);
    display.print("F4:"); display.print(f4);
    display.setCursor(0, 30);
    display.print("F5:"); display.print(f5);

    // Accelerometer Data
    display.setCursor(0, 40);
    display.print("Ax:"); display.print(accX,1);
    display.setCursor(64, 40);
    display.print("Ay:"); display.print(accY,1);
    display.setCursor(0, 50);
    display.print("Az:"); display.print(accZ,1);

    // Gyroscope Data
    display.setCursor(64, 50);
    display.print("Gx:"); display.print(gyroX,1);
    display.setCursor(0, 58);
    display.print("Gy:"); display.print(gyroY,1);
    display.setCursor(64, 58);
    display.print("Gz:"); display.print(gyroZ,1);

    display.display(); // Update OLED screen
    delay(500);  // Refresh every second
}