package com.nativedevps.support.utility.validation

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.anko.toast
import java.util.regex.Pattern

/**
 * Created by pchub on 29-09-2017.
 *
 * A Simple ValidationUtil class that can validate your inputs
 * and can display the error messages on its own so that you
 * don't have to bother adding multiple if-else-if statements
 * and Toasts for your Validations.
 *
 * Of course you can modify this file according to your needs
 * and implement more methods here to improve the validation of this class.
 *
 * <b>*Note*</b>
 * <pre>
 *     Do not save your activity's context in this class as it will lead to memory leak.
 *     Only pass your activity's context to the parameters and avoid keeping reference
 *     of the activity stored inside this class.
 * </pre>
 */
object ValidationUtility {

    fun showToast(context: Context, message: String) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    private fun isNullOrEmpty(input: String?): Boolean = input == null || input.isEmpty()

    fun isValidUsername(username: String?, regex: String = "^[a-zA-Z0-9._-]{3,20}$"): Boolean {
        return !isNullOrEmpty(username) &&
                Pattern.matches(regex, username)
    }

    fun isValidEmail(email: String?): Boolean {
        return !isNullOrEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidMobile(mobile: String?, regex: String = "^[0-9]{10}$"): Boolean {
        return !isNullOrEmpty(mobile) && Pattern.matches(regex, mobile)
    }

    fun isValidPassword(password: String?, context: Context? = null): String? {
        return when {
            isNullOrEmpty(password) -> "Please enter Password first."
            password!!.length < 6 -> "Password length should not be less than 6 characters"
            password.length > 30 -> "Password length should not be greater than 30 characters"
            else -> return null
        }.also {
            context?.toast(it)
        }
    }

    fun String.isValidPassword(): String? {
        return when {
            isNullOrEmpty(this) -> "Please enter Password first."
            this.length < 6 -> "Password length should not be less than 6 characters"
            this.length > 30 -> "Password length should not be greater than 30 characters"
            else -> return null
        }
    }

    /**
     *password must contain 1 number (0-9)
     *password must contain 1 uppercase letters
     *password must contain 1 lowercase letters
     *password must contain 1 non-alpha numeric number
     *password is 8-16 characters with no space
     *
     * https://regex101.com/library/0bH043?orderBy=RELEVANCE&search=password
     **/
    fun isSecurePassword(password: String): Boolean {
        return Pattern.matches(
            "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}\$",
            password
        )
    }

    fun isValidName(txtInput: String?): String? {
        return when {
            isNullOrEmpty(txtInput) -> "Please enter Name first."
            txtInput!!.length < 4 -> "Name length should not be less than 4 characters"
            txtInput.length > 30 -> "Name length should not be greater than 30 characters"
            else -> return null
        }
    }
}