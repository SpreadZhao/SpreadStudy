package com.spread.easyphone

import android.app.Activity
import android.app.KeyguardManager
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.os.Build
import android.os.Bundle
import androidx.core.net.toUri

class CallPhoneActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(KeyguardManager::class.java)
            keyguardManager?.requestDismissKeyguard(this, null)
        }
        handleNfc(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNfc(intent)
    }

    private fun handleNfc(intent: Intent?) {
        if (intent == null || intent.action != NfcAdapter.ACTION_NDEF_DISCOVERED) {
            finish()
            return
        }
        val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        val message = rawMessages?.firstOrNull() as? NdefMessage ?: return
        for (record in message.records) {
            if (record.tnf == NdefRecord.TNF_MIME_MEDIA) {
                val mimeType = String(record.type)
                if (mimeType == "application/com.spread.easyphone.phone") {
                    val phoneNumber = String(record.payload, Charsets.UTF_8)
                    callPhone(phoneNumber)
                }
            }
        }
        finish()
    }

    private fun callPhone(phone: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = "tel:$phone".toUri()
        startActivity(callIntent)
    }

}