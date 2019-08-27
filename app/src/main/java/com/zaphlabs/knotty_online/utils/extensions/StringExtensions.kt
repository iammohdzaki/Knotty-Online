package com.zaphlabs.knotty_online.utils.extensions

/**
 * Created by Mohammad Zaki
 *
 * Converts string to integer safely otherwise returns zero
 */
fun String.toIntOrZero(): Int {
    var value = 0
    justTry {
        value = this.toInt()
    }
    return value
}


fun String.isGenericEmpty(): Boolean {
    return this.trim().isEmpty()
}

/**
 * Converts a string to boolean such as 'Y', 'yes', 'TRUE'
 */

fun String.toBoolean(): Boolean {
    return this != "" &&
            (this.equals("TRUE", ignoreCase = true)
                    || this.equals("Y", ignoreCase = true)
                    || this.equals("YES", ignoreCase = true))
}

/**
 * Converts string to camel case. Handles multiple strings and empty strings
 */
fun String.convertToCamelCase(): String {
    var titleText = ""
    if (!this.isEmpty()) {
        val words = this.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        words.filterNot { it.isEmpty() }
            .map { it.substring(0, 1).toUpperCase() + it.substring(1).toLowerCase() }
            .forEach { titleText += it + " " }
    }
    return titleText.trim { it <= ' ' }
}

inline fun <T> justTry(block: () -> T) = try {
    block()
} catch (e: Throwable) {
}
