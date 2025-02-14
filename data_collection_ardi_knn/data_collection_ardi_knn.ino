#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include <Wire.h>

// Initialize MPU6050 object
Adafruit_MPU6050 mpu;

// Define Flex Sensor Pins
const int flex1 = A0;
const int flex2 = A1;
const int flex3 = A2;
const int flex4 = A3;
const int flex5 = A4;

void setup() {
  Serial.begin(115200);
  while (!Serial) delay(10);

  Serial.println("MPU6050 & Flex Sensor Initialization...");

  // Initialize MPU6050
  if (!mpu.begin()) {
    Serial.println("MPU6050 not detected. Check connections!");
    while (1) delay(10);
  }
  Serial.println("MPU6050 Connected!");

  // Set MPU6050 configurations
  mpu.setAccelerometerRange(MPU6050_RANGE_8_G);
  mpu.setGyroRange(MPU6050_RANGE_500_DEG);
  mpu.setFilterBandwidth(MPU6050_BAND_21_HZ);

  delay(100);
}

void loop() {
  // Read flex sensor values
  int flexValue1 = analogRead(flex1);
  int flexValue2 = analogRead(flex2);
  int flexValue3 = analogRead(flex3);
  int flexValue4 = analogRead(flex4);
  int flexValue5 = analogRead(flex5);

  // Get MPU6050 sensor data
  sensors_event_t accel, gyro, temp;
  mpu.getEvent(&accel, &gyro, &temp);

  // Format Data for Python (CSV format)
  Serial.print(flexValue1); Serial.print(",");
  Serial.print(flexValue2); Serial.print(",");
  Serial.print(flexValue3); Serial.print(",");
  Serial.print(flexValue4); Serial.print(",");
  Serial.print(flexValue5); Serial.print(",");
  Serial.print(accel.acceleration.x); Serial.print(",");
  Serial.print(accel.acceleration.y); Serial.print(",");
  Serial.print(accel.acceleration.z); Serial.print(",");
  Serial.print(gyro.gyro.x); Serial.print(",");
  Serial.print(gyro.gyro.y); Serial.print(",");
  Serial.print(gyro.gyro.z);
  Serial.println();  // End of line (for Python to read properly)

  delay(2500);  // Adjust delay as needed
}
