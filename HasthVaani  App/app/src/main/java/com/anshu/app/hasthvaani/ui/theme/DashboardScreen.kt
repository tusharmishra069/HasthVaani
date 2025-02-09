@file:Suppress("NAME_SHADOWING")

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.anshu.app.hasthvaani.R

@Composable
fun DashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))  // Dark background
            .padding(horizontal = 20.dp)
    ) {
        // Profile Section

        Spacer(modifier = Modifier.height(150.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            verticalAlignment = CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Morning, Marvin Goldener",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "16 January 2024",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White, RoundedCornerShape(50))
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Connect Button
        GradientButton(text = "Connect your device") {
            // Handle device connection
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Help Text
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Trouble connecting to device? ", color = Color.Gray, fontSize = 14.sp)
            Text(
                text = "Click here",
                color = Color.Blue,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    // Handle click event
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Feature Cards
        FeatureCard(
            title = "Sign Gestures",
            description = "Tap to add, remove or update your gestures",
            iconRes = R.drawable.hand,
            iconColor = Color(0xFF276D2B)  // Green
        ) {
            navController.navigate("GestureControlScreen")
        }

        FeatureCard(
            title = "Modes",
            description = "Switch from different modes in our app",
            iconRes = R.drawable.modes,
            iconColor = Color(0xFF4C120E)  // Red
        ) {
            navController.navigate("ModesScreen")
        }

        FeatureCard(
            title = "Track location",
            description = "Track your device location by tapping here",
            iconRes = R.drawable.track,
            iconColor = Color(0xFF453A12)  // Yellow
        ) {
            navController.navigate("TrackLocationScreen")
        }

        FeatureCard(
            title = "Voice recognition",
            description = "Record your voice and convert it into texts",
            iconRes = R.drawable.mic,
            iconColor = Color(0xFF0E1444)  // Blue
        ) {
            navController.navigate("VoiceRecognitionScreen")
        }
    }
}

// Feature Card with Navigation Support
@Composable
fun FeatureCard(title: String, description: String, iconRes: Int, iconColor: Color, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(iconColor, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = description, fontSize = 14.sp, color = Color.Gray)
            }
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
        }
    }
}

// Gradient Button
@Composable
fun GradientButton(text: String, onClick: () -> Unit) {
    val gradient = Brush.horizontalGradient(colors = listOf(Color(0xFF7C4DFF), Color(0xFF6200EA)))

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(gradient, shape = RoundedCornerShape(50.dp))
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}
