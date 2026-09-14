package com.rclink.connectivity

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import android.util.Log
import java.util.UUID

class BleHandler(private val context: Context) {

    private var bluetoothGatt: BluetoothGatt? = null
    private var writeCharacteristic: BluetoothGattCharacteristic? = null
    var isConnected = false

    // ESP32 standard UART Service UUIDs
    private val UART_SERVICE_UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E")
    private val UART_TX_UUID = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E") // RX on ESP
    private val UART_RX_UUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E") // TX on ESP

    private val gattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                isConnected = true
                Log.d("BLE", "Connected to GATT server.")
                gatt.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                isConnected = false
                Log.d("BLE", "Disconnected from GATT server.")
            }
        }

        @SuppressLint("MissingPermission")
        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                val service = gatt.getService(UART_SERVICE_UUID)
                writeCharacteristic = service?.getCharacteristic(UART_TX_UUID)
                val notifyCharacteristic = service?.getCharacteristic(UART_RX_UUID)
                
                if (notifyCharacteristic != null) {
                    gatt.setCharacteristicNotification(notifyCharacteristic, true)
                }
            } else {
                Log.w("BLE", "onServicesDiscovered received: $status")
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            if (characteristic.uuid == UART_RX_UUID) {
                val received = String(value)
                Log.d("BLE", "Received: $received")
                // TODO: Route to CommunicationManager
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun connect(device: BluetoothDevice) {
        bluetoothGatt = device.connectGatt(context, false, gattCallback)
    }

    @SuppressLint("MissingPermission")
    fun sendData(data: String) {
        if (!isConnected || writeCharacteristic == null || bluetoothGatt == null) return
        
        writeCharacteristic?.let {
            it.value = data.toByteArray()
            bluetoothGatt?.writeCharacteristic(it)
        }
    }

    @SuppressLint("MissingPermission")
    fun disconnect() {
        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
        isConnected = false
    }
}
