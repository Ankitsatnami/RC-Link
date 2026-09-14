package com.rclink.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rclink.ui.theme.PrimaryNeon
import com.rclink.ui.theme.SurfaceDark
import com.rclink.ui.theme.TextSecondary

@Composable
fun DashboardScreen(onNavigateToController: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        HeaderSection()
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Select Project",
            fontSize = 18.sp,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { ProjectCard("🏎️", "RC Car", "Proportional Steering") { onNavigateToController("car") } }
            item { ProjectCard("🚁", "Mini Drone", "6-Axis Control") { onNavigateToController("drone") } }
            item { ProjectCard("🏠", "Smart Home", "IoT Toggles") { onNavigateToController("home") } }
            item { ProjectCard("💡", "RGB LED", "PWM Dimming") { onNavigateToController("led") } }
            item { ProjectCard("⚽", "Ball Robot", "3D Rolling") { onNavigateToController("ball") } }
            item { ProjectCard("🚤", "Water Boat", "Marine Interface") { onNavigateToController("boat") } }
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryNeon)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "RC Link", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                Text(text = "PRO", fontSize = 12.sp, color = PrimaryNeon, fontWeight = FontWeight.ExtraBold)
            }
        }
        
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🔴", fontSize = 10.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Not Connected", fontSize = 12.sp, color = TextSecondary)
            }
        }
    }
}

@Composable
fun ProjectCard(icon: String, title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = icon, fontSize = 40.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            Text(text = subtitle, fontSize = 12.sp, color = TextSecondary)
        }
    }
}
