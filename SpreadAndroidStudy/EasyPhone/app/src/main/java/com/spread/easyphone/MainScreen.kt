package com.spread.easyphone

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier, activity: Activity, viewModel: MainViewModel) {
    val pendingIntent: PendingIntent? = remember {
        PendingIntent.getActivity(
            activity, 0, Intent(activity, activity.javaClass).addFlags(
                Intent.FLAG_ACTIVITY_SINGLE_TOP
            ), PendingIntent.FLAG_MUTABLE
        )
    }
    val nfcReadStr by viewModel.nfcReadStr.collectAsState()
    val nfcState by viewModel.nfcState.collectAsState()
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape,
            onClick = {
                pendingIntent?.let {
                    viewModel.startReading(activity, it)
                }
            }
        ) {
            Text(text = "读数据")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                state = viewModel.phoneNumberState,
                label = { Text(text = "电话") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                lineLimits = TextFieldLineLimits.SingleLine
            )
            Button(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 10.dp),
                shape = RectangleShape,
                onClick = {
                    pendingIntent?.let {
                        viewModel.startWriting(activity, it)
                    }
                }
            ) {
                Text(text = "写数据")
            }
        }
        if (nfcReadStr.isNotBlank()) {
            Text(text = "电话：${nfcReadStr}")
        }
    }
    when (nfcState) {
        NfcState.PendingRead, NfcState.PendingWrite -> {
            BasicAlertDialog(
                onDismissRequest = {},
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            ) {
                Card {
                    Text(modifier = Modifier.padding(20.dp), text = "请将NFC卡片靠近手机")
                }
            }
        }

        NfcState.Default -> {}
    }
}
