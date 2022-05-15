package com.example.task4.di

import android.content.Context
import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.Repository
import com.example.task4.service.HabitsService
import com.google.gson.Gson
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
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit : Retrofit) : HabitsService {
        return retrofit.create(HabitsService::class.java) // или класс сюда всунуть?
    }


}