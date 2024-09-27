package com.example.nfcstudy

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NFCActivity : AppCompatActivity() {

    private lateinit var nfcAdapter: NfcAdapter

    private val TAG = "NFCStudy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nfcactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        val msg = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        val builder = StringBuilder().apply {
            appendLine("action: ${intent.action}")
            appendLine("tag: $tag")
            appendLine("msg: ${msg?.let { extractMsg(it) }}")
        }
        Log.d(TAG, "action: ${intent.action}")
        Log.d(TAG, "tag: $tag")
        IsoDep.get(tag)?.use {
            it.connect()
            val apduCommand = byteArrayOf(
                0x00.toByte(), 0xA4.toByte(), 0x04.toByte(), 0x00.toByte(), 0x05.toByte(),
                0xF2.toByte(), 0x22.toByte(), 0x22.toByte(), 0x22.toByte(), 0x22.toByte(), 0x00.toByte()
            )
            val response = it.transceive(apduCommand)
            builder.appendLine("response: ${String(response)}")
        }
        findViewById<TextView>(R.id.nfc_msg).text = builder.toString()
    }

    override fun onResume() {
        super.onResume()
        NFCUtil.setupForegroundDispatchNew(this, nfcAdapter)
    }

    override fun onPause() {
        super.onPause()
        NFCUtil.stopForegroundDispatch(this, nfcAdapter)
    }

    private fun createMsg(): NdefMessage {
        return NdefMessage(NdefRecord.createMime("text/plain", "Hello, nfc!".toByteArray()))
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val sb = java.lang.StringBuilder()
        for (b in bytes) {
            sb.append(String.format("%02X", b))
        }
        return sb.toString()
    }

    private fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((s[i].digitToIntOrNull(16) ?: (-1 shl 4)) + s[i + 1].digitToIntOrNull(
                16
            )!!).toByte()
            i += 2
        }
        return data
    }

    private fun extractMsg(raw: Array<Parcelable?>): String {
        val res = mutableListOf<String>()
        (raw[0] as? NdefMessage)?.let {
            for (record in it.records) {
                res.add("[type: ${String(record.type)}, payload: ${String(record.payload)}]")
            }
        }
        return res.joinToString(separator = "\n") { it }
    }
}

