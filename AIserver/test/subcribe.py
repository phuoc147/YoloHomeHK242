import time
import random
import paho.mqtt.client as mqtt
import json
# EMQX Cloud MQTT Broker Info
BROKER = "j2271a15.ala.dedicated.aws.emqxcloud.com"  # Change this to your EMQX broker
PORT = 1883 
USERNAME = "yolohome-subscriber"  # Remove if no authentication
PASSWORD = "subscriber"  # Remove if no authentication
TOPIC = "/temperature"
DEVICE_ID = "device123"  # Unique device ID


class TemperatureSensor:
    def __init__(self, device_id):
        self.device_id = device_id
        self.temperature = None

    def read_temperature(self):
        # Simulate reading temperature from a sensor
        return random.randint(20, 30)
#Connection success callback
def on_connect(client, userdata, flags, rc):
    print('Connected with result code '+str(rc))
    client.subscribe(TOPIC)  # Subscribe to the topic

# Message receiving callback
def on_message(client, userdata, msg):
    print(msg.topic+" "+str(msg.payload))

client = mqtt.Client()

client.username_pw_set(USERNAME, PASSWORD)  # Set username and password
# Specify callback function
client.on_connect = on_connect
client.on_message = on_message

# Establish a connection
client.connect(BROKER, PORT, 60)
# Subscribe a message
client.loop_forever()
