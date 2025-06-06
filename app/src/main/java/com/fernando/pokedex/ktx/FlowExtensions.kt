package com.fernando.pokedex.ktx

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

fun <T> Flow<T>.scope(scope: CoroutineScope): Flow<T> {
    this.launchIn(scope)
    return this
}