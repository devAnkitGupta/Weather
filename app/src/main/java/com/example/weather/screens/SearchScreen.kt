package com.example.weather.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weather.navigation.WeatherScreens
import com.example.weather.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController){
    Scaffold (topBar = {
        WeatherAppBar(
            "Search",
            navController = navController,
            icon =  Icons.Default.ArrowBack,
            isMainScreen = false,
            onButtonClicked = {
                navController.popBackStack()
            })
    }){
        it
   Surface {
       Column(
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Box(
               modifier = Modifier.size(
                   height = 50.dp,
                   width = 4.dp
               )
           )
          SearchBar(modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)
              .align(Alignment.CenterHorizontally),
          ){
              mCity ->
           navController.navigate(WeatherScreens.MainScreen.name + "/$mCity")
          }
       }
   }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {}
){
    Column {
    val searchQueryState = rememberSaveable{
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember (searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }
        CommonTextField(
            valueState = searchQueryState,
            placeholder = "Seattle",
            onAction = KeyboardActions {
                if(!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(valueState: MutableState<String>,
                    placeholder: String,
                    keyboardType: KeyboardType = KeyboardType.Text,
                    imeAction: ImeAction = ImeAction.Next,
                    onAction: KeyboardActions = KeyboardActions.Default
) {
  OutlinedTextField(
      value = valueState.value,
      onValueChange = {valueState.value = it},
      label = { Text(text = placeholder)},
      maxLines = 1,
      singleLine = true,
      keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
      keyboardActions = onAction,
      colors = TextFieldDefaults.outlinedTextFieldColors(
          focusedBorderColor = Color.Blue,
          cursorColor = Color.Black,
      ),
      shape = RoundedCornerShape(15.dp),
      modifier = Modifier
          .fillMaxWidth()
          .padding(start = 10.dp, end = 10.dp)

      )
}
