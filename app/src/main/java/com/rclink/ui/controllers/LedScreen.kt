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
fun LedScreen(onBack: () -> Unit) {
    var brightness by remember { mutableStateOf(50f) }
    var isOn by remember { mutableStateOf(true) }

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
            Text("RGB LED", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Text("💡 Synced", color = SuccessNeon, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Big LED visualizer
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(if (isOn) PrimaryNeon.copy(alpha = brightness / 100f) else SurfaceDark),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(if (isOn) PrimaryNeon else Color.DarkGray)
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Controls
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Master Power", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Switch(
                        checked = isOn,
                        onCheckedChange = { isOn = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = PrimaryNeon, checkedTrackColor = PrimaryDark)
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text("Brightness: ${brightness.toInt()}%", color = TextSecondary, fontSize = 14.sp)
                Slider(
                    value = brightness,
                    onValueChange = { brightness = it },
                    valueRange = 0f..100f,
                    enabled = isOn,
                    colors = SliderDefaults.colors(
                        thumbColor = PrimaryNeon,
                        activeTrackColor = PrimaryNeon,
                        inactiveTrackColor = Color.DarkGray
                    )
                )
            }
        }
    }
}
