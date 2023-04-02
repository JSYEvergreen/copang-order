package com.copang.utils

/**
 * get 했을 때 **반드시 값이 존재하는 경우**에만 사용함.
 * 매번 !! 로 강제 not null 캐스팅 하거나 checkNotNull(), requireNotNull() 등을 사용하지 않아도 됨.
 *
 * ex)
 *
 * val safeMap: SafeMap<Long, Some> = someList.toSafeMap { it.id }
 *
 * val some: Some = safeMap`[`id`]` // not null
 */
class SafeMap<K, V>(
    private val innerMap: Map<K, V> = mapOf()
) : Map<K, V> by innerMap {

    override fun get(key: K): V = innerMap[key]!!
}

fun <T, K> Iterable<T>.toSafeMap(keySelector: (T) -> K): SafeMap<K, T> {
    return SafeMap(associateBy(keySelector))
}
