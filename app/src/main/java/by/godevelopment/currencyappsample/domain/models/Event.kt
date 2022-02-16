package by.godevelopment.currencyappsample.domain.models

class Event<T>(
    value: T?
) {
    private var _value: T? = value
    fun get(): T? = _value.also { _value = null }
}