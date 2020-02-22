package com.kotlinplay.app.util


fun removeSpaces(removeSpaces: String):String {
    var message = removeSpaces
    message = message.replace("\\s".toRegex(), "")
    return message

}

