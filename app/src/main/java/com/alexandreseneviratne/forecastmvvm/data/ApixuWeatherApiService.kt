package com.alexandreseneviratne.forecastmvvm.data

import com.alexandreseneviratne.forecastmvvm.data.network.response.CurrentWeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Alexandre SENEVIRATNE on 6/7/2019.
 */
private const val API_KEY = "fc15e1f97e7e41529c4162035190606"

// http://api.apixu.com/v1/current.json?key=fc15e1f97e7e41529c4162035190606&q=Paris&lang=en

interface ApixuWeatherApiService {
    @GET("current.json")
   suspend fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang") languageCode: String = "en"
    ): CurrentWeatherResponse

    companion object {
        operator fun invoke(): ApixuWeatherApiService {
            val requestInterceptor = Interceptor {chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.apixu.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuWeatherApiService::class.java)
        }
    }
}