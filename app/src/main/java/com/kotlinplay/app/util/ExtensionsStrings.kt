package com.kotlinplay.app.util


fun giveSerchableString(removeSpaces: String):String {
    var message = removeSpaces
    message = message.replace("\\s".toRegex(), "%")
    return message

}

