package com.example.weather.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weather.data.DataOrException
import com.example.weather.model.Weather
import com.example.weather.model.WeatherItem
import com.example.weather.navigation.WeatherScreens
import com.example.weather.utils.formatDate
import com.example.weather.utils.formatDecimals
import com.example.weather.widgets.HumidityWindPressureRow
import com.example.weather.widgets.SunsetSunRiseRow
import com.example.weather.widgets.WeatherAppBar
import com.example.weather.widgets.WeatherDetailRow
import com.example.weather.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?
){

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(initialValue = DataOrException(loading = true))  {
        value = mainViewModel.getWeatherData(city.toString())
    }.value
    print(weatherData.data.toString())

    if(weatherData.loading == true){
        CircularProgressIndicator()
    } else if(weatherData.data != null){
        ShowData(weather = weatherData.data!!, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowData(weather: Weather, navController: NavController){
  Scaffold(topBar = {
  WeatherAppBar(title = weather.city.name + ",${weather.city.country}",
      icon = Icons.Default.ArrowBack,
      navController = navController,

      onAddActionClicked = {
       navController.navigate(WeatherScreens.SearchScreen.name)
      }
      )

  }) {it
      val imageUrl = "https://openweathermap.org/img/wn/${weather!!.list[0].weather[0].icon}.png"

      Column (
          Modifier
              .padding(4.dp)
              .fillMaxWidth(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
      ) {
          Text(text = formatDate(weather.list[0].dt),
              style = MaterialTheme.typography.titleSmall,
              color = Color.Black,
              fontWeight = FontWeight.SemiBold,
              modifier = Modifier.padding(top = 40.dp, bottom = 20.dp)
              )
          Surface (
              modifier = Modifier
                  .padding(4.dp)
                  .size(200.dp),
              shape = CircleShape,
              color = Color(0xFFFFC400)
          ){
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
              Text(text = formatDecimals(weather.list[0].temp.day ) + "°", style = MaterialTheme.typography.headlineSmall,
                  fontWeight = FontWeight.ExtraBold)
                Text(text = "Snow", fontStyle = FontStyle.Italic)

            }
          }
          HumidityWindPressureRow(weather = weather!!.list[0])
          Divider()
          SunsetSunRiseRow(weather = weather!!.list[0])
          Text(text = "This Week",
              style = MaterialTheme.typography.bodySmall,
              fontWeight = FontWeight.Bold,
              )
          Surface(modifier = Modifier
              .fillMaxWidth()
              .fillMaxHeight(),
              color = Color(0xFFEEF1EF),
              shape = RoundedCornerShape(size = 14.dp),
              ){
             LazyColumn( modifier = Modifier.padding(2.dp),
                 contentPadding = PaddingValues(1.dp)
                 ){
                 items(items = weather!!.list ){
                     item: WeatherItem ->
                     WeatherDetailRow(weather = item)
                 }
                 
             }
          }
      }
  }
}
