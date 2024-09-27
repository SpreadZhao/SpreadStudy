package com.spread.solomon.state

object SystemStateType {
    const val CPU = 1
    const val NETWORK = 2
    const val BATTERY = 3
}

object CpuLevel {
    val low = StateLevel(0, "Low")
    val moderateLow = StateLevel(1, "Moderate Low")
    val moderate = StateLevel(2, "Moderate")
    val high = StateLevel(3, "High")
}

object NetworkLevel {
    val excellent = StateLevel(0, "Excellent")
    val good = StateLevel(1, "Good")
    val fair = StateLevel(2, "Fair")
    val poor = StateLevel(3, "Poor")
    val offline = StateLevel(4, "Offline")
}

object BatteryLevel {
    val high = StateLevel(0, "High")
    val medium = StateLevel(1, "Medium")
    val mediumLow = StateLevel(2, "Medium Low")
    val low = StateLevel(3, "Low")
    val veryLow = StateLevel(4, "Very Low")
}