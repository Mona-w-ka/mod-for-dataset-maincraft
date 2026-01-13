import pyautogui
import time
import os

screenWidth, screenHeight = pyautogui.size() # Получаем размер экрана.

while True:
    
    if os.path.exists("G:/minecraft mods/modee/tplog.txt"):
        pyautogui.press('f2')
        os.remove("G:/minecraft mods/modee/tplog.txt")
        print("200")
    time.sleep(3)

    
    


