package com.rclink.ui.controllers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rclink.ui.theme.*

@Composable
fun DroneScreen(onBack: () -> Unit) {
    var isArmed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text("◀ Back", color = PrimaryNeon, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("Mini Drone", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Text("📡 GPS: 3D Fix", color = PrimaryNeon, fontSize = 12.sp)
        }

        // Telemetry Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TelemetryBox("ALT", "12.4m")
            TelemetryBox("SPEED", "5.2m/s")
            TelemetryBox("BAT", "92%")
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Joysticks
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            JoystickPlaceholder("THROTTLE / YAW")
            JoystickPlaceholder("PITCH / ROLL")
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { isArmed = !isArmed },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isArmed) DangerNeon else SurfaceDark
                ),
                modifier = Modifier.weight(1f).height(60.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(if (isArmed) "DISARM" else "ARM MOTORS", fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { /* Emergency Stop */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCC0000)),
                modifier = Modifier.weight(1f).height(60.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("LAND NOW", color = TextPrimary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TelemetryBox(label: String, value: String) {
    Column(
        modifier = Modifier
            .background(SurfaceDark, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(label, color = TextSecondary, fontSize = 10.sp, letterSpacing = 1.sp)
        Text(value, color = PrimaryNeon, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}
