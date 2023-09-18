package com.interview.login.domain

import com.interview.login.data.remote.login.LoginAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object  LoginService {
    private const val BASE_URL = "http://private-222d3-homework5.apiary-mock.com/"

    fun create(): LoginAPI {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val builder = OkHttpClient().newBuilder()
        builder.connectTimeout(40, TimeUnit.SECONDS)
        builder.readTimeout(40, TimeUnit.SECONDS)
        builder.writeTimeout(40, TimeUnit.SECONDS)

        builder.addInterceptor(Interceptor { chain ->
            var request: Request = chain.request()
            request = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("IMSI", "357175048449937")
                .addHeader("IMEI", "510110406068589")
                .build()
            chain.proceed(request)
        })
        val httpClient: OkHttpClient = builder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        return retrofit.create(LoginAPI::class.java)
    }
}