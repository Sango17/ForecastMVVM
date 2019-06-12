package com.alexandreseneviratne.forecastmvvm.internal

import kotlinx.coroutines.*

/**
 * Created by Alexandre SENEVIRATNE on 6/12/2019.
 */

fun <T> lazyDeferred(bloc: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            bloc.invoke(this)
        }
    }
}