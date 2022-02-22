package by.godevelopment.currencyappsample.di

import android.util.Log
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.data.datasources.network.CurrencyApi
import by.godevelopment.currencyappsample.data.datasources.network.RemoteDataSource
import by.godevelopment.currencyappsample.data.repositories.CurrencyRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    init {
        Log.i(TAG, "NetworkModule init")
    }

    @Provides
    fun provideBaseUrl() : String = "https://www.nbrb.by/api/exrates/"// BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        )
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        BASE_URL : String,
        okHttpClient: OkHttpClient
    ) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
//        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideCurrencyApi(retrofit: Retrofit) = retrofit.create(CurrencyApi::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        currencyApi: CurrencyApi
    ): RemoteDataSource = RemoteDataSource(currencyApi)
}