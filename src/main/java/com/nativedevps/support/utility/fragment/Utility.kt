package com.nativedevps.support.utility.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


fun FragmentActivity.findHostFragment(hostRes:Int): Fragment? {
    return this.supportFragmentManager.findFragmentById(hostRes);
}