package com.rclink.core.communication

import com.rclink.core.model.ConnectionState
import com.rclink.core.model.DeviceProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface CommunicationManager {
    val connectionState: StateFlow<ConnectionState>
    val receivedData: StateFlow<String>

    fun connect(device: DeviceProfile)
    fun disconnect()
    fun sendData(data: String): Boolean
}

class CommunicationManagerImpl : CommunicationManager {
    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    override val connectionState: StateFlow<ConnectionState> = _connectionState

    private val _receivedData = MutableStateFlow("")
    override val receivedData: StateFlow<String> = _receivedData

    override fun connect(device: DeviceProfile) {
        // TODO: Implement Bluetooth/Wi-Fi connection logic
        _connectionState.value = ConnectionState.Connecting
        // Mock connection success
        _connectionState.value = ConnectionState.Connected(device)
    }

    override fun disconnect() {
        // TODO: Implement disconnect logic
        _connectionState.value = ConnectionState.Disconnected
    }

    override fun sendData(data: String): Boolean {
        if (_connectionState.value is ConnectionState.Connected) {
            // TODO: Write to output stream
            return true
        }
        return false
    }
}
