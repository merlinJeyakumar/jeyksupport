package com.nativedevps.support.utility.view

import android.app.Activity
import android.content.res.ColorStateList
import android.content.res.Resources.getSystem
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.URLSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import java.io.File
import kotlin.math.absoluteValue


val Int.dp: Int get() = (this / getSystem().displayMetrics.density).toInt()

val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()

fun TextView.removeLinksUnderline() {
    val spannable = SpannableString(text)
    for (u in spannable.getSpans(0, spannable.length, URLSpan::class.java)) {
        spannable.setSpan(object : URLSpan(u.url) {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }, spannable.getSpanStart(u), spannable.getSpanEnd(u), 0)
    }
    text = spannable
}

fun View.setViewAndChildrenEnabled(enabled: Boolean) {
    this.isEnabled = enabled
    if (this is ViewGroup) {
        val viewGroup = this
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            child.setViewAndChildrenEnabled(enabled)
        }
    }
}

fun View.delayOnLifecycle(
    durationInMillis: Long,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: () -> Unit,
): Job? = findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
    lifecycleOwner.lifecycle.coroutineScope.launch(dispatcher) {
        delay(durationInMillis)
        block()
    }
}

fun String.asIpfsImage(): String {
    return replace("ipfs://", "https://ipfs.io/ipfs/")
}

fun String.asIpfs(): String {
    return "ipfs://$this"
}

fun <E> SendChannel<E>.tryOffer(element: E): Boolean = try {
    trySend(element).isSuccess
} catch (t: Throwable) {
    false // Ignore
}

inline fun <reified T> Bundle?.parcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= 33) {
        this?.getParcelable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        this?.getParcelable(key)
    }
}


fun View.visible(visibleIf: Boolean, gone: Boolean = true) {
    visibility = if (visibleIf) {
        View.VISIBLE
    } else {
        if (gone) {
            View.GONE
        } else {
            View.INVISIBLE
        }
    }
}

fun View.gone(goneIf: Boolean) {
    visibility = if (goneIf) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.isGone(): Boolean {
    return this.visibility == View.GONE
}

fun View.isInvisible(): Boolean {
    return this.visibility == View.INVISIBLE
}

fun Activity.hideSystemUI() { //pass getWindow();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController!!.hide(WindowInsets.Type.systemBars())
    } else {
        val decorView = window.decorView
        var uiVisibility = decorView.systemUiVisibility
        uiVisibility = uiVisibility or View.SYSTEM_UI_FLAG_LOW_PROFILE
        uiVisibility = uiVisibility or View.SYSTEM_UI_FLAG_FULLSCREEN
        uiVisibility = uiVisibility or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiVisibility = uiVisibility or View.SYSTEM_UI_FLAG_IMMERSIVE
            uiVisibility = uiVisibility or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
        decorView.systemUiVisibility = uiVisibility
    }
}

fun Activity.showSystemUI() { //pass getWindow();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController!!.show(WindowInsets.Type.systemBars())
    } else {
        val decorView = window.decorView
        var uiVisibility = decorView.systemUiVisibility
        uiVisibility = uiVisibility and View.SYSTEM_UI_FLAG_LOW_PROFILE.inv()
        uiVisibility = uiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
        uiVisibility = uiVisibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiVisibility = uiVisibility and View.SYSTEM_UI_FLAG_IMMERSIVE.inv()
            uiVisibility = uiVisibility and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
        }
        decorView.systemUiVisibility = uiVisibility
    }
}

fun EditText.clear() {
    setText("")
}

fun TabLayout.setTabDisabled(tabPosition: Int) {
    val vg: ViewGroup = this.getChildAt(0) as ViewGroup
    val tabsCount = vg.childCount
    for (j in 0 until tabsCount) {
        //get view of selected tab
        val vgTab = vg.getChildAt(j) as ViewGroup
        if (j == tabPosition) {
            //disable the selected tab
            vgTab.isEnabled = false
        }
    }
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    )
}

fun TabLayout.setupWithViewPager(viewPager: ViewPager2, tabList: List<Pair<String, Drawable>>) {
    if (tabList.size != viewPager.adapter?.itemCount)
        throw Exception("The size of list and the tab count should be equal!")
    TabLayoutMediator(
        this, viewPager
    ) { tab, position ->
        tab.text = tabList[position].first
        tab.icon = tabList[position].second
    }.attach()
}

fun MaterialButton.setBackgroundTint(color: Int) {
    backgroundTintList = ContextCompat.getColorStateList(this.context, color)
}

var isScrolling: Boolean = false
fun ScrollView.onScrollToEnd(threshold: Int = 500, action: () -> Unit) {
    this.viewTreeObserver.addOnScrollChangedListener {
        val view = this.getChildAt(this.childCount - 1)
        val diff = (view.bottom - (this.height + this.scrollY)).absoluteValue
        val isAtBottom = diff <= threshold
        val isNotAtTop = this.scrollY > 0
        if (isAtBottom && isNotAtTop && !isScrolling) {
            isScrolling = true
            CoroutineScope(Dispatchers.Main).launch {
                delay(500) // Wait for 500ms before executing the action
                action()
                isScrolling = false
            }
        }
    }
}

fun View.setMarginTop(margin: Int) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(params.leftMargin, margin, params.rightMargin, params.bottomMargin)
        requestLayout()
    }
}

fun View.setMarginEnd(margin: Int) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(params.leftMargin, params.topMargin, margin, params.bottomMargin)
        requestLayout()
    }
}

fun EditText.moveCursorToEnd() {
    this.setSelection(this.text.length)
}

fun Menu.findMenuItem(@IdRes res: Int): MenuItem? {
    return findItem(res)
}