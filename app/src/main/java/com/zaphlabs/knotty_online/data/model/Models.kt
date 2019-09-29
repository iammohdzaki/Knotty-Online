package com.zaphlabs.knotty_online.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class User(
    var name: String? = null,
    var email: String? = null,
    var accounts: Map<String,Any> ?= null
)

@Parcelize
data class UserAccount(
    var accountId: String,
    var accountTitle: String ?= null,
    var accountUserName: String? = null,
    var accountPassword: String? = null,
    var accountEmail: String? = null,
    var accountTimeStamp: String,
    var accountNote: String? = null,
    var accountStarred: Int
) : Parcelable