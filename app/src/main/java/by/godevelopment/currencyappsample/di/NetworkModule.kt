package by.godevelopment.currencyappsample.di

import by.godevelopment.currencyappsample.data.BASE_URL
import by.godevelopment.currencyappsample.data.datasources.network.CurrencyApi
import by.godevelopment.currencyappsample.data.datasources.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideBaseUrl() : String = BASE_URL

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