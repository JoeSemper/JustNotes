package com.joesemper.justnotes.data.model

import android.content.Context
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.joesemper.justnotes.R
import com.joesemper.justnotes.data.noteId
import com.joesemper.justnotes.ui.MainActivity
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

@Parcelize
data class Note(
    val id: Long = noteId,
    val title: String = "",
    val note: String = "",
    val color: Color = Color.WHITE,
) : Parcelable

enum class Color(val index: Int, val colorId: Int, val designation: String) {
    WHITE(0, R.color.color_white, "White"),
    YELLOW(1, R.color.color_yellow, "Yellow"),
    GREEN(2, R.color.color_green, "Green"),
    BLUE(3, R.color.color_blue, "Blue"),
    RED(4, R.color.color_red, "Red"),
    VIOLET(5, R.color.color_violet, "Violet"),
    PINK(6, R.color.color_pink, "Pink");

    fun mapToColor(context: Context): Int {
        return ContextCompat.getColor(context, colorId)
    }
}

fun getArrayOfColors(): Array<String> {
    return Array(Color.values().size) { i: Int ->
        Color.values()[i].designation
    }
}

fun getColorByNumber(number: Int): Color {
    for (color in Color.values())
        if (color.index == number) {
            return color
        }
    return Color.WHITE
}

fun getRandomColor(): Color {
    return getColorByNumber((0..Color.values().size).random())
}


