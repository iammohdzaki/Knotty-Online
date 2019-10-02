package com.zaphlabs.knotty_online.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*



object DateTimeUtil {

    private const val BACKEND_DATE_FORMAT = "yyyy-MM-dd"
    private const val USER_TIMESHEET_DATE_FORMAT = "dd - MM - yyyy"


    fun getCurrentDate(): Date {
        val calendar = Calendar.getInstance()
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

    fun getRelativeTimeWithCurrentTime(time: Date): String {
        val currentDate = Calendar.getInstance().time
        val relativeDifference = (currentDate.time - time.time) / 1000

        return if (relativeDifference > 0) {
            when {
                relativeDifference < 60 -> "$relativeDifference seconds ago"
                relativeDifference < 60 * 60 -> (relativeDifference / 60).toString() + " minutes ago"
                relativeDifference < 60 * 60 * 24 -> (relativeDifference / (60 * 60)).toString() + " hours ago"
                else -> (relativeDifference / (60 * 60 * 24)).toString() + " days ago"
            }

        } else {
            "moments ago"
        }

    }

    fun getDate(formattedDate: String): Date {

        var date: Date?

        try {

            date = SimpleDateFormat(DateFormat.STANDARD_DATE_FORMAT, Locale.ENGLISH).parse(formattedDate)
        } catch (e: ParseException) {

            try {

                date = SimpleDateFormat(DateFormat.STANDARD_DATE_FORMAT_TZ, Locale.ENGLISH).parse(
                    formattedDate
                )
            } catch (exTz: Exception) {

                try {
                    date = SimpleDateFormat(DateFormat.ONLY_DATE, Locale.ENGLISH).parse(formattedDate)
                } catch (exDate: Exception) {

                    try {
                        date = SimpleDateFormat(DateFormat.TIME_FORMAT_24, Locale.ENGLISH).parse(
                            formattedDate
                        )
                    } catch (exTime: Exception) {

                        try {
                            date = SimpleDateFormat(DateFormat.BACKEND_PICKUP_TIME, Locale.ENGLISH).parse(
                                formattedDate
                            )
                        } catch (exD: Exception) {
                            try {
                                date =
                                    SimpleDateFormat(DateFormat.END_USER_DATE_FORMAT, Locale.ENGLISH).parse(
                                        formattedDate
                                    )
                            } catch (exEndUserFormat: Exception) {
                                try {
                                    date =
                                        SimpleDateFormat(DateFormat.TIME_FORMAT_12_with_ampm, Locale.ENGLISH).parse(
                                            formattedDate
                                        )
                                } catch (exTimeWithAmPm: Exception) {

                                    exEndUserFormat.printStackTrace()
                                    exD.printStackTrace()
                                    exTime.printStackTrace()
                                    exDate.printStackTrace()
                                    e.printStackTrace()
                                    exTz.printStackTrace()
                                    date = Date()
                                }

                            }

                        }

                    }


                }

            }

        }

        return date!!
    }


}