package com.zaphlabs.knotty_online.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {

    private const val BACKEND_DATE_FORMAT = "yyyy-MM-dd"
    private const val USER_TIMESHEET_DATE_FORMAT = "dd - MM - yyyy"


    fun getCurrentDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        return calendar.time
    }


    fun getFutureDate(daysToAdd: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        return calendar.time
    }



    fun getEndUserDate(calendar: Calendar): String {
        return getFormattedDateFromCalendar(calendar, USER_TIMESHEET_DATE_FORMAT)
    }


    private fun getFormattedDateFromCalendar(calendar: Calendar, requiredFormat: String): String {
        return SimpleDateFormat(requiredFormat, Locale.US).format(calendar.time)
    }


    @Throws(ParseException::class)
    fun generateUtcTime(date: String): String {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone
        val localFormat = SimpleDateFormat(USER_TIMESHEET_DATE_FORMAT, Locale.US)

        val utcFormat = SimpleDateFormat(BACKEND_DATE_FORMAT)
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")
        localFormat.timeZone = tz
        val dateObject = localFormat.parse(date)
        return utcFormat.format(dateObject)
    }

    /**
     * Method to get Todays date in a specified format
     *
     * @return
     */
    fun getFormattedDate(date: Date, format: String): String {
        return SimpleDateFormat(format, Locale.ENGLISH).format(date)
    }

}