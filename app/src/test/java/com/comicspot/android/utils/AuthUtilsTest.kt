package com.comicspot.android.utils

import android.content.Context
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AuthUtilsTest {

    @MockK
    lateinit var mockContext: Context

    @Before
    fun setUp() {
        mockContext = mockkClass(Context::class)
    }
    @Test
    fun testGenerateComicServiceParams() {
        assertEquals((AuthUtils.generateComicServiceParams(mockContext, 1, "1234", "abcd")),
            "ffd275c5130566a2916217b101f26150")
    }

    @Test
    fun testMd5() {
        val result = AuthUtils.md5("1abcd1234")
        assertEquals(result, "ffd275c5130566a2916217b101f26150")
    }

}