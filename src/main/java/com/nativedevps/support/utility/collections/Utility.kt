package io.digikraft.utility.collections


fun <T> List<T>.copy(): List<T> {
    return mutableListOf<T>().apply {
        addAll(this)
    }
}

fun <K, V> Map<K, V?>.filterValuesNotNull() =
    mapNotNull { (k, v) -> v?.let { k to v } }.toMap()