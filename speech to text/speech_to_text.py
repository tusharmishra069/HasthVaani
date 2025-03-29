import speech_recognition as sr
import serial
import time

arduino = serial.Serial('COM7', 115200, timeout=1)
time.sleep(2)  #  time for the connection to establish

def speech_to_text():
    recognizer = sr.Recognizer()

    with sr.Microphone() as source:
        print("Please speak something...")
        recognizer.adjust_for_ambient_noise(source)
        audio = recognizer.listen(source)

    try:
        text = recognizer.recognize_google(audio)
        print("You said:", text)

        arduino.write((text + "\n").encode())  
    except sr.UnknownValueError:
        print("Sorry, I could not understand the audio.")
    except sr.RequestError as e:  
        print(f"Could not get request; {e}")

if __name__ == "__main__":
    while True:
        speech_to_text()
