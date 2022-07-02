package com.nativedevps.support.utility.fragment.fragmentstatemanager

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.jetbrains.anko.collections.forEachReversedWithIndex
import java.util.*


class StateManager private constructor(builder: StateManagerBuilder) {
    private val bottomNavigation: Map<Int, Stack<Pair<String, Fragment>>>
    private val supportFragmentManager: FragmentManager
    private val fragmentContainer: ViewGroup

    /*
     * OnBackPressed from a fragment user calls this function
     * Fragment will be removed
     * Last Fragment in stack will be showed
     */
    fun fragmentOnBackPressed(id: Int) {
        bottomNavigation[id]?.pop()
        bottomNavigation[id]?.peek()?.let { manageFragments(it.second, id, true, it.first) }
    }

    /*
     * In your activity which contains your bottomNavigationView
     * User calls this function to show initial fragments
     */
    fun showOnNavigationClick(
        id: Int, fragment: Fragment, tag: String = id.toString()
    ) {
        if (bottomNavigation[id]?.isEmpty() == true) {
            manageFragments(fragment, id, false, tag)
        } else {
            bottomNavigation[id]?.peek()?.let {
                manageFragments(
                    it.second, id, false, it.first
                )
            }
        }
    }

    /*
     * User calls this function to show any fragment
     */
    fun showFragment(id: Int, fragment: Fragment, tag: String) {
        manageFragments(fragment, id, false, tag)
    }

    /*
     * If user wants complete a stream of pages such as select option-> select option -> payment -> show initial fragment
     * Then user needs to use this function
     */
    fun removeAllFragmentStream(
        id: Int, nextToShow: Fragment?, tag: String
    ) {
        while (bottomNavigation[id]?.isNotEmpty() == true) {
            val fg: Pair<String, Fragment> = bottomNavigation[id]?.pop() ?: return
            val removable = supportFragmentManager.findFragmentByTag(getTag(id, fg.first))
            if (removable != null) {
                val transaction = supportFragmentManager.beginTransaction()
                removable.userVisibleHint = false
                transaction.remove(removable).commit()
            }
        }
        val transaction = supportFragmentManager.beginTransaction().setTransition(
            FragmentTransaction.TRANSIT_FRAGMENT_FADE
        )
        val current = visibleFragment
        if (current != null) {
            transaction.remove(current)
        }
        if (nextToShow != null) {
            bottomNavigation[id]?.push(Pair(tag, nextToShow))
            nextToShow.userVisibleHint = true
            transaction.add(fragmentContainer.id, nextToShow, getTag(id, tag)).commit()
        }
    }

    /*
     * If user wants to remove everything according to this manager than use this function
     * For example when you want to logout from app then use this function to clear everything
     */
    fun removeAll() {
        for (key in bottomNavigation.keys) {
            bottomNavigation[key]?.clear()
        }
        stateManager = null
    }

    /*
     * This function manages transactions to show, hide and remove the fragments
     */
    private fun manageFragments(
        fragment: Fragment, id: Int, isComeByBackButton: Boolean, tag: String
    ) {
        val current = visibleFragment
        val foundFragment = supportFragmentManager.findFragmentByTag(getTag(id, tag))
        val transaction = supportFragmentManager.beginTransaction().setTransition(
            FragmentTransaction.TRANSIT_FRAGMENT_FADE
        )
        if (foundFragment == null) {
            if (current != null) {
                transaction.hide(current)
            }
            bottomNavigation[id]?.push(Pair(tag, fragment))
            fragment.let {
                fragment.setMenuVisibility(true)
                fragment.userVisibleHint = true
                current?.userVisibleHint = false
                transaction.add(fragmentContainer.id, it, getTag(id, tag)).commit()
            }
        } else {
            if (current != null) {
                if (isComeByBackButton) {
                    current.userVisibleHint = false
                    current.setMenuVisibility(false)
                    transaction.remove(current)
                } else {
                    current.userVisibleHint = false
                    current.setMenuVisibility(false)
                    transaction.hide(current)
                }
            }
            foundFragment.userVisibleHint = true
            foundFragment.setMenuVisibility(true)
            transaction.show(foundFragment).commit()
        }
    }

