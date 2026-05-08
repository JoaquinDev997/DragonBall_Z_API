package com.example.dbzapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dbzapi.ui.theme.DBZAPITheme
import com.example.dbzapi.ui.theme.DragonBallZDetailScreen
import com.example.dbzapi.ui.theme.DragonBallZListScreen
import com.example.dbzapi.viewModel.DragonBallZViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DBZAPITheme {
                val navController = rememberNavController()
                val viewModel: DragonBallZViewModel = viewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "list",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("list") {
                            DragonBallZListScreen(
                                viewModel = viewModel,
                                onCharacterClick = { character ->
                                    viewModel.selectCharacter(character)
                                    navController.navigate("detail")
                                }
                            )
                        }

                        composable("detail") {
                            val selectedChar = viewModel.selectedCharacter
                            if (selectedChar != null) {
                                DragonBallZDetailScreen(
                                    character = selectedChar,
                                    onBack = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
