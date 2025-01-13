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
            bundle.putString(Constant.SCREEN_NAME, baseAnalyticsEvent.screenName)
        }

        Log.d("baseAnalyticsEvent.eventName", bundle.toJson() ?: "bundle")
        firebaseAnalytics.logEvent(baseAnalyticsEvent.eventName, bundle)
    }

    sealed class BaseAnalyticsEvent {
        abstract val eventName: String
        abstract val screenName: String

        class AppLaunch(override val screenName: String) : BaseAnalyticsEvent() {
            override val eventName: String
                get() = "app_launch"
        }

        class AppExit(override val screenName: String) : BaseAnalyticsEvent() {
            override val eventName: String
                get() = "app_exit"
        }

        class AppForeground(override val screenName: String) : BaseAnalyticsEvent() {
            override val eventName: String
                get() = "app_foreground"
        }

        class AppBackground(override val screenName: String) : BaseAnalyticsEvent() {
            override val eventName: String
                get() = "app_background"
        }

        class ScreenEvent(
            override val screenName: String,
            private val action: AnalyticsAction
        ) : BaseAnalyticsEvent() {
            override val eventName: String
                get() = action.toString()
        }
    }

    class AnalyticsEvent(
        override val screenName: String,
        private val event: String
    ) : BaseAnalyticsEvent() {
        override val eventName: String
            get() = event
    }

    sealed class AnalyticsAction {
        object Click : AnalyticsAction() {
            override fun toString(): String {
                return "click"
            }
        }

        object View : AnalyticsAction() {
            override fun toString(): String {
                return "view"
            }
        }

        class Custom(private val action: String) : AnalyticsAction() {
            override fun toString(): String {
                return action
            }
        }
    }

    object Constant {
        const val SCREEN_NAME = "screen_name"
        const val EVENT_NAME = "event_name"
    }
}