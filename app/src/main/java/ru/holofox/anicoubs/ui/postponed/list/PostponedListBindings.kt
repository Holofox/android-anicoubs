package ru.holofox.anicoubs.ui.postponed.list

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

import ru.holofox.anicoubs.R

import java.util.regex.Pattern

object PostponedListBindings {

    @BindingAdapter("identifyHashtags")
    @JvmStatic
    fun bindHashtags(textView: TextView, text: String) {
        val spannableText = SpannableString(text)
        val matcher = Pattern.compile("#([A-Za-z0-9@_-]+)").matcher(spannableText)

        while (matcher.find()) {
            spannableText.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        textView.context,
                        R.color.colorTags
                    )
                ),
                matcher.start(),
                matcher.end(),
                0
            )
        }

        textView.text = spannableText
    }
}