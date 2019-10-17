package com.myweatherapp.common.file

/**
 * Json reader
 */
interface JsonReader {
    fun getFileContent(fileName: String): String
}