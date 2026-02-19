package com.spread.easyphone

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.spread.easyphone.ui.theme.EasyPhoneTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Spread", "on create")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EasyPhoneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        this,
                        viewModel
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Spread", "on start")
    }

    override fun onResume() {
        Log.d("Spread", "on resume")
        super.onResume()
        addOnNewIntentListener {
            when (viewModel.nfcState.value) {
                NfcState.PendingRead -> {
                    val nfcTag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        it.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
                    } else {
                        it.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
                    }
                    if (nfcTag != null) {
                        viewModel.read(nfcTag)
                    }
                    viewModel.stop(this)
                }

                NfcState.PendingWrite -> {
                    val nfcTag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        it.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
                    } else {
                        it.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
                    }
                    if (nfcTag != null) {
                        viewModel.write(this, nfcTag)
                    }
                    viewModel.stop(this)
                }

                NfcState.Default -> {

                }
            }
        }
    }

    override fun onPause() {
        viewModel.disableForegroundDispatch(this)
        super.onPause()
        Log.d("Spread", "on pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Spread", "on stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Spread", "on destroy")
    }

}