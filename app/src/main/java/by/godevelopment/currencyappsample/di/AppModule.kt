package by.godevelopment.currencyappsample.di

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
    @Provides
    @Singleton
    fun provideCurrencyRepositoryImp(
        remoteDataSource: RemoteDataSource,
        rateCurrencyDao: RateCurrencyDao,
        settingsDao: SettingsDao,
        coroutineScope: CoroutineScope
    ): CurrencyRepositoryImp
            = CurrencyRepositoryImp(remoteDataSource, rateCurrencyDao, settingsDao, coroutineScope)
}