package com.anhpham.photofetcher.presentation.util

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FileHelperTest {
    @get:Rule
    var tempFolder = TemporaryFolder()

    private lateinit var testFolder: File

    @Before
    fun setup() {
        testFolder = createFolderWithFiles()
    }

    @Test
    fun getFileNameWithCurrentTime() {
        val datePattern = "yyyyMMdd"
        val pattern = "'log_'$datePattern'.txt'"
        val fileName = FileHelper.getFileNameWithCurrentTime(pattern)

        val datePart = fileName.substring(4, fileName.length - 4)
        val dateString = SimpleDateFormat(datePattern, Locale.getDefault()).format(Date())
        assertEquals(datePart, dateString)
    }

    @Test
    fun getFilesWithPatternReturns5Files() {
        val matchedFiles = FileHelper.getFilesWithPattern(testFolder, "log_\\d+.txt")
        assert(matchedFiles.size == 5)
    }

    @Test
    fun getFilesWithPatternReturnsNoFile() {
        val matchedFiles = FileHelper.getFilesWithPattern(testFolder, "")
        assert(matchedFiles.isEmpty())
    }

    private fun createFolderWithFiles(): File {
        val folder = tempFolder.newFolder("Log")
        tempFolder.newFile(folder.name + File.separator + "log_20190303.txt")
        tempFolder.newFile(folder.name + File.separator + "log_20200312.txt")
        tempFolder.newFile(folder.name + File.separator + "log_20100222.txt")
        tempFolder.newFile(folder.name + File.separator + "log_20210430.txt")
        tempFolder.newFile(folder.name + File.separator + "log_20300923.txt")
        tempFolder.newFile(folder.name + File.separator + "aaaaaaaa.txt")
        tempFolder.newFile(folder.name + File.separator + "12ansa13.txt")
        tempFolder.newFile(folder.name + File.separator + "log_10-14934.txt")
        tempFolder.newFile(folder.name + File.separator + "_.txt")

        return folder
    }
}