package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Anasayfa()
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Anasayfa() {


    val viewModel: WeatherViewModel = viewModel()
    //Tokyo



    viewModel.getCurrentWeather("Ankara")

    val weather = viewModel.weather.observeAsState()






Column(modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally) {

    CitySearchBar(onSearchClick = { cityName ->
        viewModel.getCurrentWeather(cityName)
    })

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(all = 12.dp),
        backgroundColor = colorResource(id = R.color.blue_new)
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(modifier = Modifier.padding(5.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.location_resim),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${weather.value?.location?.name}/${weather.value?.location?.country}",
                        style = MaterialTheme.typography.body2,
                        color = Color.White
                    )
                }



                Text(
                    text = "Today ${weather.value?.current?.observationTime}",
                    color = Color.White
                )

            }


            val painter = rememberImagePainter(data = weather.value?.current?.weatherİcons?.get(0))
            val painterstate = painter.state

            Image(
                painter = painter,
                contentDescription = "none content decs..",
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp, 100.dp)
            )
            if (painterstate is ImagePainter.State.Loading) {
                CircularProgressIndicator()
            }

            Text(
                text = "${weather.value?.current?.temperature}°C",
                fontSize = 50.sp,
                color = Color.White
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(modifier = Modifier.padding(5.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_waves_24),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${weather.value?.current?.pressure} hpa",
                        color = Color.White
                    )
                }
                Row(modifier = Modifier.padding(5.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.water_resim),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Blue
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${weather.value?.current?.humidity}%",
                        color = Color.White
                    )
                }
                Row(modifier = Modifier.padding(5.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.wind_resim),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${weather.value?.current?.windSpeed} km/h",
                        color = Color.White
                    )
                }

            }


        }

    }

    Image(
        painter = painterResource(id = R.drawable.resim3),
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxWidth()
            .padding(6.dp)
            .size(400.dp, 320.dp)
    )
}

    

}

@Composable
fun CitySearchBar(
    onSearchClick: (cityName: String) -> Unit
) {
    var cityName by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Enter city name") },
            modifier = Modifier
                .weight(0.9f)
                .padding(end = 2.dp)
        )

        IconButton(onClick = {
            onSearchClick(cityName)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.search_resim),
                contentDescription = "",
                modifier=Modifier.size(50.dp).padding(top = 5.dp),
                tint = colorResource(id = R.color.purple_700)
            )
        }
    }
}
