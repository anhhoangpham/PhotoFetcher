package com.anhpham.photofetcher.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.anhpham.photofetcher.presentation.MainActivity
import com.anhpham.photofetcher.presentation.util.LogCollector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.anhpham.photofetcher.R

@Composable
fun TopAppBarWithMenu() {
    val expanded = remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h6,
            )
        },
        elevation = 8.dp,
        actions = {
            Box(Modifier.wrapContentSize(Alignment.TopEnd)) {
                IconButton(onClick = {
                    expanded.value = true
                }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "More Menu",
                    )
                }
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }) {

                DropdownMenuItem(onClick = {
                    expanded.value = false
                    sendLogFiles(coroutine, context)
                }) {
                    Text("Report issues")
                }

                DropdownMenuItem(onClick = {
                    expanded.value = false
                    makeAppCrash()
                }) {
                    Text("Make app crash")
                }
            }
        }
    )
}

private fun sendLogFiles(
    coroutine: CoroutineScope,
    context: Context
) {
    coroutine.launch(Dispatchers.IO) {
        LogCollector.cleanUpOldFiles()
        val zippedLog = LogCollector.collectLogs()
        if (zippedLog.exists()) {
            val uri = FileProvider.getUriForFile(
                context,
                MainActivity::class.java.name + ".provider",
                zippedLog
            )
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "application/zip"
            }

            val chooser = Intent.createChooser(sendIntent, "Report issues")
            val resInfoList: List<ResolveInfo> = context.packageManager.queryIntentActivities(
                chooser,
                PackageManager.MATCH_DEFAULT_ONLY
            )
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                context.grantUriPermission(
                    packageName,
                    uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            context.startActivity(chooser)
        }
    }
}

private fun makeAppCrash() {
    throw Exception("This is a test!")
}