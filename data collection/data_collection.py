import serial
import csv
import time

# Define Serial Port (Change based on your setup)
SERIAL_PORT = "COM7"  # Windows Example (Change if needed)
# SERIAL_PORT = "/dev/ttyUSB0"  # Linux/Mac Example
BAUD_RATE = 115200  # Ensure this matches Arduino

# Open serial connection
try:
    ser = serial.Serial(SERIAL_PORT, BAUD_RATE, timeout=1)
    print(f"Connected to {SERIAL_PORT} at {BAUD_RATE} baud rate.")
except Exception as e:
    print(f"Error: {e}")
    exit()

# Define CSV File Name
csv_filename = "gesture_data.csv"

# CSV Headers
headers = ["Flex1", "Flex2", "Flex3", "Flex4", "Flex5", "Acc_X", "Acc_Y", "Acc_Z", "Gyro_X", "Gyro_Y", "Gyro_Z", "Gesture"]

# Open CSV file in append mode (so new data gets added)
with open(csv_filename, mode="a", newline="") as file:
    writer = csv.writer(file)
    
    # Write headers if the file is empty
    if file.tell() == 0:
        writer.writerow(headers)

    print("Ready to collect data. Make a gesture and press ENTER.")

    while True:
        input("Press ENTER to record data for a new gesture (or CTRL+C to stop)...")
        gesture_label = input("Enter gesture name: ")  # Get gesture name from user

        for _ in range(10):  # Collect 10 samples per gesture
            raw_data = ser.readline().decode().strip()  # Read from serial
            
            if raw_data:
                try:
                    print(f"Raw Data: {raw_data}")
                    
                    # Convert received data into a list
                    data_list = list(map(float, raw_data.split(",")))

                    # Ensure data format is correct
                    if len(data_list) == 10:
                        data_list.append(gesture_label)  # Add gesture label
                        writer.writerow(data_list)  # Write data to CSV
                        print("Data saved:", data_list)
                    else:
                        print("Invalid data format received.")

                except ValueError as e:
                    print(f"Data Conversion Error: {raw_data}, Error: {e}")

        print(f"Gesture '{gesture_label}' recorded successfully!")

print("\nData collection completed. File saved as:", csv_filename)
ser.close()