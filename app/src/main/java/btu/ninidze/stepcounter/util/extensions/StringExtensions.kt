package btu.ninidze.stepcounter.util.extensions

fun String.toIntSafely(): Int {
    return if (this == "") {
        0
    } else {
        toInt()
    }
}

fun String.getSneakersIdFromName() = split(" ").last()