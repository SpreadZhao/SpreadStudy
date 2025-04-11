package com.spread.ottotts

import android.media.AudioFormat
import android.speech.tts.SynthesisCallback
import android.speech.tts.SynthesisRequest
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeechService
import android.speech.tts.Voice
import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum
import com.github.houbb.pinyin.util.PinyinHelper
import kotlin.math.min

class OttOTTSService : TextToSpeechService() {

    override fun onIsLanguageAvailable(
        lang: String?,
        country: String?,
        variant: String?
    ): Int {
        if (lang == "zho") {
            return TextToSpeech.LANG_AVAILABLE
        }
        return TextToSpeech.LANG_NOT_SUPPORTED
    }

    override fun onGetLanguage(): Array<out String?>? {
        return arrayOf("zho", "", "")
    }

    override fun onLoadLanguage(
        lang: String?,
        country: String?,
        variant: String?
    ): Int {
        return onIsLanguageAvailable(lang, country, variant)
    }

    override fun onStop() {
    }

    override fun onSynthesizeText(
        request: SynthesisRequest?,
        callback: SynthesisCallback?
    ) {
        if (request == null || callback == null) {
            return
        }
        val text = request.charSequenceText.toString()
        callback.start(16000, AudioFormat.ENCODING_PCM_16BIT, 1)
        val pinyinList =
            PinyinHelper.toPinyin(text, PinyinStyleEnum.NORMAL).split(" ").map { it.trim() }
        for (pinyin in pinyinList) {
            try {
                val afd = assets.openFd("audio/$pinyin.wav")
                val inputStream = afd.createInputStream()
                val totalLength = afd.length
                val bufferSize = 512 // 每次读取 1KB，可以根据需要调大
                val buffer = ByteArray(bufferSize)
                var remaining = totalLength
                while (remaining > 0) {
                    val readSize = min(remaining.toInt(), bufferSize)
                    buffer.fill(0)
                    val bytesRead = inputStream.read(buffer, 0, readSize)
                    if (bytesRead <= 0) break

                    callback.audioAvailable(buffer, 0, bytesRead)
                    remaining -= bytesRead
                }
                inputStream.close()
            } catch (_: Exception) {
                // 找不到音频文件就跳过
                continue
            }
        }
        callback.done()
    }

    override fun onGetVoices(): List<Voice?>? {
        return super.onGetVoices()
    }


}