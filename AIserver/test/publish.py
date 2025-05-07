import time
import random
import paho.mqtt.client as mqtt
import json
# EMQX Cloud MQTT Broker Info
BROKER = "e6902ddd.ala.dedicated.aws.emqxcloud.com"  # Change this to your EMQX broker
PORT = 1883 
USERNAME = "phuocbk22"  # Remove if no authentication
PASSWORD = "phuocbk22"  # Remove if no authentication
DEVICE_ID = "device123"  # Unique device ID
TOPIC_TEMPERATURE = "khoahuynh/feeds/V1"
TOPIC_LIGHT = "khoahuynh/feeds/V3"
TOPIC_HUMIDITY = "khoahuynh/feeds/V2"


class TemperatureSensor:
    def __init__(self, device_id):
        self.device_id = device_id
        self.temperature = None

    def read_temperature(self):
        # Simulate reading temperature from a sensor
        return random.randint(20, 30)
#Light
class LightSensor:
    def __init__(self, device_id):
        self.device_id = device_id
        self.light_intensity = None

    def read_light_intensity(self):
        # Simulate reading light intensity from a sensor
        return random.randint(0, 100)
#Humidity
class HumiditySensor:
    def __init__(self, device_id):
        self.device_id = device_id
        self.humidity = None

    def read_humidity(self):
        # Simulate reading humidity from a sensor
        return random.randint(30, 70)
    
#Connection success callback
def on_connect(client, userdata, flags, rc):
    print('Connected with result code '+str(rc))
    # Subscribe to the topic
    client.subscribe(TOPIC_TEMPERATURE)
    client.subscribe(TOPIC_LIGHT)
    client.subscribe(TOPIC_HUMIDITY)
    print("Subscribed to topic:", TOPIC_TEMPERATURE)
    print("Subscribed to topic:", TOPIC_LIGHT)
    print("Subscribed to topic:", TOPIC_HUMIDITY)

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

#Publish temperature data
sensor = TemperatureSensor(DEVICE_ID)
sensor_light = LightSensor(DEVICE_ID)
sensor_humidity = HumiditySensor(DEVICE_ID)
client.loop_start()  # Start the loop to process network events
while True:
    sensor.temperature = sensor.read_temperature()
    payload = sensor.temperature
    client.publish(TOPIC_TEMPERATURE, payload)  # Publish the temperature data
    print(f"Published: {payload}")

    sensor_light.light_intensity = sensor_light.read_light_intensity()
    payload_light = sensor_light.light_intensity
    client.publish(TOPIC_LIGHT, payload_light)  # Publish the light intensity data
    print(f"Published: {payload_light}")

    sensor_humidity.humidity = sensor_humidity.read_humidity()
    payload_humidity = sensor_humidity.humidity
    client.publish(TOPIC_HUMIDITY, payload_humidity)  # Publish the humidity data
    print(f"Published: {payload_humidity}")

    time.sleep(5)  # Wait for 5 seconds before publishing again
# Subscribe a message
