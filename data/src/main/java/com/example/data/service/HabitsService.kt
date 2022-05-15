package com.example.task4.service

import com.example.task3.objects.Habit
import com.example.task4.objects.HabitUID
import com.example.task4.processersJSON.HabitJsonDeserializer
import com.example.task4.processersJSON.HabitJsonSerializer
import com.example.task4.processersJSON.HabitUIDJsonDeserializer
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface HabitsService {

    companion object {
        const val TOKEN : String = "23f80e7b-7e5d-4e9d-b072-03d900e4a9b3"

        private val interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val builder = originalRequest.newBuilder().header(
                    "Authorization", TOKEN
                )

                val newRequest = builder.build()
                return chain.proceed(newRequest)
            }

        }

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private var service: HabitsService? = null

        fun getInstance(): HabitsService {
            if (service != null) {
                return service as HabitsService
            }

            else {
                val okHttpClient = OkHttpClient().newBuilder()
                    .addInterceptor(logging)
                    .addInterceptor(interceptor)
                    .build()

                val gson = GsonBuilder()
                    .registerTypeAdapter(Habit::class.java, HabitJsonSerializer())
                    .registerTypeAdapter(Habit::class.java, HabitJsonDeserializer())
                    .registerTypeAdapter(HabitUID::class.java, HabitUIDJsonDeserializer())
                    .create()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://droid-test-server.doubletapp.ru/api/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

                service = retrofit.create(HabitsService::class.java)
                return service as HabitsService
            }
        }
    }

    @GET("habit")
    suspend fun getHabits(): List<Habit>

    @PUT("habit")
    suspend fun putHabit(@Body habit: Habit): HabitUID

//    @POST("habit_done") - типа отметил, что в жизни совершил действие привычки. Для этого надо по-хорошему отельный класс завести вроде
//    suspend fun makeHabitDone()

}