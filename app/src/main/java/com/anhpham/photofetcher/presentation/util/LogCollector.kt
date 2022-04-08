package com.anhpham.photofetcher.presentation.util

import com.anhpham.photofetcher.presentation.PhotoApplication
import java.io.File
import java.util.*

object LogCollector {
    fun collectLogs(): File {
        val logWriter = PhotoApplication.instance.getLogWriter()
        val logDir = logWriter.getLogDirectory()

        val logFiles = logWriter.getLatestLogFiles()
        val logcatFile = LogcatHelper.createLogcatFile(
            FileHelper.getFileNameWithCurrentTime(LOGCAT_NAME_PATTERN),
            logDir
        )

        return zipAllFiles(
            logDir + File.separator + FileHelper.getFileNameWithCurrentTime(ZIP_FILE_PATTERN),
            logcatFile,
            logFiles
        )
    }

    private fun zipAllFiles(zipFilePath: String, logcatFile: File?, logFiles: List<File>?): File {
        val logFilePaths = ArrayList<String>()
        logcatFile?.let {
            logFilePaths.add(logcatFile.absolutePath)
        }
        logFiles?.let {
            for (file in it) {
                logFilePaths.add(file.absolutePath)
            }
        }

        Compressor.zip(logFilePaths, zipFilePath)
        return File(zipFilePath)
    }

    fun cleanUpOldFiles() {
        val logWriter = PhotoApplication.instance.getLogWriter()
        val logDir = logWriter.getLogDirectory()
        val logDirectory = File(logDir)

        if (logDirectory.exists()) {
            val zipFiles = FileHelper.getFilesWithPattern(logDirectory, ZIP_FILE_REGEX_PATTERN)
            for (zipFile in zipFiles) {
                zipFile.delete()
            }

            val logcatFiles = FileHelper.getFilesWithPattern(logDirectory, LOGCAT_NAME_REGEX_PATTERN)
            for (logcatFile in logcatFiles) {
                logcatFile.delete()
            }
        }
    }

    private const val LOGCAT_NAME_PATTERN = "'logcat_'yyyyMMddHHmm'.txt'"
    private const val LOGCAT_NAME_REGEX_PATTERN = "logcat_\\d+.txt"
    private const val ZIP_FILE_PATTERN = "'photo_app_log_'yyyyMMddHHmm'.zip'"
    private const val ZIP_FILE_REGEX_PATTERN = "photo_app_log_\\d+.zip"
}