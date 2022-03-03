package by.godevelopment.currencyappsample.data.datasources.network

import by.godevelopment.currencyappsample.data.datamodels.CurrencyApiModel
import by.godevelopment.currencyappsample.data.datamodels.RateCurrencyApiModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("currencies")
    suspend fun getAllCurrencies(): List<CurrencyApiModel>

    @GET("rates?periodicity=0")
    suspend fun getAllRatesByDate(@Query("ondate") date: String): List<RateCurrencyApiModel>

    @GET("rates?periodicity=1")
    suspend fun getAllRatesByMonth(@Query("ondate") date: String): List<RateCurrencyApiModel>

}

// полный перечень валют: https://www.nbrb.by/api/exrates/currencies
// Адрес запроса: https://www.nbrb.by/api/exrates/rates[/{cur_id}]
// получение официального курса белорусского рубля по отношению к иностранным валютам, устанавливаемого ежедневно, на сегодня:
// https://www.nbrb.by/api/exrates/rates?periodicity=0
// получение официального курса белорусского рубля по отношению к иностранным валютам, устанавливаемого ежедневно, на 6 июля 2016 года:
// https://www.nbrb.by/api/exrates/rates?ondate=2016-7-6&periodicity=0
// получение официального курса белорусского рубля по отношению к иностранным валютам, устанавливаемого ежемесячно, на 1 июля 2016 года:
// https://www.nbrb.by/api/exrates/rates?ondate=2016-7-1&periodicity=1
// получение официального курса белорусского рубля по отношению к 1 Доллару США на сегодня:
// https://www.nbrb.by/api/exrates/rates/431 – по внутреннему коду валюты
// https://www.nbrb.by/api/exrates/rates/840?parammode=1 – по цифровому коду валюты (ИСО 4217)
// https://www.nbrb.by/api/exrates/rates/USD?parammode=2 – по буквенному коду валюты (ИСО 4217)
// получение официального курса белорусского рубля по отношению к 100 Российским рублям на 5 июля 2016 года:
// https://www.nbrb.by/api/exrates/rates/298?ondate=2016-7-5