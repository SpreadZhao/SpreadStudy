package com.spread.solomon.state

interface StateListener {
    fun onStateChange(type: Int, oldLevel: Int, newLevel: Int)
}