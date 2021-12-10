package com.maasglobal.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maasglobal.data.rx.RxSingleSchedulers
import com.maasglobal.data.networking.DirectionService
import com.maasglobal.data.networking.WikipediaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(HttpLoggingInterceptor())
        return okHttpBuilder.build()

    }

    // Base URLs
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    // Gson
    @Provides
    //@Singleton
    fun provideGson(): Gson = GsonBuilder().create()


    @Provides
    @Singleton
    fun provideWikipediaServiceApi(retrofit: Retrofit.Builder): WikipediaService =
        retrofit.baseUrl(WikipediaService.BASE_URL).build().create(WikipediaService::class.java)


    @Provides
    @Singleton
    fun provideDirectionServiceApi(retrofit: Retrofit.Builder): DirectionService =
        retrofit.baseUrl(DirectionService.BASE_URL).build().create(DirectionService::class.java)


    @Provides
    @Singleton
    fun providesScheduler(): RxSingleSchedulers {
        return RxSingleSchedulers.DEFAULT
    }





}


