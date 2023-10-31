package com.example.weather.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weather.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController,
                   settingsViewModel: SettingsViewModel = hiltViewModel()
                   ){
    val unitToggleState by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
          WeatherAppBar(navController = navController,
              title = "Settings",
              icon = Icons.Default.ArrowBack,
              isMainScreen = false,
              )
        }
    ) {it
      Surface (
          modifier = Modifier
              .fillMaxWidth()
              .fillMaxHeight()
      ) {
         Column {
             Text(text = "Change Units of Mesurements",

                 modifier = Modifier.padding(15.dp))



         }
      }
    }
}