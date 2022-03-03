package by.godevelopment.currencyappsample.di

import android.content.Context
import androidx.room.Room
import by.godevelopment.currencyappsample.data.datasources.database.CurrencyDatabase
import by.godevelopment.currencyappsample.data.datasources.database.RateCurrencyDao
import by.godevelopment.currencyappsample.data.datasources.database.SettingsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {
    @Provides
    fun provideRateDatabaseDao(database: CurrencyDatabase): RateCurrencyDao {
        return database.getRateCurrencyDao()
    }

    @Provides
    fun provideSettingsDatabaseDao(database: CurrencyDatabase): SettingsDao {
        return database.getSettingsDao()
    }

    @Provides
    @Singleton
    fun provideCurrencyDatabase(
        @ApplicationContext appContext: Context,
        providerRate: Provider<RateCurrencyDao>,
        providerSettings: Provider<SettingsDao>
    ): CurrencyDatabase {
        return Room.databaseBuilder(
            appContext,
            CurrencyDatabase::class.java,
            "currency_database.db"
        )
            .build()
    }
}