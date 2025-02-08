package com.anshu.app.talktomyhand


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.anshu.app.talktomyhand.ui.theme.Modes
import com.anshu.app.talktomyhand.ui.theme.TalktomyhandTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TalktomyhandTheme {
               val navController = rememberNavController()

                // Call the MainScreen with NavController to set up navigation
                MainScreen(){

                }

                //GestureControlScreen(navController)




            }
        }
    }
}
