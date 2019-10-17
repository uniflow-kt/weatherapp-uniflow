package com.myweatherapp.common.file

import java.io.File

/**
 * Java Json reader for Tests
 */
class JavaReader : JsonReader {

    val path: String by lazy {
        val classLoader: ClassLoader = JavaReader::class.java.classLoader
                ?: error("can't find classpath")
        classLoader.getResource("json/").path
    }

    override fun getFileContent(fileName: String): String {
        val toLowerCase = fileName.toLowerCase()
        return File("$path/$toLowerCase").readLines().joinToString(separator = "\n")
    }
}