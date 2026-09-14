package com.rclink.ui.controllers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rclink.ui.theme.*

@Composable
fun HomeAutoScreen(onBack: () -> Unit) {
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
            Text("Smart Home", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Text("🏠 Online", color = SuccessNeon, fontSize = 14.sp)
        }
        
        // Rooms Tabs
        ScrollableTabRow(
            selectedTabIndex = 0,
            containerColor = DarkBackground,
            contentColor = PrimaryNeon,
            edgePadding = 24.dp
        ) {
            Tab(selected = true, onClick = {}, text = { Text("Living Room") })
            Tab(selected = false, onClick = {}, text = { Text("Bedroom") }, unselectedContentColor = TextSecondary)
            Tab(selected = false, onClick = {}, text = { Text("Kitchen") }, unselectedContentColor = TextSecondary)
            Tab(selected = false, onClick = {}, text = { Text("Outdoor") }, unselectedContentColor = TextSecondary)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Switches Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            item { SmartSwitch("Main Light", "💡", true) }
            item { SmartSwitch("Ceiling Fan", "🌀", false) }
            item { SmartSwitch("AC Unit", "❄️", true) }
            item { SmartSwitch("TV Power", "📺", false) }
            item { SmartSwitch("Water Pump", "💧", false) }
            item { SmartSwitch("Garden Lights", "🌿", false) }
        }
    }
}

@Composable
fun SmartSwitch(title: String, icon: String, initialState: Boolean) {
    var isOn by remember { mutableStateOf(initialState) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { isOn = !isOn },
        colors = CardDefaults.cardColors(
            containerColor = if (isOn) PrimaryDark.copy(alpha = 0.2f) else SurfaceDark
        ),
        shape = RoundedCornerShape(24.dp),
        border = if (isOn) androidx.compose.foundation.BorderStroke(1.dp, PrimaryNeon.copy(alpha = 0.5f)) else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(text = icon, fontSize = 32.sp)
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(if (isOn) SuccessNeon else Color.DarkGray)
                )
            }
            
            Column {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = if(isOn) PrimaryNeon else TextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = if (isOn) "ON" else "OFF", fontSize = 12.sp, color = if(isOn) SuccessNeon else TextSecondary, fontWeight = FontWeight.Bold)
            }
        }
    }
}
