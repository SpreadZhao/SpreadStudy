package com.spread.easyphone

import android.app.Activity
import android.app.PendingIntent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.widget.Toast
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed interface NfcState {
    object Default : NfcState
    object PendingRead : NfcState object PendingWrite : NfcState
}

class MainViewModel : ViewModel() {

    private var _nfcState: MutableStateFlow<NfcState> = MutableStateFlow(NfcState.Default)
    val nfcState: StateFlow<NfcState>
        get() = _nfcState

    val phoneNumberState = TextFieldState()

    val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(MainApplication.applicationContext)
    }

    private var foregroundDispatchEnabled = false

    val nfcReadStr: MutableStateFlow<String> = MutableStateFlow("")

    fun startWriting(activity: Activity, pendingIntent: PendingIntent) {
        if (nfcState.value != NfcState.Default) {
            error("nfc state error: ${nfcState.value.javaClass.simpleName}")
        }
        val adapter = nfcAdapter
        if (adapter == null) {
            Toast.makeText(activity, "不支持NFC", Toast.LENGTH_SHORT).show()
            return
        }
        _nfcState.value = NfcState.PendingWrite
        enableForegroundDispatch(activity, pendingIntent)
    }

    fun write(activity: Activity, tag: Tag) {
        val phoneNumber = phoneNumberState.text
        if (phoneNumber.isBlank()) {
            Toast.makeText(activity, "电话号为空", Toast.LENGTH_LONG).show()
            return
        }
        val mimeRecord = NdefRecord.createMime(
            "application/com.spread.easyphone.phone",
            phoneNumber.toString().toByteArray()
        )
        val aarRecord = NdefRecord.createApplicationRecord(activity.packageName)
        val ndefMessage = NdefMessage(mimeRecord, aarRecord)
        runCatching {
            Ndef.get(tag).use {
                it.connect()
                it.writeNdefMessage(ndefMessage)
                Toast.makeText(activity, "write to Card success!", Toast.LENGTH_SHORT).show()
            }
        }.onFailure {
            Toast.makeText(activity, "write to Card failed!", Toast.LENGTH_SHORT).show()
        }
    }

    fun startReading(activity: Activity, pendingIntent: PendingIntent) {
        if (nfcState.value != NfcState.Default) {
            error("nfc state error: ${nfcState.value.javaClass.simpleName}")
        }
        val adapter = nfcAdapter
        if (adapter == null) {
            Toast.makeText(activity, "不支持NFC", Toast.LENGTH_SHORT).show()
            return
        }
        _nfcState.value = NfcState.PendingRead
        enableForegroundDispatch(activity, pendingIntent)
    }

    fun read(tag: Tag) {
        nfcReadStr.value = ""
        runCatching {
            Ndef.get(tag).use {
                it.connect()
                val message = it.ndefMessage
                nfcReadStr.value = buildString {
                    message.records?.forEach { record ->
                        if (record == null) {
                            return@forEach
                        }
                        if (record.tnf == NdefRecord.TNF_MIME_MEDIA) {
                            val mimeType = String(record.type)
                            if (mimeType == "application/com.spread.easyphone.phone") {
                                val phoneNumber = String(record.payload, Charsets.UTF_8)
                                append(phoneNumber)
                            }
                        }
                    }
                }
            }
        }
    }

    fun stop(activity: Activity) {
        _nfcState.value = NfcState.Default
        disableForegroundDispatch(activity)
    }

    fun enableForegroundDispatch(activity: Activity, pendingIntent: PendingIntent) {
        if (!foregroundDispatchEnabled) {
            foregroundDispatchEnabled = true
            nfcAdapter?.enableForegroundDispatch(activity, pendingIntent, null, null)
        }
    }

    fun disableForegroundDispatch(activity: Activity) {
        if (foregroundDispatchEnabled) {
            foregroundDispatchEnabled = false
            nfcAdapter?.disableForegroundDispatch(activity)
        }
    }

}