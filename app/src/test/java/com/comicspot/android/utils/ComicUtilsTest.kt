package com.comicspot.android.utils

import com.comicspot.android.Constants
import org.junit.Test
import kotlin.test.assertEquals

class ComicUtilsTest {

    @Test
    fun testGenerateImagePath() {
        val result = ComicUtils.generateImagePath("http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73",
        "jpg",
        Constants.PORTRAIT_FANTASTIC)
        assertEquals(result, "http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73/portrait_fantastic.jpg")
    }
}