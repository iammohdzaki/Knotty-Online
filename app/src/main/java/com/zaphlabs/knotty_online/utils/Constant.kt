package com.zaphlabs.knotty_online.utils


const val ONE_MIN_IN_MILLISECONDS = 60000
const val USER_DATA_CODE = 200
const val USER_MODEL_DATA = "user_data"
const val END_USER_DATE_FORMAT = "dd MMMM"
const val IS_FOR_SAVE = "SAVE"
const val IS_FOR_UPDATE = "UPDATE"
const val SAVE = "save"
const val UPDATE = "update"
const val SPLASH_WAIT_TIME: Long = 3000
const val ENCRYPTION_KEY="z1a2p3h4l5a6b7s8"
const val REQUEST_DIRECTORY=100

//FireSTore DB
const val USER_COLLECTION="users"
const val USER_ACCOUNT="accounts"
const val USER_DATA="user_data"

internal interface STATUS_CODES{
    companion object{
        val SUCCESS=0
        val FAILED=1
        val DEFAULT=-1
    }
}

//DB Attributes
const val ACCOUNT_ID="account_id"
const val ACCOUNT_TITLE="accountTitle"
const val ACCOUNT_USER_NAME="accountUserName"
const val ACCOUNT_PASSWORD="accountPassword"
const val ACCOUNT_EMAIL="accountEmail"
const val ACCOUNT_TIME_STAMP="accountTimeStamp"
const val ACCOUNT_NOTE="accountNote"
const val ACCOUNT_STARRED="accountStarred"
const val ACCOUNT_COLOR="imageColor"

//REQUEST CODES
const val OPEN_EDITOR_SCREEN=101

internal interface DateFormat {
    companion object {
        val STANDARD_DATE_FORMAT_NO_SEC = "yyyy-MM-dd HH:mm"
        val STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        val END_USER_DATE_FORMAT = "dd MMM yyyy, hh:mm aa"
        val END_USER_DATE_FORMAT1 = "dd MMM yyyy, hh:mm aa - hh:mm aa"
        val END_USER_DATE_FORMAT2 = "dd MMM yyyy, hh:mm aa"
        val END_USER_DATE_FORMAT3 = "MMM dd, yyyy | hh:mm aa"
        val END_USER_DATE_FORMAT4 = "MMMM dd, yyyy | hh:mm aa"
        val STANDARD_DATE_FORMAT_12 = "yyyy-MM-dd hh:mm aa"
        val STANDARD_DATE_FORMAT_TZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val BACKEND_PICKUP_TIME = "MM/dd/yyyy hh:mm aa"
        val TIME_FORMAT_12_without_ampm = "hh:mm"
        val TIME_FORMAT_12_with_ampm = "hh:mm a"
        val TIME_FORMAT_12 = "hh:mm aa"
        val TIME_FORMAT_12_NO_SPACE = "hh:mmaa"
        val ONLY_DATE = "yyyy-MM-dd"
        val DATE_FORMAT = "dd MMM"
        val ONLY_DATE_mm_dd_yy = "MM/dd/yyyy"
        val TIME_FORMAT_24 = "HH:mm:ss"
        val TIME_FORMAT_24_no_seconds = "HH:mm"
        val CALENDER_FORMAT_DATE = "EEE MMM dd HH:mm:ss z yyyy"
    }
}


