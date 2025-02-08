#include <Wire.h>
#include <MPU6050.h>

MPU6050 mpu;

// Flex Sensor Pins
const int flex1 = A0;
const int flex2 = A1;
const int flex3 = A2;
const int flex4 = A3;
const int flex5 = A4;

void setup() {
    Serial.begin(115200);
    Wire.begin();
    mpu.initialize();

    if (!mpu.testConnection()) {
        Serial.println("MPU6050 connection failed!");
        while (1);
    }
}

void loop() {
    // Read Flex Sensor Values
    int flexVal1 = analogRead(flex1);
    int flexVal2 = analogRead(flex2);
    int flexVal3 = analogRead(flex3);
    int flexVal4 = analogRead(flex4);
    int flexVal5 = analogRead(flex5);

    // Read Accelerometer and Gyroscope Data
    int16_t ax, ay, az, gx, gy, gz;
    mpu.getMotion6(&ax, &ay, &az, &gx, &gy, &gz);

    // Convert raw values to standard units
    float accX = ax / 16384.0;
    float accY = ay / 16384.0;
    float accZ = az / 16384.0;
    float gyroX = gx / 131.0;
    float gyroY = gy / 131.0;
    float gyroZ = gz / 131.0;

    // Send Data via Serial in expected format
    Serial.print("Flex: ");
    Serial.print(flexVal1); Serial.print(",");
    Serial.print(flexVal2); Serial.print(",");
    Serial.print(flexVal3); Serial.print(",");
    Serial.print(flexVal4); Serial.print(",");
    Serial.print(flexVal5);

    Serial.print(" | Acc: ");
    Serial.print(accX, 2); Serial.print(",");
    Serial.print(accY, 2); Serial.print(",");
    Serial.print(accZ, 2);

    Serial.print(" | Gyro: ");
    Serial.print(gyroX, 2); Serial.print(",");
    Serial.print(gyroY, 2); Serial.print(",");
    Serial.println(gyroZ, 2);

    delay(1000); // Adjust as needed
}
