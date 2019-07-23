package com.zgz.mobile.android.prepexam.datasources.bufferoo.cache.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zgz.mobile.android.prepexam.datasources.bufferoo.cache.db.BufferoosDatabase
import com.zgz.mobile.android.prepexam.datasources.bufferoo.cache.test.factory.BufferooFactory
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
open class CachedBufferooDaoTest {

    private lateinit var bufferoosDatabase: BufferoosDatabase

    @Before
    fun initDb() {
        bufferoosDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BufferoosDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        bufferoosDatabase.close()
    }

    @Test
    fun insertBufferoosSavesData() {
        val cachedBufferoo = BufferooFactory.makeCachedBufferoo()
        bufferoosDatabase.cachedBufferooDao().insertBufferoo(cachedBufferoo)

        val bufferoos = bufferoosDatabase.cachedBufferooDao().getBufferoos()
        assert(bufferoos.isNotEmpty())
    }

    @Test
    fun getBufferoosRetrievesData() {
        val cachedBufferoos = BufferooFactory.makeCachedBufferooList(5)

        cachedBufferoos.forEach {
            bufferoosDatabase.cachedBufferooDao().insertBufferoo(it)
        }

        val retrievedBufferoos = bufferoosDatabase.cachedBufferooDao().getBufferoos()
        assert(retrievedBufferoos == cachedBufferoos.sortedWith(compareBy({ it.id }, { it.id })))
    }
}