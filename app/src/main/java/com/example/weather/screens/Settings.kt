package com.example.weather.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weather.model.Unit
import com.example.weather.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController,
                   settingsViewModel: SettingsViewModel = hiltViewModel()
                   ){
    var unitToggleState by remember {
        mutableStateOf(false)
    }
    val measurementUnits = listOf("Imperial (F)", "Metric (c)")
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value
    var choiceState by remember {
        mutableStateOf("")
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

             IconToggleButton(checked = !unitToggleState,
                 onCheckedChange = { value ->
                     unitToggleState = !value
                     if(unitToggleState) {
                         choiceState = "Imperial (F)"
                     } else {
                         choiceState = "Metric (C)"
                     }
                 },
                 modifier = Modifier
                     .fillMaxWidth(0.5f)
                     .clip(shape = RectangleShape)
                     .padding(5.dp)
                     .background(Color.Magenta.copy(alpha = 0.4f))
                 ) {
                Text(text = if(unitToggleState) "Fahrenheit" else "Celsius")
             }
           Button(onClick = {
                  settingsViewModel.deleteAllUnits()
               settingsViewModel.insertUnit(Unit(unit = choiceState))
           },
               modifier = Modifier
                   .padding(3.dp)
                   .align(CenterHorizontally),
               shape = RoundedCornerShape(34.dp),
               colors = ButtonDefaults.buttonColors(
                   containerColor =
                     Color(0xFFEFBE42)
               )

               ) {
            Text(text = "Save",
                modifier = Modifier.padding(4.dp),
                color = Color.White,
                fontSize = 17.sp
                )


           }
         }
      }
    }
}