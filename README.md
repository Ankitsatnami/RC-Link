<p align="center">
  <img src="https://raw.githubusercontent.com/Ankitsatnami/RC-Link/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher.jpg" alt="RC Link Logo" width="220" />
</p>

<h1 align="center">🚀 RC Link Pro</h1>
<p align="center">
  <b>The Ultimate Universal Digital Transmitter for ESP32 & IoT Robotics</b>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Platform" />
  <img src="https://img.shields.io/badge/Status-In%20Development-FF9900?style=for-the-badge" alt="Status" />
  <img src="https://img.shields.io/badge/Kotlin-Jetpack%20Compose-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin" />
  <img src="https://img.shields.io/github/actions/workflow/status/Ankitsatnami/RC-Link/android.yml?style=for-the-badge&logo=github" alt="Build Status" />
</p>

---

## 🌟 Overview

**RC Link** is a premium, professional-grade Android controller application engineered to operate ESP32-based hardware, robotics, drones, and smart home automation systems. Featuring an ultra-realistic UI, glowing neon indicators, and tactile haptic feedback, it brings the physical feel of a high-end RC transmitter directly to your smartphone.

Whether you are communicating over **Bluetooth Classic, Bluetooth Low Energy (BLE), or Wi-Fi**, RC Link provides zero-latency command mapping and comprehensive live data monitoring.

---

## 🎮 Interactive UI Simulator

We've built a live web-based simulator so you can experience the professional grade RC Link Dashboard directly in your browser without installing the app!

<p align="center">
  <a href="https://ankitsatnami.github.io/RC-Link/">
    <img src="https://img.shields.io/badge/Launch_UI_Simulator-FF0055?style=for-the-badge&logo=googlechrome&logoColor=white&scale=1.5" alt="Launch Simulator" />
  </a>
</p>

*(To enable the simulator on your repo, go to **Settings > Pages**, set the source to `Deploy from a branch`, choose the `main` branch and `/docs` folder, and click Save!)*

---

## 📥 Direct APK Download

Click the button below to download the latest compiled Android Application directly.

<p align="center">
  <a href="https://github.com/Ankitsatnami/RC-Link/releases/download/latest/app-debug.apk">
    <img src="https://img.shields.io/badge/Download_Latest_APK-0078D4?style=for-the-badge&logo=android&logoColor=white&scale=1.5" alt="Download APK" />
  </a>
</p>

> *Note: Make sure to allow "Install from Unknown Sources" on your Android device to install the application.*

---

## 🔥 Professional Features

### 📡 **Universal Connectivity Protocol**
- **Bluetooth Classic (SPP):** Standard seamless connection for classic HC-05/ESP32 serial ports.
- **Bluetooth Low Energy (BLE):** Low-power characteristic-based mapping for modern hardware.
- **Wi-Fi (TCP/UDP):** High-bandwidth local network connectivity for complex systems.

### 🎮 **Hyper-Realistic Dashboards**
Every project gets a specialized, ultra-responsive UI dashboard built with **Jetpack Compose**:
- 🏎️ **RC Car Controller:** Dual-joystick proportional steering with throttle gradients.
- ⚽ **Scrolling Ball Robot:** 3D drag-and-tilt virtual ball interface.
- 🚤 **Marine Boat Controller:** Nautical steering and throttle interface.
- 🚁 **Mini Drone Remote:** Professional transmitter style with distinct ARM/DISARM toggles.
- 💡 **Smart LED Hub:** Smooth PWM brightness sliders with RGB color-picking wheels.
- 🏠 **Home Automation:** Animated glowing toggle switches for smart rooms.

### 🎙️ **Voice & Text Command Engine**
- **Speech-to-Signal:** Map custom spoken phrases (e.g., `"Move Forward"`) directly to ESP32 bytes (`F`).
- **Live Terminal:** Monitor all `SENT` and `RECEIVED` data in a professional scrolling hacker-style console for easy hardware debugging.

### 🛠️ **Custom Controller Builder (Coming Soon)**
Drag and drop buttons, sliders, and joysticks to build your own perfect interface and map them to your hardware!

---

## 🏗️ Architecture

RC Link separates UI logic from hardware communication using a robust, highly modular architecture:

```mermaid
graph TD
    UI[Jetpack Compose UI Dashboards] --> CM[Command Manager]
    CM --> CommM[Communication Manager]
    CommM --> BT[Bluetooth Classic]
    CommM --> BLE[Bluetooth LE]
    CommM --> WIFI[Wi-Fi TCP/UDP]
    BT --> ESP[ESP32 Hardware]
    BLE --> ESP
    WIFI --> ESP
```

---

## 👨‍💻 Contributing & Development

This project is actively maintained. The codebase uses modern Android standards:
- **Kotlin 1.9+**
- **Jetpack Compose**
- **Coroutines & StateFlow**

*(c) RC Link Team - Designed for Robotics Engineers and Hobbyists.*
