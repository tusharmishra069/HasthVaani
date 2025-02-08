import serial
import csv
import os

# Serial Port Configuration
port = 'COM7'  # Change as needed (e.g., '/dev/ttyUSB0' for Linux)
baud_rate = 115200  # Match with Arduino baud rate

# CSV File Setup
filename = os.path.join(os.path.dirname(__file__), 'sensor_data.csv')

# Open the serial connection
try:
    ser = serial.Serial(port, baud_rate, timeout=1)
    print(f"Connected to {port} at {baud_rate} baud rate.")
except:
    print("Error: Could not open the serial port.")
    exit()

# Create/Open the CSV file and write headers
with open(filename, mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(['Flex1', 'Flex2', 'Flex3', 'Flex4', 'Flex5', 
                      'Acc_X', 'Acc_Y', 'Acc_Z', 
                      'Gyro_X', 'Gyro_Y', 'Gyro_Z'])  # Header

    print("Recording data... Press Ctrl+C to stop.")

    try:
        while True:
            if ser.in_waiting:
                data = ser.readline().decode('utf-8').strip()  # Read and decode serial data
                
                # Expected Format: "Flex: 520,490,560,480,510 | Acc: 0.12,0.45,9.80 | Gyro: 0.01,0.02,0.03"
                if "Flex:" in data and "Acc:" in data and "Gyro:" in data:
                    try:
                        # Splitting the data
                        flex_data = data.split('|')[0].split(':')[1].strip().split(',')
                        acc_data = data.split('|')[1].split(':')[1].strip().split(',')
                        gyro_data = data.split('|')[2].split(':')[1].strip().split(',')
                        
                        # Check if all readings are valid
                        if len(flex_data) == 5 and len(acc_data) == 3 and len(gyro_data) == 3:
                            row = flex_data + acc_data + gyro_data
                            writer.writerow(row)  # Save the data
                            print(row)  # Display data in console

                    except Exception as e:
                        print(f"Error parsing data: {e}")

    except KeyboardInterrupt:
        print("\nData recording stopped by the user.")
        ser.close()
        print(f"Data saved in {filename}.")



























































































































































































































