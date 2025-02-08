import serial

import joblib

import numpy as np



# Load trained KNN model and scaler

knn = joblib.load(r"gesture Detection\knn_model.pkl")  # Use raw string or double backslashes

scaler = joblib.load(r"gesture Detection\scaler.pkl")



# Set up Serial communication (Change 'COM3' to your actual port)

ser = serial.Serial('COM7', 230400, timeout=1)



print("Listening for real-time data...")



while True:

    try:

        raw_data = ser.readline().decode('utf-8').strip()  # Read from Serial

        

        if not raw_data or not raw_data[0].isdigit():  # Ignore non-numeric data

            continue



        values = list(map(float, raw_data.split(",")))  # Convert to float list

        values = np.array(values).reshape(1, -1)  # Reshape for prediction

        values = scaler.transform(values)  # Normalize using saved scaler

        

        # Predict the gesture

        gesture = knn.predict(values)[0]

        print(f"Predicted Gesture: {gesture}")



    except Exception as e:

        print("Error:", e)