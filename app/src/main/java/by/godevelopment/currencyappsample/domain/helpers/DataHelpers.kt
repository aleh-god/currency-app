package by.godevelopment.currencyappsample.domain.helpers

import android.annotation.SuppressLint
import by.godevelopment.currencyappsample.data.DATE_FORMAT
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DataHelpers @Inject constructor() {
    fun getCurrentDayString(): String = convertDateToString(Calendar.getInstance().time)

    fun getYesterdayDateString(): String {
        val calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return convertDateToString(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertDateToString(day: Date): String {
        val dateFormat: DateFormat = SimpleDateFormat(DATE_FORMAT)    // 2016-7-1
        return dateFormat.format(day)
    }

    // Date":"2022-02-17T00:00:00
    fun convertDateStringToString(dayString: String): String = dayString.substringBefore('T')
}