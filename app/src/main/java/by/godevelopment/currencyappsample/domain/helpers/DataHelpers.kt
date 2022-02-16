package by.godevelopment.currencyappsample.domain.helpers

import android.annotation.SuppressLint
import android.util.Log
import by.godevelopment.currencyappsample.commons.TAG
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
        val result = dateFormat.format(day)
        Log.i(TAG, "convertDateToString: $result")
        return result
    }
}