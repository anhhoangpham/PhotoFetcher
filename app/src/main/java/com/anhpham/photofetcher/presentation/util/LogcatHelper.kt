package com.anhpham.photofetcher.presentation.util

import java.io.*

object LogcatHelper {
    fun createLogcatFile(fileName: String, folderPath: String): File? {
        val logcat: String = readLogcat()
        return writeToFile(logcat, folderPath, fileName)
    }

    private fun readLogcat(): String {
        val log = StringBuilder()
        try {
            val logcat: Process = Runtime.getRuntime().exec(arrayOf("logcat", "-d"))
            val br = BufferedReader(InputStreamReader(logcat.inputStream), 4 * 1024)
            var line: String?
            val separator = System.getProperty("line.separator")
            while (br.readLine().also { line = it } != null) {
                log.append(line)
                log.append(separator)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return log.toString()
    }

    private fun writeToFile(logcat: String, folderPath: String, fileName: String): File? {
        val folder = File(folderPath)
        val file = File(folder, fileName)
        var outStream: FileOutputStream? = null
        try {
            folder.mkdirs()
            outStream = FileOutputStream(file, false)
            outStream.write(logcat.toByteArray())
            outStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            if (outStream != null) {
                try {
                    outStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return file
    }
}