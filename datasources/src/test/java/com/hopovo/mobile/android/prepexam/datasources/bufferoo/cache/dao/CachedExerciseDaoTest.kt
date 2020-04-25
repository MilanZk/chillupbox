package com.hopovo.mobile.android.prepexam.datasources.bufferoo.cache.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.db.ExerciseDatabase
import com.hopovo.mobile.android.prepexam.datasources.bufferoo.cache.test.factory.BufferooFactory
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
open class CachedExerciseDaoTest {

    private lateinit var bufferoosDatabase: ExerciseDatabase

    @Before
    fun initDb() {
        bufferoosDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ExerciseDatabase::class.java
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
        bufferoosDatabase.exerciseDao().insertExercise(cachedBufferoo)

        val bufferoos = bufferoosDatabase.exerciseDao().getExercises()
        assert(bufferoos.isNotEmpty())
    }

    @Test
    fun getBufferoosRetrievesData() {
        val cachedBufferoos = BufferooFactory.makeCachedBufferooList(5)

        cachedBufferoos.forEach {
            bufferoosDatabase.exerciseDao().insertExercise(it)
        }

        val retrievedBufferoos = bufferoosDatabase.exerciseDao().getExercises()
        assert(retrievedBufferoos == cachedBufferoos.sortedWith(compareBy({ it.id }, { it.id })))
    }
}