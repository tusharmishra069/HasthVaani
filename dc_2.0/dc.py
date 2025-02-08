import serial
import csv
import time

# Set up serial connection (Change 'COM3' to your port, e.g., '/dev/ttyUSB0' for Linux)
ser = serial.Serial('COM7', 115200, timeout=1)
time.sleep(2)  # Allow time for the connection to establish

# Open CSV file for writing data
csv_filename = "gesture_data.csv"

# Define column headers
columns = [
    "Flex1", "Flex2", "Flex3", "Flex4", "Flex5",
    "AccX", "AccY", "AccZ", "GyroX", "GyroY", "GyroZ",
    "Gesture"
]

# Create CSV file and write headers if not already created
try:
    with open(csv_filename, "x", newline="") as f:
        writer = csv.writer(f)
        writer.writerow(columns)
except FileExistsError:
    pass  # File already exists, no need to overwrite headers

while True:
    gesture_name = input("Enter gesture name (or 'exit' to stop): ").strip()
    
    if gesture_name.lower() == "exit":
        print("Exiting data collection.")
        break

    print(f"Collecting 20 samples for gesture: {gesture_name}")

    collected_samples = 0
    with open(csv_filename, "a", newline="") as f:
        writer = csv.writer(f)
        
        while collected_samples < 20:
            try:
                line = ser.readline().decode('utf-8').strip()
                
                if line:
                    data = line.split(",")

                    if len(data) == 11:  # Ensure valid data is received
                        data.append(gesture_name)  # Append gesture label
                        writer.writerow(data)
                        collected_samples += 1
                        print(f"Sample {collected_samples}/20: {data}")

            except Exception as e:
                print("Error:", e)

    print(f"Finished collecting 20 samples for {gesture_name}.\n")

ser.close()