package com.anhpham.photofetcher.presentation.util

import java.io.File
import java.io.FilenameFilter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object FileHelper {
    fun getFileNameWithCurrentTime(pattern: String): String {
        val dateFormat: DateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun getFilesWithPattern(parentDir: File, regexPattern: String): List<File> {
        val logNameFilter = FilenameFilter { _: File?, filename: String ->
            val name = filename.lowercase()
            val pattern = Pattern.compile(regexPattern)
            val matcher = pattern.matcher(name)
            matcher.matches()
        }
        val files = parentDir.listFiles(logNameFilter)
        return if (files != null) listOf(*files) else ArrayList()
    }
}