package com.example.task4.di

import android.content.Context
import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.Repository
import com.example.task3.objects.Habit
import com.example.task4.objects.HabitUID
import com.example.task4.processersJSON.HabitJsonDeserializer
import com.example.task4.processersJSON.HabitJsonSerializer
import com.example.task4.processersJSON.HabitUIDJsonDeserializer
import com.example.task4.service.HabitsService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideRepository(context : Context) : Repository { // убрать зависимоть модуля от дата-слоя?
        return RepositoryImpl(context) // и тут тоже зависимость убрать
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(HabitsService.logging)
            .addInterceptor(HabitsService.interceptor)
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(Habit::class.java, HabitJsonSerializer()) // куда вынести эти сериалязеры?  
            .registerTypeAdapter(Habit::class.java, HabitJsonDeserializer())
            .registerTypeAdapter(HabitUID::class.java, HabitUIDJsonDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit : Retrofit) : HabitsService {
        return retrofit.create(HabitsService::class.java)
    }


}