package com.rclink.core.command

import com.rclink.core.communication.CommunicationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CommandLog(
    val timestamp: Long,
    val direction: Direction,
    val data: String
) {
    enum class Direction { SENT, RECEIVED }
    
    fun getFormattedTime(): String {
        val sdf = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}

class CommandManager(private val communicationManager: CommunicationManager) {
    
    private val _logs = MutableStateFlow<List<CommandLog>>(emptyList())
    val logs: StateFlow<List<CommandLog>> = _logs

    fun sendCommand(command: String) {
        val success = communicationManager.sendData(command)
        if (success) {
            addLog(CommandLog.Direction.SENT, command)
        } else {
            addLog(CommandLog.Direction.SENT, "FAILED: $command")
        }
    }
    
    fun logReceivedData(data: String) {
        addLog(CommandLog.Direction.RECEIVED, data)
    }

    private fun addLog(direction: CommandLog.Direction, data: String) {
        val newLog = CommandLog(System.currentTimeMillis(), direction, data)
        val currentLogs = _logs.value.toMutableList()
        currentLogs.add(0, newLog) // Add to top
        if (currentLogs.size > 100) {
            currentLogs.removeLast()
        }
        _logs.value = currentLogs
    }
    
    fun clearLogs() {
        _logs.value = emptyList()
    }
}
