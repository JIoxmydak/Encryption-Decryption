package encryptdecrypt
import java.io.File

const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"

fun caesarEncrypt(text: String, shift: Int):String {
    var newMessage = ""
    for (i in text.indices) {
        if (text[i].isLowerCase()) {
            val alphabetIndex = ALPHABET.indexOf(text[i])
            newMessage += ALPHABET[(alphabetIndex + shift) % 26]
        }
        else if (text[i].isUpperCase()) {
            val alphabetIndex = ALPHABET.indexOf(text[i])
            newMessage += ALPHABET[(alphabetIndex + shift) % 26].uppercase()
        } else newMessage += text[i]
    }
    return newMessage
}


fun caesarDecrypt(text: String, shift: Int):String {
    var newMessage = ""
    for (i in text.indices) {
        if (text[i].isLowerCase()) {
            val alphabetIndex = ALPHABET.indexOf(text[i])
            newMessage += ALPHABET[((alphabetIndex - shift) + ALPHABET.length) % 26]
        }
        else if (text[i].isUpperCase()) {
            val alphabetIndex = ALPHABET.indexOf(text[i])
            newMessage += ALPHABET[(alphabetIndex - shift + ALPHABET.length) % 26].uppercase()
        } else newMessage += text[i]
    }
    return newMessage
}

fun unicodeEncrypt(text: String, shift: Int):String {
    val newMessage = mutableListOf<Char>()
    for (i in text.indices) newMessage.add((text[i].code + shift).toChar())
    return newMessage.joinToString("")
}

fun unicodeDecrypt(text: String, shift: Int):String {
    val newMessage = mutableListOf<Char>()
    for (i in text.indices) newMessage.add((text[i].code - shift).toChar())
    return newMessage.joinToString("")
}

fun main(args: Array<String>) {
    val path = System.getProperty ("user.dir")
    val separator = File.separator
    var inFileName: String
    var outFileName = ""
    var algorithm = "shift"
    var targetOperation = "enc"
    var message = ""
    var key = 0

    for (i in args.indices) {
        if (args[i] == "-alg") algorithm = args[i + 1]
        if (args[i] == "-data") message = args[i + 1]
        if (args[i] == "-in" && !args.contains("-data")) {
            inFileName = args[i + 1]
            message = File("$path$separator$inFileName").readText()
        }
        if (args[i] == "-out") outFileName = args[i + 1]
        if (args[i] == "-mode") targetOperation = args[i + 1]
        if (args[i] == "-key") key = args[i + 1].toInt()
    }

    when (algorithm) {
        "unicode" -> {
            if (args.contains("-out")) {
                when(targetOperation) {
                    "dec" -> File("$path$separator$outFileName").writeText(unicodeDecrypt(message, key))
                    "enc" -> File("$path$separator$outFileName").writeText(unicodeEncrypt(message, key))
                }
            }

            else if (!args.contains("-out")) {
                when(targetOperation) {
                    "dec" -> println(unicodeDecrypt(message, key))
                    "enc" -> println(unicodeEncrypt(message, key))
                }
            }

            else println("Error")
        }
        "shift" -> {
            if (args.contains("-out")) {
                when(targetOperation) {
                    "dec" -> File("$path$separator$outFileName").writeText(caesarDecrypt(message, key))
                    "enc" -> File("$path$separator$outFileName").writeText(caesarEncrypt(message, key))
                }
            }

            else if (!args.contains("-out")) {
                when(targetOperation) {
                    "dec" -> println(caesarDecrypt(message, key))
                    "enc" -> println(caesarEncrypt(message, key))
                }
            }

            else println("Error")
        }
    }
}