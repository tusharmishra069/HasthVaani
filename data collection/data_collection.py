import serial
import pandas as pd
import time

# Serial Port Configuration
SERIAL_PORT = "COM7"  
BAUD_RATE = 115200
OUTPUT_FILE = "sensor_data.csv"

# Connect to Arduino
try:
    ser = serial.Serial(SERIAL_PORT, BAUD_RATE, timeout=1)
    time.sleep(2)  # Allow time for connection
    print(f"Connected to {SERIAL_PORT} at {BAUD_RATE} baud.")
except Exception as e:
    print(f"Error: {e}")
    exit()

# Define column names
columns = ["Flex1", "Flex2", "Flex3", "Flex4", "Flex5", "AccX", "AccY", "AccZ", "GyroX", "GyroY", "GyroZ"]

# Create CSV file with headers
df = pd.DataFrame(columns=columns)
df.to_csv(OUTPUT_FILE, index=False)

# Data collection loop
try:
    while True:
        line = ser.readline().decode("utf-8").strip()
        if line:
            # Parse Arduino Serial Data
            try:
                parts = line.split(",")
                if len(parts) == 11:
                    data = list(map(float, parts))  # Convert values to float
                    print("Data Received:", data)
                    
                    # Append to CSV
                    df = pd.DataFrame([data], columns=columns)
                    df.to_csv(OUTPUT_FILE, mode='a', header=False, index=False)
            except Exception as e:
                print(f"Data Parsing Error: {e}")
except KeyboardInterrupt:
    print("\nData collection stopped.")
    ser.close()
