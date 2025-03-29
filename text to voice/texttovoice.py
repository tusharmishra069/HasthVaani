import serial
import joblib
import numpy as np
import pyttsx3

knn = joblib.load(r"gesture Detection\knn_model.pkl")
scaler = joblib.load(r"gesture Detection\scaler.pkl")


ser = serial.Serial('COM7', 115200, timeout=0.05)  
engine = pyttsx3.init()

print("Listening for real-time data...")

while True:
    try:
        raw_data = ser.readline().decode(errors='ignore').strip()  
        
        if not raw_data or not raw_data[0].isdigit():  # Ignore non-numeric data
            continue

        values = np.fromstring(raw_data, sep=',')  # Convert to float array
        values = values.reshape(1, -1)  # Reshape for prediction
        values = scaler.transform(values)  # Normalize using saved scaler
        
        # Predict the gesture
        gesture = knn.predict(values)[0]
        print(f"Predicted Gesture: {gesture}")
        
        # Convert gesture to speech
        engine.say(f"{gesture}")
        engine.runAndWait()

    except Exception as e:
        print("Error:", e)
