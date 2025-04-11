package com.spread.ottotts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech

class GetSampleText : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent().apply {
            putExtra(TextToSpeech.Engine.EXTRA_SAMPLE_TEXT, "大家好啊，我是欧头踢踢艾斯，今天来点大家想看的东西")
        }
        setResult(TextToSpeech.LANG_AVAILABLE, intent)
        finish()
    }
}