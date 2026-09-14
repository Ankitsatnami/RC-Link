package com.rclink.core.model

enum class Protocol {
    BLUETOOTH_CLASSIC,
    BLE,
    WIFI_TCP,
    WIFI_UDP
}

data class DeviceProfile(
    val id: String,
    val name: String,
    val protocol: Protocol,
    val address: String, // MAC address or IP address
    val port: Int? = null // For Wi-Fi
)

sealed class ConnectionState {
    object Disconnected : ConnectionState()
    object Connecting : ConnectionState()
    data class Connected(val device: DeviceProfile) : ConnectionState()
    data class Error(val message: String) : ConnectionState()
}
