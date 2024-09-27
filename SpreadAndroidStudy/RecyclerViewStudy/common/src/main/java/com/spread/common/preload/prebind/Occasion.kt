package com.spread.common.preload.prebind

import android.view.View

sealed interface Occasion {
    data object Scroll : Occasion
    data class Detach(val itemView: View) : Occasion
    data object LayoutComplete : Occasion
}

enum class Reason {
    SCROLL, DETACH, LAYOUT_COMPLETE
}




