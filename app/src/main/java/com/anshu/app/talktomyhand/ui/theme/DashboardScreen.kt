@file:Suppress("NAME_SHADOWING")

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anshu.app.talktomyhand.R
import com.anshu.app.talktomyhand.ui.theme.GestureControlScreen

@Composable
fun DashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A))
            .padding(top = 24.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Section
        Row(
            modifier = Modifier.fillMaxWidth(),

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
                Text(text = "16 January 2024", fontSize = 14.sp, color = Color.Gray)
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
        GradientButton(text = "Connect your device!") {
            // Handle Bluetooth connection logic
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Help Text
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Trouble connecting to device? ", color = Color.Gray)
            Text(
                text = "Click here",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    // Handle click event
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Feature Cards with Navigation
        FeatureCard("Sign Gestures", "Tap to add, remove or update your gestures", R.drawable.hand, Color.Green) {
            navController.navigate("GestureControlScreens")
        }
        FeatureCard("Modes", "Switch from different modes in our app", R.drawable.modes, Color.Red) {
            navController.navigate("modes")
        }
        FeatureCard("Track Location", "Track your device location by tapping here", R.drawable.track, Color.Yellow) {
            navController.navigate("track_location")
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
    val gradient = Brush.horizontalGradient(colors = listOf(Color(0xFF2C2C5A), Color(0xFF12002A)))

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

@Composable
fun MainScreen(navController: NavHostController) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen(navController) }
        composable("GestureControlScreen") { GestureControlScreen(navController) }
        composable("Modes") { ModesScreen(navController) }
        composable("track_location") { TrackLocationScreen(navController) }
    }
}

// Example Placeholder Screens
@Composable
fun SignGesturesScreen(navController: NavController) {
    PlaceholderScreen(navController, "GestureControlScreen")
}

@Composable
fun ModesScreen(navController: NavController) {
    PlaceholderScreen(navController, "Modes Screen")
}

@Composable
fun TrackLocationScreen(navController: NavController) {
    PlaceholderScreen(navController, "Track Location Screen")
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlaceholderScreen(navController: NavController, screenName: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenName) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = screenName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}
