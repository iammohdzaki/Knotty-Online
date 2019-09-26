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

interface STATUS_CODES{
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
