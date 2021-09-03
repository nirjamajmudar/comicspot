package com.comicspot.android.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.comicspot.android.Constants
import com.comicspot.android.R
import java.lang.StringBuilder
import java.util.*

object ComicUtils {

    fun generateImagePath(path: String?, extension: String?, type: String): String {
        val builder = StringBuilder()
        path?.let {
            builder.append(path).append(Constants.CHAR_SLASH).append(type)
                .append(Constants.CHAR_PERIOD).append(extension)
        }
        return builder.toString()
    }

    fun buildErrorDialog(context: Context?, message: String?): AlertDialog {
        val dialog = AlertDialog.Builder(context)
        dialog.setCancelable(true)
        dialog.setMessage(message)
        dialog.setPositiveButton(R.string.ok_button) { errorDialog, id ->
            errorDialog.dismiss()
        }
        return dialog.create()
    }
}