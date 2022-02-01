package com.example.android.weather

import retrofit2.http.GET

interface WeatherApi {
    @GET("./data/2.5/weather?id=550280&units=metric&appid=a95146e702caea89dfb41b73e7f346dc")
    suspend fun getData(): WeatherJson
}