package by.godevelopment.currencyappsample.di

import android.util.Log
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.data.datasources.database.CurrencyDao
import by.godevelopment.currencyappsample.data.datasources.database.RateCurrencyDao
import by.godevelopment.currencyappsample.data.datasources.database.SettingsDao
import by.godevelopment.currencyappsample.data.datasources.network.RemoteDataSource
import by.godevelopment.currencyappsample.data.repositories.CurrencyRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    init {
        Log.i(TAG, "AppModule init")
    }

    @Provides
    @Singleton
    fun provideCurrencyRepositoryImp(
        remoteDataSource: RemoteDataSource,
        rateCurrencyDao: RateCurrencyDao,
        currencyDao: CurrencyDao,
        settingsDao: SettingsDao,
        coroutineScope: CoroutineScope
    ): CurrencyRepositoryImp
            = CurrencyRepositoryImp(remoteDataSource, rateCurrencyDao, currencyDao, settingsDao, coroutineScope)

//    @Provides
//    @Singleton
//    fun provideSettingsRepositoryImp(
//        settingsDao: SettingsDao
//    ): SettingsRepositoryImp
//            = SettingsRepositoryImp(settingsDao)
}