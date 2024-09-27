package com.example.cardemulatorstudy

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import java.nio.charset.StandardCharsets

class MyHostApduService : HostApduService() {

    private val SELECT_APDU = byteArrayOf(
        0x00.toByte(),  // CLA
        0xA4.toByte(),  // INS
        0x04.toByte(),  // P1
        0x00.toByte(),  // P2
    )

    companion object {
        private var responseStr = "Hello from HCE!"
        fun updateStr(str: String) {
            responseStr = str
        }
    }

    override fun processCommandApdu(apdu: ByteArray, extras: Bundle?): ByteArray {
        // Check if the APDU command is a SELECT AID command
        if (apdu.size >= SELECT_APDU.size) {
            var isSelectApdu = true
            for (i in SELECT_APDU.indices) {
                if (apdu[i] != SELECT_APDU[i]) {
                    isSelectApdu = false
                    break
                }
            }
            if (isSelectApdu) {
                // Return the response string as bytes
                return responseStr.toByteArray(StandardCharsets.UTF_8)
            }
        }
        return ByteArray(0)
    }

    override fun onDeactivated(reason: Int) {
        // Handle deactivation if necessary
    }
}