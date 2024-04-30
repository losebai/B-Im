package com.example.myapplication

import mu.KotlinLogging
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
private val logger = KotlinLogging.logger {}
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun logTest(){
        logger.info { "info" }
        logger.warn { "warn" }
        logger.debug { "debug" }
        logger.error { "error" }
    }
}