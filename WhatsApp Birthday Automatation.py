import json

import pywhatkit
import time
import os
import sys
import datetime

current_time = datetime.datetime.now()

with open("C:\\Users\\Vargha\\Desktop\\Contacts.json") as file:
    contacts = json.load(file)



def send_message(name):
    for key1 in contacts:
        number = key1["Mobile Phone"]
        if name == key1["Display Name"]:
            first_name = key1["First Name"]
            pywhatkit.sendwhatmsg(number,
                                  f"tavalodet mobarak {first_name} jan ishala Hamishe moafagh bashi va to masire pishraft bashi ❤️🎊🎉❤️",
                                  current_time.hour, current_time.minute + 1, wait_time=20)


target = input("Input Name: ")
send_message(target)
