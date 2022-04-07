package com.nativedevps.support.utility.device.security;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Security {
    public static boolean isLockScreenDisabled(Context context) {
        // Starting with android 6.0 calling isLockScreenDisabled fails altogether because the
        // signature has changed. There is a new method isDeviceSecure which, however, does
        // not allow the differentiation between lock screen 'None' and 'Swipe.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            KeyguardManager keyguardMgr = (KeyguardManager) context
                    .getSystemService(Context.KEYGUARD_SERVICE);

            // But luckily there is no 'Automatically lock x minutes after sleep' option when
            // 'Swipe' is set which means that as soon as the screen is off, switching back on
            // requires a swipe which results in a USER_PRESENT broadcast.
            return !keyguardMgr.isDeviceSecure();
        }

        String LOCKSCREEN_UTILS = "com.android.internal.widget.LockPatternUtils";

        try {
            Class<?> lockUtilsClass = Class.forName(LOCKSCREEN_UTILS);

            Object lockUtils = lockUtilsClass.getConstructor(Context.class).newInstance(context);

            Method method = lockUtilsClass.getMethod("isLockScreenDisabled");

            // Starting with android 5.x this fails with InvocationTargetException
            // (caused by SecurityException - MANAGE_USERS permission is required because
            //  internally some additional logic was added to return false if one can switch between several users)
            // if (Screen Lock is None) {
            //   ... exception caused by getting all users (if user count)
            // } else {
            //   return false;
            // }
            // -> therefore if no exception is thrown, we know the screen lock setting is
            //    set to Swipe, Pattern, PIN/PW or something else other than 'None'

            boolean isDisabled;
            try {

                isDisabled = Boolean.valueOf(String.valueOf(method.invoke(lockUtils)));
            } catch (InvocationTargetException ex) {
                Log.w("JeyK", "Expected exception with screen lock type equals 'None': " + ex);
                isDisabled = true;
            }
            return isDisabled;
        } catch (Exception e) {
            Log.e("JeyK", "Error detecting whether screen lock is disabled: " + e);

            e.printStackTrace();
        }

        return false;
    }
}
