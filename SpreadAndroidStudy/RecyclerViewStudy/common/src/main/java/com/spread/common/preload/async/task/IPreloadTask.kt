package com.spread.common.preload.async.task

interface IPreloadTask<T> {
    fun runWithAsync()
    fun runWithIdle()
    fun runWithSync(): T
}