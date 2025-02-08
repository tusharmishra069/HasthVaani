#include <Wire.h>
#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>

// Initialize MPU6050 sensor
Adafruit_MPU6050 mpu;

// Define flex sensor pins
const int flex1 = A0;
const int flex2 = A1;
const int flex3 = A2;
const int flex4 = A3;
const int flex5 = A4;

// MPU6050 calibration offsets
float acc_x_offset = 0, acc_y_offset = 0, acc_z_offset = 0;
float gyro_x_offset = 0, gyro_y_offset = 0, gyro_z_offset = 0;

void calibrateMPU6050() {
  Serial.println("Calibrating MPU6050... Keep the glove still.");
  
  float acc_x_sum = 0, acc_y_sum = 0, acc_z_sum = 0;
  float gyro_x_sum = 0, gyro_y_sum = 0, gyro_z_sum = 0;
  int samples = 100;

  for (int i = 0; i < samples; i++) {
    sensors_event_t a, g, temp;
    mpu.getEvent(&a, &g, &temp);
    
    acc_x_sum += a.acceleration.x;
    acc_y_sum += a.acceleration.y;
    acc_z_sum += a.acceleration.z;
    gyro_x_sum += g.gyro.x;
    gyro_y_sum += g.gyro.y;
    gyro_z_sum += g.gyro.z;

    delay(10);
  }

  acc_x_offset = acc_x_sum / samples;
  acc_y_offset = acc_y_sum / samples;
  acc_z_offset = acc_z_sum / samples;
  gyro_x_offset = gyro_x_sum / samples;
  gyro_y_offset = gyro_y_sum / samples;
  gyro_z_offset = gyro_z_sum / samples;

  Serial.println("MPU6050 Calibration Complete.");
}

void setup() {
  Serial.begin(115200);
  while (!Serial);

  Serial.println("Initializing MPU6050 & Flex Sensors...");

  if (!mpu.begin()) {
    Serial.println("Failed to find MPU6050 chip!");
    while (1);
  }

  Serial.println("MPU6050 Found!");
  mpu.setAccelerometerRange(MPU6050_RANGE_8_G);
  mpu.setGyroRange(MPU6050_RANGE_500_DEG);
  mpu.setFilterBandwidth(MPU6050_BAND_21_HZ);

  calibrateMPU6050();
}

void loop() {
  // Read flex sensor values
  int flexValues[5] = {
    analogRead(flex1),
    analogRead(flex2),
    analogRead(flex3),
    analogRead(flex4),
    analogRead(flex5)
  };

  // Get MPU6050 sensor data
  sensors_event_t a, g, temp;
  mpu.getEvent(&a, &g, &temp);

  // Apply calibration offsets
  float acc_x = a.acceleration.x - acc_x_offset;
  float acc_y = a.acceleration.y - acc_y_offset;
  float acc_z = a.acceleration.z - acc_z_offset;
  float gyro_x = g.gyro.x - gyro_x_offset;
  float gyro_y = g.gyro.y - gyro_y_offset;
  float gyro_z = g.gyro.z - gyro_z_offset;

  // Print Data in CSV Format
  Serial.print(flexValues[0]); Serial.print(",");
  Serial.print(flexValues[1]); Serial.print(",");
  Serial.print(flexValues[2]); Serial.print(",");
  Serial.print(flexValues[3]); Serial.print(",");
  Serial.print(flexValues[4]); Serial.print(",");
  
  Serial.print(acc_x); Serial.print(",");
  Serial.print(acc_y); Serial.print(",");
  Serial.print(acc_z); Serial.print(",");
  
  Serial.print(gyro_x); Serial.print(",");
  Serial.print(gyro_y); Serial.print(",");
  Serial.println(gyro_z); // Last value, so use println to move to next line

  delay(3000);  // Adjust delay for data collection rate
}

