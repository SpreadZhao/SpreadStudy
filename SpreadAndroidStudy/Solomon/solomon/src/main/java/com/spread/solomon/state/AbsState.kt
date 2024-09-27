package com.spread.solomon.state

abstract class AbsState<V> : State {
    @Volatile
    protected var mValue: V? = null
    @Volatile
    protected var mLevel: StateLevel? = null

    fun updateLevel(value: V) {
        value ?: return
        mValue = value
        updateLevel(valueToLevel(value))
    }

    private fun updateLevel(level: StateLevel?) {
        level ?: return
        if (level.level < 0 || mLevel?.level == level.level) {
            return
        }
        val old = mLevel
        mLevel = level
        onLevelChanged(old, level)
    }

    override fun getLevel(): Int {
        return mLevel?.level ?: -1
    }

    override fun getInfo(): Any? {
        return mValue
    }

    override fun levelName(): String? {
        return mLevel?.name ?: "unInit"
    }

    protected abstract fun onLevelChanged(oldLevel: StateLevel?, newLevel: StateLevel?)

    protected abstract fun valueToLevel(value: V): StateLevel?

}