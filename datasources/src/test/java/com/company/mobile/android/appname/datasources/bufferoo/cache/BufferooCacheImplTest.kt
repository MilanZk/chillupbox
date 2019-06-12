package com.company.mobile.android.appname.datasources.bufferoo.cache

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.company.mobile.android.appname.datasources.bufferoo.cache.db.BufferoosDatabase
import com.company.mobile.android.appname.datasources.bufferoo.cache.mapper.BufferooEntityMapper
import com.company.mobile.android.appname.datasources.bufferoo.cache.model.CachedBufferoo
import com.company.mobile.android.appname.datasources.bufferoo.cache.test.factory.BufferooFactory
import org.junit.*
import org.junit.runner.*
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class BufferooCacheImplTest {

    private var bufferoosDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        BufferoosDatabase::class.java
    ).allowMainThreadQueries().build()
    private var entityMapper = BufferooEntityMapper()
    private var preferencesHelper = PreferencesHelper(ApplicationProvider.getApplicationContext())

    private val databaseHelper = BufferooCacheImpl(
        bufferoosDatabase,
        entityMapper, preferencesHelper
    )

    @Test
    fun clearTablesCompletes() {
        val testObserver = databaseHelper.clearBufferoos().test()
        testObserver.assertComplete()
    }

    //<editor-fold desc="Save Bufferoos">
    @Test
    fun saveBufferoosCompletes() {
        val bufferooEntities = BufferooFactory.makeBufferooEntityList(2)

        val testObserver = databaseHelper.saveBufferoos(bufferooEntities).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveBufferoosSavesData() {
        val bufferooCount = 2
        val bufferooEntities = BufferooFactory.makeBufferooEntityList(bufferooCount)

        databaseHelper.saveBufferoos(bufferooEntities).test()
        checkNumRowsInBufferoosTable(bufferooCount)
    }
    //</editor-fold>

    //<editor-fold desc="Get Bufferoos">
    @Test
    fun getBufferoosCompletes() {
        val testObserver = databaseHelper.getBufferoos().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBufferoosReturnsData() {
        val bufferooEntities = BufferooFactory.makeBufferooEntityList(2)
        val cachedBufferoos = mutableListOf<CachedBufferoo>()
        bufferooEntities.forEach {
            cachedBufferoos.add(entityMapper.mapToCached(it))
        }
        insertBufferoos(cachedBufferoos)

        val testObserver = databaseHelper.getBufferoos().test()
        // List is an ordered data structure so the order of elements matters by design.
        // Elements are returned from the database sorted by its id field, so the comparison must be done with a sorted
        // version of the original elements.
        val sortedBufferooEntities = bufferooEntities.sortedBy { bufferoo -> bufferoo.id }
        testObserver.assertValue(sortedBufferooEntities)
    }
    //</editor-fold>

    private fun insertBufferoos(cachedBufferoos: List<CachedBufferoo>) {
        cachedBufferoos.forEach {
            bufferoosDatabase.cachedBufferooDao().insertBufferoo(it)
        }
    }

    private fun checkNumRowsInBufferoosTable(expectedRows: Int) {
        val numberOfRows = bufferoosDatabase.cachedBufferooDao().getBufferoos().size
        assertEquals(expectedRows, numberOfRows)
    }
}