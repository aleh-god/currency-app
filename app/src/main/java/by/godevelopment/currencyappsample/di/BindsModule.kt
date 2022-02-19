package by.godevelopment.currencyappsample.di

import by.godevelopment.currencyappsample.data.repositories.CurrencyRepositoryImp
import by.godevelopment.currencyappsample.domain.repositories.CurrencyRep
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class BindsModule {

    @Binds
    abstract fun bindRepository(currencyRepositoryImp: CurrencyRepositoryImp): CurrencyRep

}