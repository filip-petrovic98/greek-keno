package com.mozzartbet.greekkeno.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.mozzartbet.greekkeno.core.data.repository.draws.IDrawsRepository
import com.mozzartbet.greekkeno.core.ui.GreekKenoTheme
import com.mozzartbet.greekkeno.ui.GreekKenoApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var drawsRepository: IDrawsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GreekKenoTheme {
                val navController = rememberNavController()
                GreekKenoApp(navController)
            }
        }
    }
}
