package com.nativedevps.support.base_class

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.nativedevps.support.inline.toJson
import com.nativedevps.support.utility.debugging.Log

@SuppressLint("MissingPermission")
open class BaseAnalytics(private val context: Context) {
    private val firebaseAnalytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }

    fun logEvent(
        baseAnalyticsEvent: BaseAnalyticsEvent,
        customPayload: (Map<String, Any>) = emptyMap()
    ) {
        val bundle = Bundle().also { bundle ->
            customPayload.forEach { (key, value) ->
                when (value) {
                    is Int -> bundle.putInt(key, value)
                    is Long -> bundle.putLong(key, value)
                    is Float -> bundle.putFloat(key, value)
                    is Double -> bundle.putDouble(key, value)
                    is Boolean -> bundle.putBoolean(key, value)
                    else -> bundle.putString(key, value.toString())
                }
            }
            bundle.putString(Constant.EVENT_NAME, baseAnalyticsEvent.eventName)
            bundle.putString(Constant.SCREEN_NAME, baseAnalyticsEvent.screen.screenName)
        }

        Log.v("baseAnalyticsEvent.eventName", bundle.toJson() ?: "bundle")
        firebaseAnalytics.logEvent(baseAnalyticsEvent.eventName, bundle)
    }

    sealed class BaseAnalyticsEvent {
        abstract val eventName: String
        abstract val screen: BaseAnalyticsScreen

        class AppLaunch(override val screen: BaseAnalyticsScreen) : BaseAnalyticsEvent() {
            override val eventName: String
                get() = "app_launch"
        }

        class AppExit(override val screen: BaseAnalyticsScreen) : BaseAnalyticsEvent() {
            override val eventName: String
                get() = "app_exit"
        }

        class AppForeground(override val screen: BaseAnalyticsScreen) : BaseAnalyticsEvent() {
            override val eventName: String
                get() = "app_foreground"
        }

        class AppBackground(override val screen: BaseAnalyticsScreen) : BaseAnalyticsEvent() {
            override val eventName: String
                get() = "app_background"
        }

        class ScreenEvent(
            override val screen: BaseAnalyticsScreen,
            private val action: AnalyticsAction
        ) : BaseAnalyticsEvent() {
            override val eventName: String
                get() = action.toString()
        }
    }

    class AnalyticsEvent(
        override val screen: BaseAnalyticsScreen,
        private val event: String
    ) : BaseAnalyticsEvent() {
        override val eventName: String
            get() = event
    }

    sealed class AnalyticsAction {
        class Click(private var properties: String) : AnalyticsAction() {
            override fun toString(): String {
                return "click_$properties"
            }
        }

        class View(private var properties: String) : AnalyticsAction() {
            override fun toString(): String {
                return "view_$properties"
            }
        }

        class Custom(private val action: String) : AnalyticsAction() {
            override fun toString(): String {
                return action
            }
        }
    }

    open class BaseAnalyticsScreen {
        open val screenName: String = ""
    }

    object Constant {
        const val SCREEN_NAME = "screen_name"
        const val EVENT_NAME = "event_name"
    }
}