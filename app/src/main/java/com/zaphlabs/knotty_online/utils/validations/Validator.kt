package com.zaphlabs.knotty.util.validations


const val NAME_REGEX="^[\\\\p{L} .'-]+\$"
const val PASSWORD_REGEX="[a-zA-Z0-9]+"

fun String.checkName() : Boolean{
    return this.matches(NAME_REGEX.toRegex())
}

fun String.checkPassword() : Boolean{
    return this.matches(PASSWORD_REGEX.toRegex())
}