    /*
     * This function gives the current fragment on screen
     */
    val visibleFragment: Fragment?
        get() {
            val fragments = supportFragmentManager.fragments
            if (fragments.isNotEmpty()) {
                for (fragment in fragments) {
                    if (fragment.isVisible) {
                        return fragment
                    }
                }
            }
            return null
        }

    fun hasChildren(id: Int): Boolean {
        return (bottomNavigation[id]?.size ?: 3) > 1
    }

    fun hasBackStack(id: Int): Boolean {
        if (bottomNavigation.containsKey(id) && hasChildren(id)) {
            return true
        }
        return false
    }

    fun showFirst(id: Int) {
        val list = bottomNavigation[id] ?: listOf()
        if (bottomNavigation[id] == null) {
            return
        }
        list.forEachReversedWithIndex { i, fragment ->
            if (i != 0) {
                val fg: Pair<String, Fragment> = bottomNavigation[id]?.pop() ?: return
                val removable = supportFragmentManager.findFragmentByTag(getTag(id, fg.first))
                if (removable != null) {
                    removable.userVisibleHint = false
                    supportFragmentManager.beginTransaction().remove(removable).commit()
                }
            }
        }
        /*val transaction = supportFragmentManager.beginTransaction().setTransition(
            FragmentTransaction.TRANSIT_FRAGMENT_FADE
        )
        val current = visibleFragment
        if (current != null) {
            transaction.remove(current)
        }*/
        bottomNavigation[id]?.firstElement()?.let {
            val foundFragment = supportFragmentManager.findFragmentByTag(getTag(id, it.first))
            val transaction = supportFragmentManager.beginTransaction().setTransition(
                FragmentTransaction.TRANSIT_FRAGMENT_FADE
            )
            foundFragment?.let {
                it.userVisibleHint = true
                transaction.show(it).commit()
            }
        }
    }

    fun showBackStackByTag(pacId: Int, tag: String) {
        if (!bottomNavigation.containsKey(pacId)) {
            return
        }
        val list = bottomNavigation[pacId] ?: listOf()
        if (bottomNavigation[pacId] == null) {
            return
        }
        for (pair in list.asReversed()) {
            val currentItem: Pair<String, Fragment> = bottomNavigation[pacId]?.pop() ?: return
            Log.e("JeyK", "currentItem: ${currentItem.first} traversal:${pair.first}")
            val currentTag = getTag(pacId, currentItem.first)

            Log.e("JeyK", "currentTag $currentTag")
            Log.e("JeyK", "tag $tag")
            Log.e("JeyK", "CurrentTag equal ${currentTag == tag}")

            if (currentTag == tag) {
                break

            } else {
                val removable = supportFragmentManager.findFragmentByTag(getTag(pacId, currentItem.first))
                if (removable != null) {
                    removable.userVisibleHint = false
                    supportFragmentManager.beginTransaction().remove(removable).commit()
                }
            }
        }

        Log.e("JeyK", "id $pacId")
        bottomNavigation[pacId]?.firstOrNull()?.let {
            val foundFragment = supportFragmentManager.findFragmentByTag(getTag(pacId, it.first))
            val transaction = supportFragmentManager.beginTransaction().setTransition(
                FragmentTransaction.TRANSIT_FRAGMENT_FADE
            )
            foundFragment?.let {
                it.userVisibleHint = true
                transaction.show(it).commit()
            }
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var stateManager: StateManager? = null

        /*
     * This function gets StateManagerBuilder object as a parameter
     * Builds the instance to use with getInstance
     */
        fun buildInstance(builder: StateManagerBuilder): StateManager {
            return if (stateManager == null) {
                StateManager(builder).apply {
                    stateManager = this
                }
            } else {
                stateManager!!
            }
        }

        fun getInstance(): StateManager {
            return stateManager!!
        }
    }

    /*
     * Constructor
     */
    init {
        bottomNavigation = builder.navigationStacks
        supportFragmentManager = builder.supportFragmentManager
        fragmentContainer = builder.fragmentContainer
    }
}

fun getTag(mapId: Int, tag: String): String {
    return "$mapId-$tag"
}