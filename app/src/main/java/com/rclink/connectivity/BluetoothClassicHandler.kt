package com.rclink.connectivity

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

class BluetoothClassicHandler {

    private var socket: BluetoothSocket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null
    private var isConnected = false

    // Standard SPP UUID for HC-05 / HC-06
    private val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    @SuppressLint("MissingPermission")
    suspend fun connect(device: BluetoothDevice): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                socket = device.createRfcommSocketToServiceRecord(SPP_UUID)
                socket?.connect()
                
                inputStream = socket?.inputStream
                outputStream = socket?.outputStream
                isConnected = true
                Log.d("BT_CLASSIC", "Connected to ${device.name}")
                true
            } catch (e: IOException) {
                Log.e("BT_CLASSIC", "Connection failed", e)
                disconnect()
                false
            }
        }
    }

    suspend fun sendData(data: String): Boolean {
        return withContext(Dispatchers.IO) {
            if (!isConnected || outputStream == null) return@withContext false
            try {
                outputStream?.write(data.toByteArray())
                true
            } catch (e: IOException) {
                Log.e("BT_CLASSIC", "Failed to send data", e)
                disconnect()
                false
            }
        }
    }

    suspend fun receiveData(onDataReceived: (String) -> Unit) {
        withContext(Dispatchers.IO) {
            val buffer = ByteArray(1024)
            var bytes: Int
            while (isConnected) {
                try {
                    bytes = inputStream?.read(buffer) ?: -1
                    if (bytes > 0) {
                        val incomingMessage = String(buffer, 0, bytes)
                        onDataReceived(incomingMessage)
                    }
                } catch (e: IOException) {
                    Log.e("BT_CLASSIC", "Input stream disconnected", e)
                    disconnect()
                    break
                }
            }
        }
    }

    fun disconnect() {
        isConnected = false
        try {
            inputStream?.close()
            outputStream?.close()
            socket?.close()
        } catch (e: IOException) {
            Log.e("BT_CLASSIC", "Error closing socket", e)
        }
        socket = null
        inputStream = null
        outputStream = null
    }
}
