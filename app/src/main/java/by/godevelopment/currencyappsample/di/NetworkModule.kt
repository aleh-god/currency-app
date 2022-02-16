package by.godevelopment.currencyappsample.di

import by.godevelopment.currencyappsample.data.BASE_URL
import by.godevelopment.currencyappsample.data.datasources.network.CurrencyApi
import by.godevelopment.currencyappsample.data.datasources.network.RemoteDataSource
import by.godevelopment.currencyappsample.data.repositories.CurrencyRepositoryImp
import by.godevelopment.currencyappsample.domain.repositories.CurrencyRep
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

//    @Provides
//    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

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

    @Provides
    @Singleton
    fun provideRepositoryImp(
        remoteDataSource: RemoteDataSource,
        coroutineScope: CoroutineScope
    ): CurrencyRepositoryImp
            = CurrencyRepositoryImp(remoteDataSource, coroutineScope)
}