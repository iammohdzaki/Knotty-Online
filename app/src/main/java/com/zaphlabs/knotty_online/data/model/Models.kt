package com.zaphlabs.knotty_online.data.model

data class User(var name:String ?= null,
                var email:String ?= null,
                var password:String ?= null)