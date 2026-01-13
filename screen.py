import pyautogui
import time
import os

screenWidth, screenHeight = pyautogui.size() # Получаем размер экрана.

while True:
    
    if os.path.exists("tplog.txt"):
        pyautogui.press('f2')
        os.remove("tplog.txt")
        print("200")
    time.sleep(3)

    
    


