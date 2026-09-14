package com.rclink.connectivity

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.net.InetSocketAddress

class WifiHandler {

    private var socket: Socket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null
    var isConnected = false

    suspend fun connect(ipAddress: String, port: Int = 8080): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                socket = Socket()
                socket?.connect(InetSocketAddress(ipAddress, port), 5000)
                
                inputStream = socket?.getInputStream()
                outputStream = socket?.getOutputStream()
                isConnected = true
                Log.d("WIFI", "Connected to $ipAddress:$port")
                true
            } catch (e: IOException) {
                Log.e("WIFI", "Connection failed", e)
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
                outputStream?.flush()
                true
            } catch (e: IOException) {
                Log.e("WIFI", "Failed to send data", e)
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
                    Log.e("WIFI", "Input stream disconnected", e)
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
            Log.e("WIFI", "Error closing socket", e)
        }
        socket = null
        inputStream = null
        outputStream = null
    }
}
