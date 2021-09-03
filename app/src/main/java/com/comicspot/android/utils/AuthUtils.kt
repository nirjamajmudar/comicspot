package com.comicspot.android.utils

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.comicspot.android.R
import com.comicspot.android.database.AuthorizationInfo
import com.comicspot.android.view.activities.ComicActivity
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

object AuthUtils {

    fun buildDialogForAuthorization(context: Context): Dialog {
        val dialog = Dialog(context)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_keys_input)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        );
        val publicKey = dialog.findViewById(R.id.public_key_value) as AppCompatEditText
        val privateKey = dialog.findViewById(R.id.private_key_value) as AppCompatEditText
        val okayButton = dialog.findViewById(R.id.ok_button) as AppCompatButton
        okayButton.setOnClickListener {
            if (publicKey.text?.isNotEmpty() == true && privateKey.text?.isNotEmpty() == true) {
                generateComicServiceParams(
                    context,
                    Date().time,
                    publicKey.text.toString(),
                    privateKey.text.toString()
                )
                dialog.dismiss()
            } else {
                Toast.makeText(context, context.getString(R.string.enter_both_keys_text), Toast.LENGTH_LONG).show()
            }
        }
        return dialog
    }

    @VisibleForTesting
    fun generateComicServiceParams(
        context: Context,
        timestamp: Long,
        publicKey: String,
        privateKey: String
    ): String {
        val builder = StringBuilder()
        builder.append(timestamp)
            .append(privateKey)
            .append(publicKey)
        val hashValue = md5(builder.toString())
        if (context is ComicActivity) {
            context.onAuthSuccess(AuthorizationInfo(1, timestamp, publicKey, hashValue))
        }
        return hashValue
    }

    @VisibleForTesting
    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}