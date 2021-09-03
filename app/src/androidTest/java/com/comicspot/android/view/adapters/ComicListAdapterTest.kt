package com.comicspot.android.view.adapters

import android.content.Context
import com.comicspot.android.network.model.ComicResults
import com.comicspot.android.network.model.ThumbnailInfo
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ComicListAdapterTest {

    lateinit var adapter: ComicListAdapter

    @MockK
    lateinit var mockContext: Context

    @Before
    fun setUp() {
        mockContext = mockkClass(Context::class)
        adapter = ComicListAdapter(ArrayList(), null)
    }

    @Test
    fun testItemCount_ListIsNotEmpty() {
        val result = ComicResults(1, "Title", "Desc", ThumbnailInfo("path", "extension"))
        val list = ArrayList<ComicResults>()
        list.add(result)
        adapter.comicList = list
        Assert.assertEquals(1, adapter.itemCount)
    }

    @Test
    fun testItemCount_ListIsEmpty() {
        adapter.comicList = null
        Assert.assertEquals(0, adapter.itemCount)
    }

    @Test
    fun testUpdateData_Success() {
        val result = ComicResults(1, "Title", "Desc", ThumbnailInfo("path", "extension"))
        val list = ArrayList<ComicResults>()
        list.add(result)
        adapter.comicList = list
        val newResult =
            ComicResults(1, "NewTitle", "NewDesc", ThumbnailInfo("Newpath", "Newextension"))
        val newList = ArrayList<ComicResults>()
        newList.add(newResult)
        adapter.updateData(newList)
        Assert.assertEquals(newList, adapter.comicList)
    }

    @Test
    fun testUpdateData_Fail() {
        val result = ComicResults(1, "Title", "Desc", ThumbnailInfo("path", "extension"))
        val list = ArrayList<ComicResults>()
        list.add(result)
        adapter.comicList = list
        val newResult =
            ComicResults(1, "NewTitle", "NewDesc", ThumbnailInfo("Newpath", "Newextension"))
        val newList = ArrayList<ComicResults>()
        newList.add(newResult)
        adapter.updateData(list)
        Assert.assertNotEquals(newList, adapter.comicList)
    }

}