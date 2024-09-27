package com.example.nfcstudy

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter

object NFCUtil {
    // this version does not correctly handle nfc tags in Samsung Galaxy S23 Ultra,
    // see new version, which set filters and techList to null and work :(
    fun setupForegroundDispatch(activity: Activity, adapter: NfcAdapter) {
        val intent = Intent(activity.applicationContext, activity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(activity.applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val filters = arrayOf(IntentFilter())
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
        filters[0].addAction(NfcAdapter.ACTION_TAG_DISCOVERED)
        filters[0].addCategory(Intent.CATEGORY_DEFAULT)
        val techList: Array<Array<String>> = arrayOf()
        try {
            filters[0].addDataType("text/plain")
        } catch (_: IntentFilter.MalformedMimeTypeException) {}
        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList)
    }

    fun setupForegroundDispatchNew(activity: Activity, adapter: NfcAdapter) {
        val intent = Intent(activity, activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_MUTABLE)
        adapter.enableForegroundDispatch(activity, pendingIntent, null, null)
    }

    fun stopForegroundDispatch(activity: Activity, adapter: NfcAdapter) {
        adapter.disableForegroundDispatch(activity)
    }
}