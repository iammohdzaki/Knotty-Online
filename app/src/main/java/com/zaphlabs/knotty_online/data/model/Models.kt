package com.zaphlabs.knotty_online.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class User(
    var name: String? = null,
    var email: String? = null
)

@Parcelize
data class Message(
    var userId:String ?= null,
    var userName:String ?= null,
    var message: String ?= null,
    var photoUrl:String ?= null,
    var messageType:Int?= null
):Parcelable

data class Chat(
    var chatId:String ?= null,
    var chatImage:String ?= null,
    var chatReceiverName:String ?= null,
    var lastMessage: String ?= null,
    var timestamp:String ?= null
)