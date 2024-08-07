package com.desafiolatam.weatherlatam.extension

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Long.toShortDateString(): String {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.timeInMillis = this
    return "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
}

fun String.toTimestampString(): String {
    return try {
        val timestamp = this.toLong()
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        format.format(date)
    } catch (e: Exception) {
        this // Return the original string if conversion fails
    }
}

fun Double.toFahrenheit(): Double {
    return this * 9 / 5 + 32
}

fun Double.toCelsius(): Double {
    return (this - 32) * 5 / 9
}

fun DialogFragment.setupWidthToMatchParent() {
    dialog?.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )


}
fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

// Función de extensión para convertir una temperatura en grados Celsius desde un String a Fahrenheit
fun String.toFahrenheit(): String {
    return try {
        val temperatureInCelsius = this.toDouble()
        val temperatureInFahrenheit = temperatureInCelsius * 9 / 5 + 32
        temperatureInFahrenheit.toString()
    } catch (e: NumberFormatException) {
        this // Devuelve el string original si ocurre un error de formato
    }
}

// Función de extensión para convertir una temperatura en grados Fahrenheit desde un String a Celsius
fun String.toCelsius(): String {
    return try {
        val temperatureInFahrenheit = this.toDouble()
        val temperatureInCelsius = (temperatureInFahrenheit - 32) * 5 / 9
        temperatureInCelsius.toString()
    } catch (e: NumberFormatException) {
        this // Devuelve el string original si ocurre un error de formato
    }
}