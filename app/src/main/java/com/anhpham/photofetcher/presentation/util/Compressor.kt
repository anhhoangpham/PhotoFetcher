package com.anhpham.photofetcher.presentation.util

import android.util.Log
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object Compressor {
    private const val BUFFER = 6 * 1024

    fun zip(files: List<String>, zipFile: String?) {
        FileOutputStream(zipFile).use { dest ->
            ZipOutputStream(BufferedOutputStream(dest)).use { out ->
                val data = ByteArray(BUFFER)
                for (file in files) {
                    Log.d("Compress", "Adding: $file")
                    FileInputStream(file).use { fi ->
                        BufferedInputStream(fi, BUFFER).use { origin ->
                            val entry = ZipEntry(file.substring(file.lastIndexOf("/") + 1))
                            out.putNextEntry(entry)
                            var count: Int
                            while (origin.read(data, 0, BUFFER).also { count = it } != -1) {
                                out.write(data, 0, count)
                            }
                        }
                    }
                }
            }
        }
    }
}