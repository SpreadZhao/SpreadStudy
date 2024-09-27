package com.spread.solomon.state

interface State {
    fun getType(): Int
    fun getLevel(): Int
    fun getInfo(): Any?
    fun name(): String?
    fun levelName(): String?
}