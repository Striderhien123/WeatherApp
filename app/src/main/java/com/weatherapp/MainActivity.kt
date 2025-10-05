package com.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fetch the weather data
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        val cityName = "New York"
        val apiKey =  BuildConfig.OWM_API_KEY

        RetrofitInstance.api.getCurrentWeather(cityName, apiKey).enqueue(object : Callback<WeatherResponse> {

            // Step 9: Handle the successful server response
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    weatherResponse?.let {
                        updateUI(it)
                    }
                } else {
                    // Handle cases where the server responds with an error (e.g., 404, 500)
                    Toast.makeText(this@MainActivity, "Server Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            // Step 10: Handle the different failure scenarios
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // Handle cases where the request could not be made (e.g., no internet)
                Log.e("MainActivity", "API Call Failed: ${t.message}")
                Toast.makeText(this@MainActivity, "API Call Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(weatherData: WeatherResponse) {
        // Find views
        val cityNameTextView: TextView = findViewById(R.id.cityNameTextView)
        val weatherMainTextView: TextView = findViewById(R.id.weatherMainTextView)
        val weatherDescriptionTextView: TextView = findViewById(R.id.weatherDescriptionTextView)
        val weatherIconImageView: ImageView = findViewById(R.id.weatherIconImageView)

        // Update text views
        cityNameTextView.text = weatherData.name
        val weatherInfo = weatherData.weather.firstOrNull()
        weatherMainTextView.text = weatherInfo?.main ?: "N/A"
        weatherDescriptionTextView.text = weatherInfo?.description?.replaceFirstChar { it.uppercase() } ?: "N/A"

        // Step 6: Fetch and display the icon using Glide
        weatherInfo?.let {
            val iconUrl = "https://openweathermap.org/img/wn/${it.icon}@2x.png"
            Glide.with(this)
                .load(iconUrl)
                .into(weatherIconImageView)
        }
    }
}