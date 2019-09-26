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
    var accountId: Int,
    var accountTitle: String,
    var accountUserName: String? = null,
    var accountPassword: String,
    var accountEmail: String? = null,
    var accountTimeStamp: String,
    var accountNote: String? = null,
    var accountStarred: Int,
    var imageColor: Int = -1
) : Parcelable