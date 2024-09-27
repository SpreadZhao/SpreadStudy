package com.example.nfcstudy

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NfcA
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.nio.charset.StandardCharsets


class WriteActivity : AppCompatActivity() {

    private val TAG = "WriteActivity"

    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_writer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        editText = findViewById(R.id.writer_edit)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent: ${intent.action}")
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        Log.d(TAG, "$tag")
        if (tag != null) {
            write(tag, editText.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        NFCUtil.setupForegroundDispatchNew(this, nfcAdapter)
    }

    override fun onPause() {
        super.onPause()
        NFCUtil.stopForegroundDispatch(this, nfcAdapter)
    }

    private fun write(tag: Tag, msg: String) {
        val ndefMessage = createNdefMessage(msg)
        Ndef.get(tag).use {
            it.connect()
            if (it.isWritable) {
                it.writeNdefMessage(ndefMessage)
                Toast.makeText(this, "write successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "tag is not writable!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createNdefMessage(msg: String): NdefMessage {
        val ndefRecord = NdefRecord.createMime("text/plain", msg.toByteArray())
        return NdefMessage(ndefRecord)
    }

}

