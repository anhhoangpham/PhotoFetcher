package com.anhpham.photofetcher.presentation

import android.app.Application
import android.util.Log
import com.anhpham.photofetcher.presentation.util.LogWriter
import kotlin.system.exitProcess

class PhotoApplication : Application() {
    private lateinit var logWriter: LogWriter

    override fun onCreate() {
        super.onCreate()
        logWriter = LogWriter(this)
        instance = this

        Thread.setDefaultUncaughtExceptionHandler { thread: Thread?, e: Throwable? ->
            this.handleUncaughtException(thread, e)
        }
    }

    private fun handleUncaughtException(thread: Thread?, e: Throwable?) {
        Log.e("UncaughtException", "handleUncaughtException", e)
        logWriter.logUncaughtException(e, flush = true)
        exitProcess(1)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        logWriter.dispose()
    }

    fun getLogWriter(): LogWriter {
        return logWriter
    }

    companion object {
        lateinit var instance: PhotoApplication
            private set
    }
}