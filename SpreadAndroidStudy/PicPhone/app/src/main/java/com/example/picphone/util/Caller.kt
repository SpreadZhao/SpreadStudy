package com.example.picphone.util

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat

class Caller(
    private val from: Activity
) {
    fun makeCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(from, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(from, "You don't have call permission!", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(Intent.ACTION_CALL)
        intent.setData(Uri.parse("tel:$phoneNumber"))
        from.startActivity(intent)
    }
}