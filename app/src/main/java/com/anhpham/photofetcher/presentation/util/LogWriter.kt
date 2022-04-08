package com.anhpham.photofetcher.presentation.util

import android.content.Context
import android.util.Log
import com.anhpham.photofetcher.BuildConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class LogWriter(context: Context) {
    private lateinit var logDirectory: File
    private val mDateFormatter: SimpleDateFormat = SimpleDateFormat(LOG_TIME_PATTERN, Locale.US)
    private var mCurrentLogFile: File? = null

    private var logScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val channel = Channel<String>()
    private val sendChannel: SendChannel<String> = channel

    init {
        initializeLogFile(context)
        removeOldLogFiles()
        startWriting()
    }

    private fun startWriting() {
        logScope.launch(Dispatchers.IO) {
            while (!channel.isClosedForReceive) {
                val log = channel.receive()
                writeLogToFile(log)
            }
        }
    }

    /**
     * Initialize log file
     */
    private fun initializeLogFile(context: Context) {
        logDirectory = File(context.filesDir, LOG_FOLDER)
        if (!logDirectory.exists()) {
            logDirectory.mkdirs()
        }
        mCurrentLogFile = getMostRecentLogFile(logDirectory)
        createNewLogFileIfNeeded()
    }

    /**
     * Create new log file if current log file is not available or is too big
     */
    private fun createNewLogFileIfNeeded(): Boolean {
        if (mCurrentLogFile == null || isFileSizeExceedLimit(
                mCurrentLogFile!!,
                DEFAULT_LOG_FILE_MAX_SIZE
            )
        ) {
            val logFileName = FileHelper.getFileNameWithCurrentTime(LOG_NAME_FORMAT_PATTERN)
            mCurrentLogFile = File(logDirectory, logFileName)
            try {
                mCurrentLogFile!!.createNewFile()
                logAppVersion()
            } catch (e: Exception) {
                mCurrentLogFile = null
                e.printStackTrace()
                return false
            }
        }
        return true
    }

    fun getLatestLogFiles(): List<File>? {
        if (logDirectory.exists()) {
            removeOldLogFiles()
            return getExistingLogFiles(logDirectory)
        }
        return null
    }

    /**
     * Get all log files
     */
    private fun getExistingLogFiles(parentDir: File): List<File> {
        return FileHelper.getFilesWithPattern(parentDir, LOG_NAME_REGEX_PATTERN)
    }

    /**
     * Get most recent log file
     */
    private fun getMostRecentLogFile(parentDir: File): File? {
        val logFiles = getExistingLogFiles(parentDir)
        if (logFiles.isNotEmpty()) {
            var result = logFiles[0]
            for (logFile in logFiles) {
                if (logFile.lastModified() >= result.lastModified()) {
                    result = logFile
                }
            }
            return result
        }
        return null
    }

    /**
     * Check if log file is bigger than a limit
     */
    private fun isFileSizeExceedLimit(file: File, limit: Long): Boolean {
        return if (file.exists() && file.isFile) {
            file.length() >= limit
        } else false
    }

    /**
     * Remove old log files if there are more than 3 files
     */
    private fun removeOldLogFiles() {
        if (!logDirectory.exists()) return
        val logFiles = getExistingLogFiles(logDirectory)
        if (logFiles.size > MAX_LOG_FILES) {
            Collections.sort(logFiles) { lhs: File, rhs: File ->
                val lLastModified = lhs.lastModified()
                val rLastModified = rhs.lastModified()
                if (lLastModified < rLastModified) return@sort 1 else if (lLastModified > rLastModified) return@sort -1
                0
            }
            val totalLogFiles = logFiles.size
            for (i in totalLogFiles - 1 downTo 3) {
                logFiles[i].delete()
            }
        }
    }

    fun logRetrofit(msg: String) {
        val log = "Retrofit:,${getCurrentTime()},Info:,$msg\n"
        enqueueLog(log)
    }

    fun logGCMEvent(msg: String) {
        val log = "GCM Info:,${getCurrentTime()},Info:,$msg\n"
        enqueueLog(log)
    }

    fun logInfoMessage(msg: String) {
        val log = "Info:,${getCurrentTime()},Info:,$msg\n"
        enqueueLog(log)
    }

    fun logErrorMessage(msg: String) {
        val log = "Error:,${getCurrentTime()},Error Message:,$msg\n"
        enqueueLog(log)
    }

    fun logUncaughtException(ex: Throwable?, flush: Boolean) {
        val log = "Uncaught Exception:,${getCurrentTime()},Exception:,${Log.getStackTraceString(ex)}\n"
        if (flush) {
            writeLogToFile(log)
        } else {
            enqueueLog(log)
        }
    }

    private fun logAppVersion() {
        val log = "App Version:,${getCurrentTime()},Version:,${BuildConfig.VERSION_NAME}\n"
        enqueueLog(log)
    }

    private fun getCurrentTime(): String {
        return "Time:," + mDateFormatter.format(System.currentTimeMillis())
    }

    private fun enqueueLog(log: String) {
        logScope.launch {
            sendChannel.send(log)
        }
    }

    private fun writeLogToFile(data: String) {
        try {
            val isFileAvailable = createNewLogFileIfNeeded()
            if (isFileAvailable) {
                val outputStream: OutputStream = FileOutputStream(mCurrentLogFile, true)
                outputStream.use { stream ->
                    stream.write(data.toByteArray())
                }
            }
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
    }

    fun getLogDirectory(): String {
        return logDirectory.path
    }

    fun dispose() {
        logScope.cancel()
        sendChannel.close()
    }

    companion object {
        private const val APP_NAME = "photo_app"
        private const val LOG_NAME_REGEX_PATTERN = APP_NAME + "_log_\\d+.txt"
        private const val LOG_NAME_FORMAT_PATTERN = "'" + APP_NAME + "_log_'yyyyMMddHHmm'.txt'"
        private const val LOG_TIME_PATTERN = "MM-dd hh:mm:ss a"
        private const val DEFAULT_LOG_FILE_MAX_SIZE = 1000000L // 1MB
        private const val LOG_FOLDER: String = "Log"
        private const val MAX_LOG_FILES: Int = 3
    }
}